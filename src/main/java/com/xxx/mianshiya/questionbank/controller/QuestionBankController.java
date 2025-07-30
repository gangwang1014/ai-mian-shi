package com.xxx.mianshiya.questionbank.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.mianshiya.common.domain.PageRequest;
import com.xxx.mianshiya.questionbank.domain.req.AddQuestionBankReq;
import com.xxx.mianshiya.questionbank.domain.req.PageQueryQuestionBankReq;
import com.xxx.mianshiya.questionbank.domain.req.UpdateQuestionBankReq;
import com.xxx.mianshiya.questionbank.domain.resp.QuestionBankResp;
import com.xxx.mianshiya.questionbank.service.QuestionBankService;
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
