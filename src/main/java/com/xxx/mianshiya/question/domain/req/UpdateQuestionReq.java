package com.xxx.mianshiya.question.domain.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UpdateQuestionReq {
    @NotBlank
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotEmpty
    private List<@NotBlank String> tags;
    @NotBlank
    private String answer;
}
