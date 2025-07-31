package com.xxx.aimianshi.questionbank.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.aimianshi.questionbank.domain.req.AddQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.req.PageQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.req.UpdateQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.resp.QuestionBankResp;
import jakarta.validation.constraints.NotNull;

public interface QuestionBankService {

    void addQuestionBank(AddQuestionBankReq addQuestionBankReq);

    void deleteQuestionBank(Long id);

    IPage<QuestionBankResp> pageQuestionBank(PageQuestionBankReq pageQuestionBankReq);

    QuestionBankResp detailQuestionBank(@NotNull Long id);

    void updateQuestionBank(UpdateQuestionBankReq updateQuestionBankReq);
}
