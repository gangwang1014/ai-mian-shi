package com.xxx.aimianshi.question.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.aimianshi.question.domain.entity.Question;
import com.xxx.aimianshi.question.mapper.QuestionMapper;
import com.xxx.aimianshi.question.repository.QuestionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepositoryImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionRepository {

}
