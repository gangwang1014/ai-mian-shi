package com.xxx.aimianshi.ai.controller;

import com.xxx.aimianshi.ai.service.AIService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @RequestMapping("/chat")
    public String chat(String message, String chatId) {
        return aiService.chat(message, chatId);
    }

}
