package com.xxx.aimianshi.questionbank.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.aimianshi.common.domain.PageRequest;
import com.xxx.aimianshi.questionbank.domain.req.AddQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.req.PageQueryQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.req.UpdateQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.resp.QuestionBankResp;
import com.xxx.aimianshi.questionbank.service.QuestionBankService;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class QuestionBankController {
    private final QuestionBankService questionBankService;

    public QuestionBankController(QuestionBankService questionBankService) {
        this.questionBankService = questionBankService;
    }

    // todo add update delete 为管理员权限
    @PostMapping
    public void addQuestionBank(@RequestBody AddQuestionBankReq addQuestionBankReq) {
        questionBankService.addQuestionBank(addQuestionBankReq);
    }

    @GetMapping
    public QuestionBankResp detailQuestionBank(@RequestParam @NotNull Long id) {
        return questionBankService.detailQuestionBank(id);
    }

    @GetMapping("/page")
    public IPage<QuestionBankResp> pageQuestionBank(
            @RequestBody PageQueryQuestionBankReq pageQueryQuestionBankReq,
            @RequestBody PageRequest pageRequest
    ) {
        return questionBankService.pageQuestionBank(pageQueryQuestionBankReq, pageRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestionBank(@PathVariable @NotNull Long id) {
        questionBankService.deleteQuestionBank(id);
    }

    @PutMapping
    public void updateQuestionBank(@RequestBody UpdateQuestionBankReq updateQuestionBankReq) {
        questionBankService.updateQuestionBank(updateQuestionBankReq);
    }

}
