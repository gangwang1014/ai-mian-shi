package com.xxx.aimianshi.questionbank.domain.entity;

import com.xxx.aimianshi.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class QuestionBankQuestion extends BaseEntity {
    private Long id;
    private Long questionBankId;
    private Long questionId;
}
