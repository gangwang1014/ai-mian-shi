package com.xxx.aimianshi.questionbank.convert;

import com.xxx.aimianshi.questionbank.domain.entity.QuestionBankQuestion;
import com.xxx.aimianshi.questionbank.domain.req.AddQuestionToBankReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface QuestionBankQuestionConverter {

    @Mappings({
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "isDelete", ignore = true),
            @Mapping(target = "updateTime", ignore = true)
    })
    QuestionBankQuestion toEntity(AddQuestionToBankReq addQuestionToBankReq);
}
