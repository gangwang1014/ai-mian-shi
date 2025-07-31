package com.xxx.aimianshi.questionbank.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.aimianshi.common.exception.BizException;
import com.xxx.aimianshi.common.utils.ThrowUtils;
import com.xxx.aimianshi.question.convert.QuestionConverter;
import com.xxx.aimianshi.question.domain.entity.Question;
import com.xxx.aimianshi.question.domain.resp.QuestionResp;
import com.xxx.aimianshi.question.repository.QuestionRepository;
import com.xxx.aimianshi.questionbank.convert.QuestionBankQuestionConverter;
import com.xxx.aimianshi.questionbank.domain.entity.QuestionBankQuestion;
import com.xxx.aimianshi.questionbank.domain.req.AddQuestionToBankReq;
import com.xxx.aimianshi.questionbank.domain.req.PageQuestionBankQuestionReq;
import com.xxx.aimianshi.questionbank.repository.QuestionBankQuestionRepository;
import com.xxx.aimianshi.questionbank.service.QuestionBankQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QuestionBankQuestionServiceImpl implements QuestionBankQuestionService {

    private final QuestionBankQuestionRepository questionBankQuestionRepository;

    private final QuestionBankQuestionConverter questionBankQuestionConverter;

    private final QuestionRepository questionRepository;

    private final QuestionConverter questionConverter;

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
        Long questionBankId = req.getQuestionBankId();
        List<QuestionBankQuestion> bankQuestions = questionBankQuestionRepository.getByQuestionBankId(questionBankId);
        List<Long> questionIds = bankQuestions.stream().map(QuestionBankQuestion::getQuestionId).toList();
        LambdaQueryWrapper<Question> wrapper = buildQuestionWrapper(req, questionIds);
        Page<Question> page = questionRepository.page(new Page<>(current, pageSize), wrapper);
        return page.convert(questionConverter::toQuestionResp);
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
