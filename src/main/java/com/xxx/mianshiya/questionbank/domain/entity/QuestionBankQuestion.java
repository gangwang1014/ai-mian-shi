package com.xxx.mianshiya.questionbank.domain.entity;

import com.xxx.mianshiya.common.domain.BaseEntity;
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
