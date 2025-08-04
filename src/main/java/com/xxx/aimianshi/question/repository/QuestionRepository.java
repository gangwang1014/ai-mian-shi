package com.xxx.aimianshi.question.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.aimianshi.question.domain.entity.Question;

import java.util.List;

public interface QuestionRepository extends IService<Question> {

    IPage<Question> pageByIds(Integer current, Integer pageSize, List<Long> questionIds);
}
