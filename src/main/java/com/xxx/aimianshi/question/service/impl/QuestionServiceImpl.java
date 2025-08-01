package com.xxx.aimianshi.question.service.impl;

import com.xxx.aimianshi.common.exception.BizException;
import com.xxx.aimianshi.common.utils.ThrowUtils;
import com.xxx.aimianshi.question.convert.QuestionConverter;
import com.xxx.aimianshi.question.domain.entity.Question;
import com.xxx.aimianshi.question.domain.req.AddQuestionReq;
import com.xxx.aimianshi.question.domain.req.UpdateQuestionReq;
import com.xxx.aimianshi.question.domain.resp.QuestionResp;
import com.xxx.aimianshi.question.repository.QuestionRepository;
import com.xxx.aimianshi.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionConverter questionConverter;

    @Override
    public void add(AddQuestionReq addQuestionReq) {
        Question question = questionConverter.toEntity(addQuestionReq);
        questionRepository.save(question);
    }

    @Override
    public QuestionResp detail(Long id) {
        Question question = questionRepository.getOptById(id)
                .orElseThrow(() -> new BizException("The question does not exist"));
        return questionConverter.toQuestionResp(question);
    }

    @Override
    public void update(UpdateQuestionReq updateQuestionReq) {
        Question question = questionConverter.toEntity(updateQuestionReq);
        boolean update = questionRepository.updateById(question);
        ThrowUtils.throwIf(!update, "update failed, maybe the question does not exist");
    }

    @Override
    public void delete(Long id) {
        boolean remove = questionRepository.removeById(id);
        ThrowUtils.throwIf(!remove, "delete failed, maybe the question does not exist");
    }

    @Override
    public void batchAddQuestion(List<AddQuestionReq> addQuestionReqList) {
        List<Question> questions = addQuestionReqList.stream()
                .map(questionConverter::toEntity)
                .toList();
        questionRepository.saveBatch(questions);
    }
}
