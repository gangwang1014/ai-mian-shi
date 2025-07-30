package com.xxx.aimianshi.questionbank.domain.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddQuestionBankReq {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String picture;
}
