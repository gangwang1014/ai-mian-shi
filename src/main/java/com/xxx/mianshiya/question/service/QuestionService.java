package com.xxx.mianshiya.question.service;

import com.xxx.mianshiya.question.domain.req.AddQuestionReq;
import com.xxx.mianshiya.question.domain.req.UpdateQuestionReq;
import com.xxx.mianshiya.question.domain.resp.QuestionDetailResp;
import jakarta.validation.constraints.NotNull;

public interface QuestionService {

    void add(AddQuestionReq addQuestionReq);

    QuestionDetailResp detail(Long id);

    void update(UpdateQuestionReq updateQuestionReq);

    void delete(@NotNull Long id);
}
