package com.xxx.mianshiya.questionbank.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.mianshiya.common.domain.PageRequest;
import com.xxx.mianshiya.questionbank.domain.req.AddQuestionBankReq;
import com.xxx.mianshiya.questionbank.domain.req.PageQueryQuestionBankReq;
import com.xxx.mianshiya.questionbank.domain.req.UpdateQuestionBankReq;
import com.xxx.mianshiya.questionbank.domain.resp.QuestionBankResp;
import jakarta.validation.constraints.NotNull;

public interface QuestionBankService {

    void addQuestionBank(AddQuestionBankReq addQuestionBankReq);

    void deleteQuestionBank(Long id);

    IPage<QuestionBankResp> pageQuestionBank(PageQueryQuestionBankReq pageQueryQuestionBankReq, PageRequest pageRequest);

    QuestionBankResp detailQuestionBank(@NotNull Long id);

    void updateQuestionBank(UpdateQuestionBankReq updateQuestionBankReq);
}
