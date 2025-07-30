package com.xxx.aimianshi.questionbank.domain.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageQueryQuestionBankReq {
    private String title;
    private String description;
    private Long userId;
}
