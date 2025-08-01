package com.xxx.aimianshi.questionbank.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.aimianshi.questionbank.domain.entity.QuestionBankQuestion;

import java.util.List;
import java.util.Optional;

public interface QuestionBankQuestionRepository extends IService<QuestionBankQuestion> {

    List<QuestionBankQuestion> getByQuestionBankId(Long questionBankId);

    void removeByQuestionId(Long questionId);

    void removeBatchByQuestionIds(List<Long> questionIds);

    Optional<QuestionBankQuestion> getOptByQuestionIdAndQuestionBankId(Long questionId, Long questionBankId);

    List<QuestionBankQuestion> getByQuestionIds(List<Long> questionIds);

    List<QuestionBankQuestion> getByQuestionIdsAndQuestionBankId(List<Long> validQuestionIds, Long questionBankId);
}
