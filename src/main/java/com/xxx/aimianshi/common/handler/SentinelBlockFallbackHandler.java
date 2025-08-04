package com.xxx.aimianshi.common.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xxx.aimianshi.questionbank.domain.req.QuestionBankDetailReq;
import com.xxx.aimianshi.questionbank.domain.resp.QuestionBankDetailResp;

public class SentinelBlockFallbackHandler {
    /**
     * 降级处理
     *
     * @param req       req
     * @param exception exception
     */
    public static QuestionBankDetailResp handleBlock(QuestionBankDetailReq req, BlockException exception) {
        throw new RuntimeException("The system pressure is too high, please try again later");
    }

    /**
     * 熔断处理
     *
     * @param req       req
     * @param throwable throwable
     */
    public static QuestionBankDetailResp handleFallback(QuestionBankDetailReq req, Throwable throwable) {
        return new QuestionBankDetailResp();
    }
}
