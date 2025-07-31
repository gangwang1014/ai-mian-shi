package com.xxx.aimianshi.question.domain.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionResp {
    private Long id;

    private String title;

    private String content;
    // 数据库为 json 数组 ["java", "python"]
    private List<String> tags;

    private String answer;
    // 创建人
    private Long userId;

    private String userNickname;
}
