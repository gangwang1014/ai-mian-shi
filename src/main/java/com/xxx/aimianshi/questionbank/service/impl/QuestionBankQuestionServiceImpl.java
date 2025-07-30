package com.xxx.aimianshi.questionbank.service.impl;

import com.xxx.aimianshi.common.exception.BizException;
import com.xxx.aimianshi.common.utils.ThrowUtils;
import com.xxx.aimianshi.questionbank.convert.QuestionBankQuestionConverter;
import com.xxx.aimianshi.questionbank.domain.entity.QuestionBankQuestion;
import com.xxx.aimianshi.questionbank.domain.req.AddQuestionToBankReq;
import com.xxx.aimianshi.questionbank.repository.QuestionBankQuestionRepository;
import com.xxx.aimianshi.questionbank.service.QuestionBankQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionBankQuestionServiceImpl implements QuestionBankQuestionService {

    private final QuestionBankQuestionRepository questionBankQuestionRepository;

    private final QuestionBankQuestionConverter questionBankQuestionConverter;
    @Override
    public void addQuestionToBank(AddQuestionToBankReq addQuestionToBankReq) {
        QuestionBankQuestion entity = questionBankQuestionConverter.toEntity(addQuestionToBankReq);
        try {
            questionBankQuestionRepository.save(entity);
        } catch (DuplicateKeyException e) {
            throw new BizException("question already in the bank");
        }

    }

    @Override
    public void deleteQuestionBank(Long id) {
        boolean remove = questionBankQuestionRepository.removeById(id);
        ThrowUtils.throwIf(!remove, "delete failed, the question may not in the bank");
    }
}
