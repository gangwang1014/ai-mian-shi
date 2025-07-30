package com.xxx.mianshiya.questionbank.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.mianshiya.common.domain.PageRequest;
import com.xxx.mianshiya.common.exception.BizException;
import com.xxx.mianshiya.common.utils.ThrowUtils;
import com.xxx.mianshiya.questionbank.convert.QuestionBankConverter;
import com.xxx.mianshiya.questionbank.domain.entity.QuestionBank;
import com.xxx.mianshiya.questionbank.domain.req.AddQuestionBankReq;
import com.xxx.mianshiya.questionbank.domain.req.PageQueryQuestionBankReq;
import com.xxx.mianshiya.questionbank.domain.req.UpdateQuestionBankReq;
import com.xxx.mianshiya.questionbank.domain.resp.QuestionBankResp;
import com.xxx.mianshiya.questionbank.repository.QuestionBankRepository;
import com.xxx.mianshiya.questionbank.service.QuestionBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QuestionBankServiceImpl implements QuestionBankService {

    private final QuestionBankRepository questionBankRepository;

    private final QuestionBankConverter questionBankConverter;

    @Override
    public void addQuestionBank(AddQuestionBankReq addQuestionBankReq) {
        QuestionBank questionBank = questionBankConverter.toEntity(addQuestionBankReq);
        try {
            questionBankRepository.save(questionBank);
        } catch (DuplicateKeyException e) {
            throw new BizException("question bank already exist");
        }
    }

    @Override
    public void deleteQuestionBank(Long id) {
        boolean remove = questionBankRepository.removeById(id);
        ThrowUtils.throwIf(!remove, "delete failed, the question bank may not exist");
        // todo 删除题库关联题目
    }

    @Override
    public IPage<QuestionBankResp> pageQuestionBank(PageQueryQuestionBankReq pageQueryQuestionBankReq, PageRequest pageRequest) {
        int pageSize = pageRequest.getPageSize();
        int current = pageRequest.getCurrent();
        LambdaQueryWrapper<QuestionBank> wrapper = buildQueryWrapper(pageQueryQuestionBankReq);
        Page<QuestionBank> page = questionBankRepository.page(new Page<>(current, pageSize), wrapper);
        return page.convert(questionBankConverter::toQuestionBankResp);
    }

    @Override
    public QuestionBankResp detailQuestionBank(Long id) {
        QuestionBank questionBank = questionBankRepository.getOptById(id)
                .orElseThrow(() -> new BizException("question bank may not exist"));
        return questionBankConverter.toQuestionBankResp(questionBank);
    }

    @Override
    public void updateQuestionBank(UpdateQuestionBankReq updateQuestionBankReq) {
        QuestionBank questionBank = questionBankConverter.toEntity(updateQuestionBankReq);
        boolean update = questionBankRepository.updateById(questionBank);
        ThrowUtils.throwIf(!update, "update failed, the question bank may not exist");
    }

    private LambdaQueryWrapper<QuestionBank> buildQueryWrapper(PageQueryQuestionBankReq req) {
        return new LambdaQueryWrapper<QuestionBank>()
                .eq(Objects.nonNull(req.getUserId()), QuestionBank::getUserId, req.getUserId())
                .like(StrUtil.isNotBlank(req.getTitle()), QuestionBank::getTitle, req.getTitle())
                .like(StrUtil.isNotBlank(req.getDescription()), QuestionBank::getDescription, req.getDescription());
    }
}
