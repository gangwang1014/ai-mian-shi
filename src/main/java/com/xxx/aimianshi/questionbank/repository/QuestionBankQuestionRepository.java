package com.xxx.aimianshi.questionbank.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.aimianshi.questionbank.domain.entity.QuestionBankQuestion;

import java.util.List;

public interface QuestionBankQuestionRepository extends IService<QuestionBankQuestion> {

    List<QuestionBankQuestion> getByQuestionBankId(Long questionBankId);

    void removeBatchByQuestionIds(List<Long> questionIds);

    List<QuestionBankQuestion> getByQuestionIdsAndQuestionBankId(List<Long> validQuestionIds, Long questionBankId);
}
