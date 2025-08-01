package com.xxx.aimianshi.question.controller;

import com.xxx.aimianshi.question.domain.req.AddQuestionReq;
import com.xxx.aimianshi.question.domain.req.UpdateQuestionReq;
import com.xxx.aimianshi.question.domain.resp.QuestionResp;
import com.xxx.aimianshi.question.service.QuestionService;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@Validated
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PreAuthorize("hasRole('admin') or hasAnyAuthority('insert', 'all')")
    @PostMapping
    public void addQuestion(@RequestBody AddQuestionReq addQuestionReq) {
        questionService.add(addQuestionReq);
    }
    @GetMapping
    public QuestionResp questionDetail(@RequestParam @NotNull Long id) {
        return questionService.detail(id);
    }

    @PreAuthorize("hasRole('admin') or hasAnyAuthority('update', 'all')")
    @PutMapping
    public void updateQuestion(@RequestBody UpdateQuestionReq updateQuestionReq) {
        questionService.update(updateQuestionReq);
    }

    @PreAuthorize("hasRole('admin') or hasAnyAuthority('delete', 'all')")
    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable @NotNull Long id) {
        questionService.delete(id);
    }

    @PreAuthorize("hasRole('admin') or hasAnyAuthority('add', 'all')")
    @PostMapping("/batch")
    public void batchAddQuestion(@RequestBody List<AddQuestionReq> addQuestionReqList) {
        questionService.batchAddQuestion(addQuestionReqList);
    }


    @PreAuthorize("hasRole('admin') or hasAnyAuthority('delete', 'all')")
    @DeleteMapping("/batch")
    public void batchDeleteQuestion(@RequestBody List<Long> questionIds) {
        questionService.batchDeleteQuestion(questionIds);
    }
}
