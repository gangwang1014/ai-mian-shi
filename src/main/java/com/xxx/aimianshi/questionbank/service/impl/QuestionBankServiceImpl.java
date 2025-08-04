package com.xxx.aimianshi.questionbank.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.platform.hotkey.client.callback.JdHotKeyStore;
import com.xxx.aimianshi.common.constant.HoyKeyManger;
import com.xxx.aimianshi.common.exception.BizException;
import com.xxx.aimianshi.common.utils.ThrowUtils;
import com.xxx.aimianshi.question.convert.QuestionConverter;
import com.xxx.aimianshi.question.domain.entity.Question;
import com.xxx.aimianshi.question.domain.resp.QuestionResp;
import com.xxx.aimianshi.question.repository.QuestionRepository;
import com.xxx.aimianshi.questionbank.convert.QuestionBankConverter;
import com.xxx.aimianshi.questionbank.domain.entity.QuestionBank;
import com.xxx.aimianshi.questionbank.domain.entity.QuestionBankQuestion;
import com.xxx.aimianshi.questionbank.domain.req.AddQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.req.PageQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.req.QuestionBankDetailReq;
import com.xxx.aimianshi.questionbank.domain.req.UpdateQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.resp.QuestionBankDetailResp;
import com.xxx.aimianshi.questionbank.domain.resp.QuestionBankResp;
import com.xxx.aimianshi.questionbank.repository.QuestionBankQuestionRepository;
import com.xxx.aimianshi.questionbank.repository.QuestionBankRepository;
import com.xxx.aimianshi.questionbank.service.QuestionBankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionBankServiceImpl implements QuestionBankService {

    private final QuestionBankRepository questionBankRepository;

    private final QuestionBankConverter questionBankConverter;

    private final QuestionBankQuestionRepository questionBankQuestionRepository;

    private final QuestionRepository questionRepository;

    private final QuestionConverter questionConverter;

    @Override
    public void addQuestionBank(AddQuestionBankReq addQuestionBankReq) {
        QuestionBank questionBank = questionBankConverter.toEntity(addQuestionBankReq);
        try {
            questionBankRepository.save(questionBank);
        } catch (DuplicateKeyException e) {
            throw new BizException("question bank already exist");
        }
    }

    @Override
    @Transactional
    public void deleteQuestionBank(Long id) {
        boolean remove = questionBankRepository.removeById(id);
        ThrowUtils.throwIf(!remove, "delete failed, the question bank may not exist");
        List<QuestionBankQuestion> bankQuestions = questionBankQuestionRepository.getByQuestionBankId(id);
        if (CollUtil.isEmpty(bankQuestions)) {
            return;
        }
        questionBankQuestionRepository.removeBatchByIds(bankQuestions);
    }

    @Override
    public IPage<QuestionBankResp> pageQuestionBank(PageQuestionBankReq pageQuestionBankReq) {
        int pageSize = pageQuestionBankReq.getPageSize();
        int current = pageQuestionBankReq.getCurrent();
        LambdaQueryWrapper<QuestionBank> wrapper = buildQueryWrapper(pageQuestionBankReq);
        Page<QuestionBank> page = questionBankRepository.page(new Page<>(current, pageSize), wrapper);
        return page.convert(questionBankConverter::toQuestionBankResp);
    }

    @Override
    public QuestionBankDetailResp detailQuestionBank(QuestionBankDetailReq req) {
        // hotkey
        Long questionBankId = req.getId();
        // 从缓存中获取
        String bankDetailKey = HoyKeyManger.getBankDetailKey(questionBankId);
        QuestionBankDetailResp cacheQuestionBankDetail = getByHotkeyCache(bankDetailKey);
        if (Objects.nonNull(cacheQuestionBankDetail)) {
            return cacheQuestionBankDetail;
        }

        QuestionBank questionBank = questionBankRepository.getOptById(questionBankId)
                .orElseThrow(() -> new BizException("question bank not exist"));

        // 查询题库详情信息
        int pageSize = req.getPageSize();
        int current = req.getCurrent();
        IPage<QuestionResp> questions = getPageQuestionResp(current, pageSize, questionBankId);

        QuestionBankDetailResp questionBankResp = questionBankConverter.toQuestionBankDetailResp(questionBank, questions);

        // hotkey 缓存
        JdHotKeyStore.smartSet(bankDetailKey, questionBankResp);

        return questionBankResp;
    }

    private QuestionBankDetailResp getByHotkeyCache(String bankDetailKey) {
        if (JdHotKeyStore.isHotKey(bankDetailKey)) {
            Object cacheQuestionBankDetail = JdHotKeyStore.get(bankDetailKey);
            if (cacheQuestionBankDetail != null) {
                log.info("is hotkey, cache question bank detail, key: {}", bankDetailKey);
                return (QuestionBankDetailResp) cacheQuestionBankDetail;
            }
        }
        return null;
    }

    private IPage<QuestionResp> getPageQuestionResp(int current, int pageSize, Long questionBankId) {
        List<QuestionBankQuestion> bankQuestions = questionBankQuestionRepository.getByQuestionBankId(questionBankId);
        List<Long> questionIds = bankQuestions.stream()
                .map(QuestionBankQuestion::getQuestionId)
                .toList();
        IPage<Question> questionList = questionRepository.pageByIds(current, pageSize, questionIds);
        return questionList.convert(questionConverter::toQuestionResp);
    }

    @Override
    public void updateQuestionBank(UpdateQuestionBankReq updateQuestionBankReq) {
        QuestionBank questionBank = questionBankConverter.toEntity(updateQuestionBankReq);
        boolean update = questionBankRepository.updateById(questionBank);
        ThrowUtils.throwIf(!update, "update failed, the question bank may not exist");
    }

    private LambdaQueryWrapper<QuestionBank> buildQueryWrapper(PageQuestionBankReq req) {
        return new LambdaQueryWrapper<QuestionBank>()
                .eq(Objects.nonNull(req.getUserId()), QuestionBank::getUserId, req.getUserId())
                .like(StrUtil.isNotBlank(req.getSearchText()), QuestionBank::getTitle, req.getSearchText())
                .like(StrUtil.isNotBlank(req.getSearchText()), QuestionBank::getDescription, req.getSearchText());
    }
}
