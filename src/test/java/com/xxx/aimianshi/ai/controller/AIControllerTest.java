package com.xxx.aimianshi.ai.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class AIControllerTest {

    @Autowired
    private AIController aiController;
    @Test
    void chat() {

        String chatId = UUID.randomUUID().toString();
        String ans = aiController.chat("现在开始面试, spring boot 内容, 我要面试的难度是中等", chatId);
        Assertions.assertNotNull(ans);
        System.out.println(ans);
        String ans2 = aiController.chat("我要面试的难度是?", chatId);
        Assertions.assertNotNull(ans2);
        System.out.println(ans2);
    }

}