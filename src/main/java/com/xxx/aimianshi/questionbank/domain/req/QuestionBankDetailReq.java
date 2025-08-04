package com.xxx.aimianshi.questionbank.domain.req;

import com.xxx.aimianshi.common.domain.PageRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuestionBankDetailReq extends PageRequest {
    @NotNull
    private Long id;
}
