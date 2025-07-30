package com.xxx.aimianshi.questionbank.domain.entity;

import com.xxx.aimianshi.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionBank extends BaseEntity {

    private Long id;

    private String title;

    private String description;

    private String picture;

    private Long userId;
}
