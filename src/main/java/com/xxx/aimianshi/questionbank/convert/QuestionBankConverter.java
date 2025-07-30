package com.xxx.aimianshi.questionbank.convert;

import com.xxx.aimianshi.questionbank.domain.entity.QuestionBank;
import com.xxx.aimianshi.questionbank.domain.req.AddQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.req.UpdateQuestionBankReq;
import com.xxx.aimianshi.questionbank.domain.resp.QuestionBankResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface QuestionBankConverter {

    @Mappings({
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "isDelete", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    QuestionBank toEntity(AddQuestionBankReq addQuestionBankReq);

    QuestionBankResp toQuestionBankResp(QuestionBank questionBank);

    @Mappings({
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "isDelete", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "userId", ignore = true)
    })
    QuestionBank toEntity(UpdateQuestionBankReq updateQuestionBankReq);
}
