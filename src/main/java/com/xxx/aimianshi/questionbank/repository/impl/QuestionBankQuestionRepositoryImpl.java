package com.xxx.aimianshi.questionbank.repository.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.aimianshi.common.client.RedisCache;
import com.xxx.aimianshi.common.constant.RedisKeyManger;
import com.xxx.aimianshi.questionbank.domain.entity.QuestionBankQuestion;
import com.xxx.aimianshi.questionbank.mapper.QuestionBankQuestionMapper;
import com.xxx.aimianshi.questionbank.repository.QuestionBankQuestionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionBankQuestionRepositoryImpl extends ServiceImpl<QuestionBankQuestionMapper, QuestionBankQuestion>
        implements QuestionBankQuestionRepository {

    private final RedisCache redisCache;

    public QuestionBankQuestionRepositoryImpl(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    public List<QuestionBankQuestion> getByQuestionBankId(Long questionBankId) {
        List<QuestionBankQuestion> questionBankQuestions = redisCache
                .listRangeAll(RedisKeyManger.getBankQuestionKey(questionBankId), QuestionBankQuestion.class);
        if (CollUtil.isNotEmpty(questionBankQuestions)) {
            return questionBankQuestions;
        }
        List<QuestionBankQuestion> bankQuestions = lambdaQuery()
                .eq(QuestionBankQuestion::getQuestionBankId, questionBankId)
                .list();
        redisCache.listRightPushAll(
                RedisKeyManger.getBankQuestionKey(questionBankId),
                bankQuestions,
                RedisKeyManger.QUESTION_EXPIRE_TIME,
                RedisKeyManger.TIME_UNIT
        );
        return bankQuestions;
    }

    @Override
    public void removeBatchByQuestionIds(List<Long> questionIds) {
        lambdaUpdate()
                .in(CollUtil.isNotEmpty(questionIds), QuestionBankQuestion::getQuestionId, questionIds)
                .remove();
    }

    @Override
    public List<QuestionBankQuestion> getByQuestionIdsAndQuestionBankId(List<Long> validQuestionIds, Long questionBankId) {
        return lambdaQuery()
                .in(CollUtil.isNotEmpty(validQuestionIds), QuestionBankQuestion::getQuestionId, validQuestionIds)
                .eq(QuestionBankQuestion::getQuestionBankId, questionBankId)
                .list();
    }
}
