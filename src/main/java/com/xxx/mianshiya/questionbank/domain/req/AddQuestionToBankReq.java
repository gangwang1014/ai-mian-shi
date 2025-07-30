package com.xxx.mianshiya.questionbank.domain.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddQuestionToBankReq {
    private Long questionBankId;
    private Long questionId;
}
