package com.xxx.aimianshi.questionbank.domain.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.aimianshi.question.domain.resp.QuestionResp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionBankDetailResp {
    private Long id;

    private String title;

    private String description;

    private String picture;

    private Long userId;

    private IPage<QuestionResp> questions;
}
