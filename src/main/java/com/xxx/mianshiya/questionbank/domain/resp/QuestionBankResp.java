package com.xxx.mianshiya.questionbank.domain.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionBankResp {
    private Long id;

    private String title;

    private String description;

    private String picture;

    private Long userId;
}
