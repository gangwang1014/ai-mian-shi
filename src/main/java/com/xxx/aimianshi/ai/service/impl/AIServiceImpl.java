package com.xxx.aimianshi.ai.service.impl;

import com.xxx.aimianshi.ai.config.QueryRewriter;
import com.xxx.aimianshi.ai.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {
    private final ChatClient chatClient;

    private final ToolCallback[] toolCallbacks;

    private final QueryRewriter queryRewriter;


    public String chat(String message, String chatId) {
        return doChat(message, chatId);
    }

    public String doChat(String message, String chatId) {
        String rewriteMessage = queryRewriter.doQueryRewrite(message);

        return chatClient.prompt()
                .user(rewriteMessage)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .tools(toolCallbacks)
                .call()
                .content();
    }
}
