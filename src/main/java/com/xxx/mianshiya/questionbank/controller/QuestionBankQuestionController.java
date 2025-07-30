package com.xxx.mianshiya.questionbank.controller;

import com.xxx.mianshiya.questionbank.domain.req.AddQuestionToBankReq;
import com.xxx.mianshiya.questionbank.service.QuestionBankQuestionService;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank-question")
public class QuestionBankQuestionController {

    private final QuestionBankQuestionService questionBankQuestionService;

    public QuestionBankQuestionController(QuestionBankQuestionService questionBankQuestionService) {
        this.questionBankQuestionService = questionBankQuestionService;
    }

    /**
     * 向题库增加题目
     */
    @PostMapping
    public void addQuestionToBank(AddQuestionToBankReq addQuestionToBankReq) {
        questionBankQuestionService.addQuestionToBank(addQuestionToBankReq);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestionBank(@PathVariable @NotNull Long id) {
        questionBankQuestionService.deleteQuestionBank(id);
    }
}
