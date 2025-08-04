package com.xxx.aimianshi.ai.service.impl;

import com.xxx.aimianshi.ai.service.AIService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Service
public class AIServiceImpl implements AIService {
    private final ChatClient chatClient;


    public AIServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chat(String message, String chatId) {
        return chatClient.prompt()
                .user( message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .content();
    }
}
