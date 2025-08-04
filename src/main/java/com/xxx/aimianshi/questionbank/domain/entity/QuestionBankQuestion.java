package com.xxx.aimianshi.questionbank.domain.entity;

import com.xxx.aimianshi.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionBankQuestion extends BaseEntity {
    private Long id;
    private Long questionBankId;
    private Long questionId;
}
