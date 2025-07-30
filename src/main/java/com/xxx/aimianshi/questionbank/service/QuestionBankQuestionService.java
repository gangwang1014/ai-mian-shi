package com.xxx.aimianshi.questionbank.service;

import com.xxx.aimianshi.questionbank.domain.req.AddQuestionToBankReq;
import jakarta.validation.constraints.NotNull;

public interface QuestionBankQuestionService {

    void addQuestionToBank(AddQuestionToBankReq addQuestionToBankReq);

    void deleteQuestionBank(@NotNull Long id);
}
