package com.xxx.aimianshi.questionbank.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.aimianshi.question.domain.resp.QuestionResp;
import com.xxx.aimianshi.questionbank.domain.req.AddQuestionToBankReq;
import com.xxx.aimianshi.questionbank.domain.req.PageQuestionBankQuestionReq;

import java.util.List;

public interface QuestionBankQuestionService {

    void addQuestionToBank(AddQuestionToBankReq addQuestionToBankReq);

    void deleteQuestionBank(Long id);

    IPage<QuestionResp> pageQuestionBankQuestion(PageQuestionBankQuestionReq req);

    IPage<QuestionResp> esPageQuestionBankQuestion(PageQuestionBankQuestionReq req);

    IPage<QuestionResp> queryQuestionWithEsFallback(PageQuestionBankQuestionReq req);

    void batchAddQuestionToBank(List<Long> questionIds, Long questionBankId);

    void batchDeleteQuestionToBank(List<Long> questionBankQuestionIds);
}
