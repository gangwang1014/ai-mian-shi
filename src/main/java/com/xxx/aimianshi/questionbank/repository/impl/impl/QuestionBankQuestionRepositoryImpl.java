package com.xxx.aimianshi.questionbank.repository.impl.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.aimianshi.questionbank.domain.entity.QuestionBankQuestion;
import com.xxx.aimianshi.questionbank.mapper.QuestionBankQuestionMapper;
import com.xxx.aimianshi.questionbank.repository.QuestionBankQuestionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionBankQuestionRepositoryImpl extends ServiceImpl<QuestionBankQuestionMapper, QuestionBankQuestion>
        implements QuestionBankQuestionRepository {

    @Override
    public List<QuestionBankQuestion> getByQuestionBankId(Long questionBankId) {
        return lambdaQuery()
                .eq(QuestionBankQuestion::getQuestionBankId, questionBankId)
                .list();
    }
}
