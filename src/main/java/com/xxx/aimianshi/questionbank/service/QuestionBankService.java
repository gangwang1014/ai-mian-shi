package com.xxx.aimianshi.questionbank.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.aimianshi.questionbank.domain.req.AddQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.req.PageQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.req.QuestionBankDetailReq;
import com.xxx.aimianshi.questionbank.domain.req.UpdateQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.resp.QuestionBankDetailResp;
import com.xxx.aimianshi.questionbank.domain.resp.QuestionBankResp;

public interface QuestionBankService {

    void addQuestionBank(AddQuestionBankReq addQuestionBankReq);

    void deleteQuestionBank(Long id);

    IPage<QuestionBankResp> pageQuestionBank(PageQuestionBankReq pageQuestionBankReq);

    QuestionBankDetailResp detailQuestionBank(QuestionBankDetailReq req);

    void updateQuestionBank(UpdateQuestionBankReq updateQuestionBankReq);
}
