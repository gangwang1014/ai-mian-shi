package com.xxx.aimianshi.questionbank.repository.impl.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.aimianshi.questionbank.domain.entity.QuestionBankQuestion;
import com.xxx.aimianshi.questionbank.mapper.QuestionBankQuestionMapper;
import com.xxx.aimianshi.questionbank.repository.QuestionBankQuestionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuestionBankQuestionRepositoryImpl extends ServiceImpl<QuestionBankQuestionMapper, QuestionBankQuestion>
        implements QuestionBankQuestionRepository {

    @Override
    public List<QuestionBankQuestion> getByQuestionBankId(Long questionBankId) {
        return lambdaQuery()
                .eq(QuestionBankQuestion::getQuestionBankId, questionBankId)
                .list();
    }

    @Override
    public void removeByQuestionId(Long questionId) {
        lambdaUpdate()
                .eq(QuestionBankQuestion::getQuestionId, questionId)
                .remove();
    }

    @Override
    public void removeBatchByQuestionIds(List<Long> questionIds) {
        lambdaUpdate()
                .in(CollUtil.isNotEmpty(questionIds), QuestionBankQuestion::getQuestionId, questionIds)
                .remove();
    }

    @Override
    public Optional<QuestionBankQuestion> getOptByQuestionIdAndQuestionBankId(Long questionId, Long questionBankId) {
        return lambdaQuery()
                .eq(QuestionBankQuestion::getQuestionId, questionId)
                .eq(QuestionBankQuestion::getQuestionBankId, questionBankId)
                .oneOpt();
    }

    @Override
    public List<QuestionBankQuestion> getByQuestionIds(List<Long> questionIds) {
        return lambdaQuery()
                .in(CollUtil.isNotEmpty(questionIds), QuestionBankQuestion::getQuestionId, questionIds)
                .list();
    }

    @Override
    public List<QuestionBankQuestion> getByQuestionIdsAndQuestionBankId(List<Long> validQuestionIds, Long questionBankId) {
        return lambdaQuery()
                .in(CollUtil.isNotEmpty(validQuestionIds), QuestionBankQuestion::getQuestionId, validQuestionIds)
                .eq(QuestionBankQuestion::getQuestionBankId, questionBankId)
                .list();
    }
}
