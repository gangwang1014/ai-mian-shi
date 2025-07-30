package com.xxx.mianshiya.questionbank.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.mianshiya.questionbank.domain.entity.QuestionBank;
import com.xxx.mianshiya.questionbank.mapper.QuestionBankMapper;
import com.xxx.mianshiya.questionbank.repository.QuestionBankRepository;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionBankRepositoryImpl extends ServiceImpl<QuestionBankMapper, QuestionBank>
        implements QuestionBankRepository {

}
