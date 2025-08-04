package com.xxx.aimianshi.ai.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.aimianshi.ai.memory.RedisChatMemory;
import com.xxx.aimianshi.common.client.RedisCache;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DashscopeAIConfig {

    private static final String SYSTEM_PROMPT = """
            You are a senior technical interviewer. Your responsibilities:
            
            Rule A (user says "start interview", "mock", "mock interview", etc. → enter proactive questioning mode)
            1. Ask for role focus (frontend / backend / algorithm / QA / mobile) and desired difficulty (Easy / Medium / Hard).
            2. Ask only one question or follow-up at a time, then wait for the candidate's answer.
            3. After each response, provide concise feedback: strengths, gaps, time/space complexity, edge cases, and suggestions for improvement.
            4. When the user says "next", "switch topic", or "end interview", obey immediately.
            
            Rule B (any other case → Q&A mode)
            Answer any interview-related questions the user may have (self-intro, project highlights, algorithms, system design, behavioral questions,
            salary negotiation, etc.) with structured, clear English explanations and example code.
            
            Output format
            Interviewer: {question or feedback}
            (code blocks wrapped with ```language)
            Candidate: {leave blank for user input}
            """;

    @Bean
    public ChatClient dashscopeChatClient(ChatModel dashscopeChatModel, ChatMemory redisChatMemory) {
        return ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(new MessageChatMemoryAdvisor(redisChatMemory))
                .build();
    }

    @Bean
    ChatMemory redisChatMemory(RedisCache redisCache, ObjectMapper objectMapper) {
        return new RedisChatMemory(redisCache, objectMapper);
    }

}
