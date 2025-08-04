package com.xxx.aimianshi.question.repository.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.aimianshi.question.domain.entity.Question;
import com.xxx.aimianshi.question.mapper.QuestionMapper;
import com.xxx.aimianshi.question.repository.QuestionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionRepositoryImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionRepository {

    @Override
    public IPage<Question> pageByIds(Integer current, Integer pageSize, List<Long> questionIds) {
        return lambdaQuery()
                .in(CollUtil.isNotEmpty(questionIds), Question::getId, questionIds)
                .page(new Page<>(current, pageSize));
    }
}
