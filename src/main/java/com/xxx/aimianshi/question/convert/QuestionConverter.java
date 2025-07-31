package com.xxx.aimianshi.question.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.aimianshi.common.utils.UserContext;
import com.xxx.aimianshi.question.domain.entity.Question;
import com.xxx.aimianshi.question.domain.req.AddQuestionReq;
import com.xxx.aimianshi.question.domain.req.UpdateQuestionReq;
import com.xxx.aimianshi.question.domain.resp.QuestionResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", imports = { UserContext.class })
public interface QuestionConverter {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "isDelete", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "userId", expression = "java(UserContext.getCurrentUserId())"),
            @Mapping(target = "tags", expression = "java(toJson(addQuestionReq.getTags()))")
    })
    Question toEntity(AddQuestionReq addQuestionReq);

    default String toJson(List<String> tags) {
        try {
            return new ObjectMapper().writeValueAsString(tags);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Mappings({
            @Mapping(target = "tags", expression = "java(toList(question.getTags()))")
    })
    QuestionResp toQuestionResp(Question question);


    default List<String> toList(String tagsJson) {
        try {
            return new ObjectMapper().readValue(tagsJson, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse tags JSON", e);
        }
    }

    @Mappings({
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "isDelete", ignore = true),
            @Mapping(target = "updateTime", ignore = true),
            @Mapping(target = "userId", expression = "java(UserContext.getCurrentUserId())"),
            @Mapping(target = "tags", expression = "java(toJson(updateQuestionReq.getTags()))")
    })
    Question toEntity(UpdateQuestionReq updateQuestionReq);
}
