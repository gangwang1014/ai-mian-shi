package com.xxx.aimianshi.question.domain.es;

import com.xxx.aimianshi.common.constant.CommonConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Document(indexName = "question")
@Getter
@Setter
public class QuestionEs {
    @Id
    private Long id;

    private String title;

    private String content;

    private String answer;

    private List<String> tags;

    private Long userId;

    @Field(type = FieldType.Date, format = {}, pattern = CommonConstant.DATE_TIME_PATTERN)
    private Date createTime;

    @Field(type = FieldType.Date, format = {}, pattern = CommonConstant.DATE_TIME_PATTERN)
    private Date updateTime;

    private Integer isDelete;
}
