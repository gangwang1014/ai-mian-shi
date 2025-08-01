package com.xxx.aimianshi.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.aimianshi.question.domain.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 同步包含已经删除的题目
     * @param minUpdateTime 最小更新时间
     * @return 题目列表
     */
    List<Question> listQuestionWithDelete(@Param("minUpdateTime") Date minUpdateTime);
}
