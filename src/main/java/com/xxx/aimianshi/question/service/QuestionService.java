package com.xxx.aimianshi.question.service;

import com.xxx.aimianshi.question.domain.req.AddQuestionReq;
import com.xxx.aimianshi.question.domain.req.UpdateQuestionReq;
import com.xxx.aimianshi.question.domain.resp.QuestionResp;
import jakarta.validation.constraints.NotNull;

public interface QuestionService {

    void add(AddQuestionReq addQuestionReq);

    QuestionResp detail(Long id);

    void update(UpdateQuestionReq updateQuestionReq);

    void delete(@NotNull Long id);
}
