package com.xxx.mianshiya.question.controller;

import com.xxx.mianshiya.question.domain.req.AddQuestionReq;
import com.xxx.mianshiya.question.domain.req.UpdateQuestionReq;
import com.xxx.mianshiya.question.domain.resp.QuestionDetailResp;
import com.xxx.mianshiya.question.service.QuestionService;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@Validated
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // todo add, update, delete 为管理员权限
    @PostMapping
    public void addQuestion(@RequestBody AddQuestionReq addQuestionReq) {
        questionService.add(addQuestionReq);
    }

    @GetMapping
    public QuestionDetailResp questionDetail(@RequestParam @NotNull Long id) {
        return questionService.detail(id);
    }

    @PutMapping
    public void updateQuestion(@RequestBody UpdateQuestionReq updateQuestionReq) {
        questionService.update(updateQuestionReq);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable @NotNull Long id) {
        questionService.delete(id);
    }

    // todo page 分页获取题目列表

    // todo 用户添加题目, 管理员审核功能
}
