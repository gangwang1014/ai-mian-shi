package com.xxx.mianshiya.questionbank.service;

import com.xxx.mianshiya.questionbank.domain.req.AddQuestionToBankReq;
import jakarta.validation.constraints.NotNull;

public interface QuestionBankQuestionService {

    void addQuestionToBank(AddQuestionToBankReq addQuestionToBankReq);

    void deleteQuestionBank(@NotNull Long id);
}
