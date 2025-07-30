package com.xxx.mianshiya.questionbank.repository.impl.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.mianshiya.questionbank.domain.entity.QuestionBankQuestion;
import com.xxx.mianshiya.questionbank.mapper.QuestionBankQuestionMapper;
import com.xxx.mianshiya.questionbank.repository.QuestionBankQuestionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionBankQuestionRepositoryImpl extends ServiceImpl<QuestionBankQuestionMapper, QuestionBankQuestion>
        implements QuestionBankQuestionRepository {

}
