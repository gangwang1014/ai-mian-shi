package com.xxx.mianshiya.question.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.mianshiya.question.domain.entity.Question;
import com.xxx.mianshiya.question.mapper.QuestionMapper;
import com.xxx.mianshiya.question.repository.QuestionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepositoryImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionRepository {

}
