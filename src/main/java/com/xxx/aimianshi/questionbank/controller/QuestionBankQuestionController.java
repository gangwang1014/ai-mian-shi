package com.xxx.aimianshi.questionbank.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.aimianshi.question.domain.resp.QuestionResp;
import com.xxx.aimianshi.questionbank.domain.req.AddQuestionToBankReq;
import com.xxx.aimianshi.questionbank.domain.req.PageQuestionBankQuestionReq;
import com.xxx.aimianshi.questionbank.service.QuestionBankQuestionService;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasRole('admin') or hasAnyAuthority('insert', 'all')")
    @PostMapping
    public void addQuestionToBank(@RequestBody AddQuestionToBankReq addQuestionToBankReq) {
        questionBankQuestionService.addQuestionToBank(addQuestionToBankReq);
    }

    @PreAuthorize("hasRole('admin') or hasAnyAuthority('delete', 'all')")
    @DeleteMapping("/{id}")
    public void deleteQuestionBank(@PathVariable @NotNull Long id) {
        questionBankQuestionService.deleteQuestionBank(id);
    }

    /**
     * 分页查询题库的题目
     */
    @PostMapping("/page")
    public IPage<QuestionResp> pageQuestionBankQuestion(@RequestBody PageQuestionBankQuestionReq req) {
        return questionBankQuestionService.pageQuestionBankQuestion(req);
    }

    /**
     * es 分页查询题库的题目
     */
    @PostMapping("/es/page")
    public IPage<QuestionResp> esPageQuestionBankQuestion(@RequestBody PageQuestionBankQuestionReq req) {
        return questionBankQuestionService.queryQuestionWithEsFallback(req);
    }

    @PreAuthorize("hasRole('admin') or hasAnyAuthority('insert', 'all')")
    @PostMapping("/batch")
    public void batchAddQuestionToBank(@RequestBody List<@NotNull Long> questionIds, @RequestParam @NotNull Long questionBankId) {
        questionBankQuestionService.batchAddQuestionToBank(questionIds, questionBankId);
    }

    @PreAuthorize("hasRole('admin') or hasAnyAuthority('delete', 'all')")
    @DeleteMapping("/batch")
    public void batchDeleteQuestionToBank(@RequestBody List<@NotNull Long> questionBankQuestionIds) {
        questionBankQuestionService.batchDeleteQuestionToBank(questionBankQuestionIds);
    }
}
