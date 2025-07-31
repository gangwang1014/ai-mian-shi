package com.xxx.aimianshi.questionbank.domain.req;

import com.xxx.aimianshi.common.domain.PageRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PageQuestionBankQuestionReq extends PageRequest {
    private String searchText;

    private List<String> tags;

    private Long userId;

    private Long questionBankId;
}
