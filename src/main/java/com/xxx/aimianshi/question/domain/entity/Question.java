package com.xxx.aimianshi.question.domain.entity;

import com.xxx.aimianshi.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Question extends BaseEntity {
    private Long id;

    private String title;

    private String content;
    // 数据库为 json 数组 ["java", "python"]
    private String tags;

    private String answer;
    // 创建人
    private Long userId;
}
