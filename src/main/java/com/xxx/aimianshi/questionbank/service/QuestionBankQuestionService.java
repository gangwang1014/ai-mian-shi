package com.xxx.aimianshi.questionbank.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.aimianshi.question.domain.resp.QuestionResp;
import com.xxx.aimianshi.questionbank.domain.req.AddQuestionToBankReq;
import com.xxx.aimianshi.questionbank.domain.req.PageQuestionBankQuestionReq;
import jakarta.validation.constraints.NotNull;

public interface QuestionBankQuestionService {

    void addQuestionToBank(AddQuestionToBankReq addQuestionToBankReq);

    void deleteQuestionBank(@NotNull Long id);

    IPage<QuestionResp> pageQuestionBankQuestion(PageQuestionBankQuestionReq req);
}
