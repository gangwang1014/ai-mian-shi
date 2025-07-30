package com.xxx.aimianshi.questionbank.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.aimianshi.questionbank.domain.entity.QuestionBank;
import com.xxx.aimianshi.questionbank.mapper.QuestionBankMapper;
import com.xxx.aimianshi.questionbank.repository.QuestionBankRepository;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionBankRepositoryImpl extends ServiceImpl<QuestionBankMapper, QuestionBank>
        implements QuestionBankRepository {

}
