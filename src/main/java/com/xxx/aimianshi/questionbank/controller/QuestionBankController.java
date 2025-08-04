package com.xxx.aimianshi.questionbank.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.aimianshi.questionbank.domain.req.AddQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.req.PageQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.req.QuestionBankDetailReq;
import com.xxx.aimianshi.questionbank.domain.req.UpdateQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.resp.QuestionBankDetailResp;
import com.xxx.aimianshi.questionbank.domain.resp.QuestionBankResp;
import com.xxx.aimianshi.questionbank.service.QuestionBankService;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class QuestionBankController {
    private final QuestionBankService questionBankService;

    public QuestionBankController(QuestionBankService questionBankService) {
        this.questionBankService = questionBankService;
    }

    @PreAuthorize("hasRole('admin') or hasAnyAuthority('insert', 'all')")
    @PostMapping
    public void addQuestionBank(@RequestBody AddQuestionBankReq addQuestionBankReq) {
        questionBankService.addQuestionBank(addQuestionBankReq);
    }

    @PostMapping("/detail")
    public QuestionBankDetailResp detailQuestionBank(@RequestBody QuestionBankDetailReq req) {
        return questionBankService.detailQuestionBank(req);
    }

    @PostMapping("/page")
    public IPage<QuestionBankResp> pageQuestionBank(@RequestBody PageQuestionBankReq pageQuestionBankReq) {
        return questionBankService.pageQuestionBank(pageQuestionBankReq);
    }

    @PreAuthorize("hasRole('admin') or hasAnyAuthority('delete', 'all')")
    @DeleteMapping("/{id}")
    public void deleteQuestionBank(@PathVariable @NotNull Long id) {
        questionBankService.deleteQuestionBank(id);
    }

    @PreAuthorize("hasRole('admin') or hasAnyAuthority('update', 'all')")
    @PutMapping
    public void updateQuestionBank(@RequestBody UpdateQuestionBankReq updateQuestionBankReq) {
        questionBankService.updateQuestionBank(updateQuestionBankReq);
    }

}
