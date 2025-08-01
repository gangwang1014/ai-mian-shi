package com.xxx.aimianshi.questionbank.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.aimianshi.common.exception.BizException;
import com.xxx.aimianshi.common.utils.ThrowUtils;
import com.xxx.aimianshi.question.constant.EsFieldConstants;
import com.xxx.aimianshi.question.convert.QuestionConverter;
import com.xxx.aimianshi.question.domain.entity.Question;
import com.xxx.aimianshi.question.domain.es.QuestionEs;
import com.xxx.aimianshi.question.domain.resp.QuestionResp;
import com.xxx.aimianshi.question.repository.QuestionRepository;
import com.xxx.aimianshi.questionbank.convert.QuestionBankQuestionConverter;
import com.xxx.aimianshi.questionbank.domain.entity.QuestionBankQuestion;
import com.xxx.aimianshi.questionbank.domain.req.AddQuestionToBankReq;
import com.xxx.aimianshi.questionbank.domain.req.PageQuestionBankQuestionReq;
import com.xxx.aimianshi.questionbank.repository.QuestionBankQuestionRepository;
import com.xxx.aimianshi.questionbank.service.QuestionBankQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionBankQuestionServiceImpl implements QuestionBankQuestionService {

    private final QuestionBankQuestionRepository questionBankQuestionRepository;

    private final QuestionBankQuestionConverter questionBankQuestionConverter;

    private final QuestionRepository questionRepository;

    private final QuestionConverter questionConverter;

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public void addQuestionToBank(AddQuestionToBankReq addQuestionToBankReq) {
        QuestionBankQuestion entity = questionBankQuestionConverter.toEntity(addQuestionToBankReq);
        try {
            questionBankQuestionRepository.save(entity);
        } catch (DuplicateKeyException e) {
            throw new BizException("question already in the bank");
        }

    }

    @Override
    public void deleteQuestionBank(Long id) {
        boolean remove = questionBankQuestionRepository.removeById(id);
        ThrowUtils.throwIf(!remove, "delete failed, the question may not in the bank");
    }

    @Override
    public IPage<QuestionResp> pageQuestionBankQuestion(PageQuestionBankQuestionReq req) {
        int current = req.getCurrent();
        int pageSize = req.getPageSize();

        // 根据 question bank id 查询 question 然后分页
        List<Long> questionIds = getQuestionIdsByBankId(req.getQuestionBankId());

        LambdaQueryWrapper<Question> wrapper = buildQuestionWrapper(req, questionIds);
        Page<Question> page = questionRepository.page(new Page<>(current, pageSize), wrapper);
        return page.convert(questionConverter::toQuestionResp);
    }

    @Override
    public IPage<QuestionResp> esPageQuestionBankQuestion(PageQuestionBankQuestionReq req) {
        int current = req.getCurrent();
        int pageSize = req.getPageSize();

        // 根据 question bank id 查询 question 然后分页
        List<Long> questionIds = getQuestionIdsByBankId(req.getQuestionBankId());

        Query query = buildQuery(req, questionIds);

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(query)
                .withPageable(PageRequest.of(current - 1, pageSize))
                .build();

        SearchHits<QuestionEs> searchHits = elasticsearchOperations
                .search(nativeQuery, QuestionEs.class, IndexCoordinates.of(EsFieldConstants.INDEX_NAME));

        return toPage(current, pageSize, searchHits);
    }

    public IPage<QuestionResp> queryQuestionWithEsFallback(PageQuestionBankQuestionReq req) {
        try {
            // 调用 Elasticsearch 查询
            return esPageQuestionBankQuestion(req);
        } catch (Exception e) {
            log.error("ES 查询失败，使用数据库降级查询：{}", e.getMessage());
            return pageQuestionBankQuestion(req);
        }
    }

    private List<Long> getQuestionIdsByBankId(Long questionBankId) {
        List<QuestionBankQuestion> bankQuestions = questionBankQuestionRepository.getByQuestionBankId(questionBankId);
        return bankQuestions
                .stream()
                .map(QuestionBankQuestion::getQuestionId)
                .toList();
    }

    private Query buildQuery(PageQuestionBankQuestionReq req, List<Long> questionIds) {
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        boolQueryBuilder.filter(f -> f.term(t -> t.field(EsFieldConstants.IS_DELETE).value(0)));

        if (CollUtil.isNotEmpty(questionIds)) {
            boolQueryBuilder.filter(f -> f.terms(
                    t -> t.field(EsFieldConstants.ID).terms(v -> v.value(
                            questionIds.stream().map(FieldValue::of).toList()
                    ))
            ));
        }

        if (Objects.nonNull(req.getUserId())) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field(EsFieldConstants.USER_ID).value(req.getUserId())));
        }

        List<String> tags = req.getTags();
        if (CollUtil.isNotEmpty(tags)) {
            for (String tag : tags) {
                boolQueryBuilder.filter(f -> f.term(t -> t.field(EsFieldConstants.TAGS).value(tag)));
            }
        }

        String searchText = req.getSearchText();
        // 搜索关键字
        if (StrUtil.isNotBlank(searchText)) {
            boolQueryBuilder.should(s -> s.match(m -> m.field(EsFieldConstants.TITLE).query(searchText)));
            boolQueryBuilder.should(s -> s.match(m -> m.field(EsFieldConstants.CONTENT).query(searchText)));
            boolQueryBuilder.should(s -> s.match(m -> m.field(EsFieldConstants.ANSWER).query(searchText)));
            boolQueryBuilder.minimumShouldMatch(EsFieldConstants.MINIMUM_SHOULD_MATCH);
        }

        return Query.of(q -> q.bool(boolQueryBuilder.build()));
    }

    private Page<QuestionResp> toPage(int current, int pageSize, SearchHits<QuestionEs> searchHits) {
        Page<QuestionResp> page = new Page<>(current, pageSize);
        // 总条数
        page.setTotal(searchHits.getTotalHits());

        // 当前页的数据列表
        List<QuestionResp> records = searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .map(questionConverter::toQuestionResp)
                .collect(Collectors.toList());

        page.setRecords(records);
        return page;
    }

    private LambdaQueryWrapper<Question> buildQuestionWrapper(PageQuestionBankQuestionReq req, List<Long> questionIds) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        if (CollUtil.isNotEmpty(questionIds)) {
            wrapper.in(Question::getId, questionIds);
        }

        if (StrUtil.isNotBlank(req.getSearchText())) {
            wrapper.like(Question::getTitle, req.getSearchText())
                    .or()
                    .like(Question::getContent, req.getSearchText());
        }
        wrapper.eq(Objects.nonNull(req.getUserId()), Question::getUserId, req.getUserId())
                .like(StrUtil.isNotBlank(req.getSearchText()), Question::getTitle, req.getSearchText())
                .like(StrUtil.isNotBlank(req.getSearchText()), Question::getContent, req.getSearchText());
        // JSON 数组查询
        List<String> tags = req.getTags();
        if (CollUtil.isNotEmpty(tags)) {
            for (String tag : tags) {
                wrapper.like(Question::getTags, "\"" + tag + "\"");
            }
        }
        return wrapper;
    }
}
