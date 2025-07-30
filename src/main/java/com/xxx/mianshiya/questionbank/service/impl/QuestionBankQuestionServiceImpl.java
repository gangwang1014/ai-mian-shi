package com.xxx.mianshiya.questionbank.service.impl;

import com.xxx.mianshiya.common.exception.BizException;
import com.xxx.mianshiya.common.utils.ThrowUtils;
import com.xxx.mianshiya.questionbank.convert.QuestionBankQuestionConverter;
import com.xxx.mianshiya.questionbank.domain.entity.QuestionBankQuestion;
import com.xxx.mianshiya.questionbank.domain.req.AddQuestionToBankReq;
import com.xxx.mianshiya.questionbank.repository.QuestionBankQuestionRepository;
import com.xxx.mianshiya.questionbank.service.QuestionBankQuestionService;
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
