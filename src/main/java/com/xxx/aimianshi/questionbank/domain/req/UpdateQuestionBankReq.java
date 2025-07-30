package com.xxx.aimianshi.questionbank.domain.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateQuestionBankReq {
    private Long id;
    private String title;
    private String description;
    private String picture;
}
