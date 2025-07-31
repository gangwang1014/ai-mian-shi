package com.xxx.aimianshi.questionbank.domain.req;

import com.xxx.aimianshi.common.domain.PageRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageQuestionBankReq extends PageRequest {
    private String searchText;
    private Long userId;
}
