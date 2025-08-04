package com.xxx.aimianshi.ai.memory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.aimianshi.common.client.RedisCache;
import com.xxx.aimianshi.common.constant.RedisKeyManger;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用 Redis 存储一个会话的完整消息列表（JSON）
 * key = ai:chat:{conversationId}
 */
@Slf4j
@Component
public class RedisChatMemory implements ChatMemory {

    private final RedisCache redisCache;
    private final ObjectMapper objectMapper;

    public RedisChatMemory(RedisCache redisCache, ObjectMapper objectMapper) {
        this.redisCache = redisCache;
        this.objectMapper = objectMapper;
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        String chatMemoryKey = RedisKeyManger.getChatMemoryKey(conversationId);
        try {
            List<String> messageJsons = getMessageJsonList(messages);
            redisCache.listRightPushAll(chatMemoryKey, messageJsons, RedisKeyManger.CHAT_MEMORY_EXPIRE_TIME, RedisKeyManger.TIME_UNIT);
        } catch (Exception e) {
            throw new RuntimeException("message push to redis error");
        }

    }

    @NotNull
    private List<String> getMessageJsonList(List<Message> messages) {
        return messages.stream()
                .map(message -> {
                    try {
                        return objectMapper.writeValueAsString(message);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        String chatMemoryKey = RedisKeyManger.getChatMemoryKey(conversationId);
        Long length = redisCache.listLength(chatMemoryKey);
        if (length ==  null || length == 0L) {
            return List.of();
        }
        long start = Math.max(0L, length - lastN);
        try {
            List<String> messageJsons = redisCache.listRange(chatMemoryKey, start, length - 1, String.class);
            List<Message> messageList = getMessageList(messageJsons);
            log.info("message get from redis: {}", messageList);
            return messageList;
        } catch (Exception e) {
            throw new RuntimeException("message get from redis error");
        }
    }

    private List<Message> getMessageList(List<String> messageJsons) {
        return messageJsons.stream()
                .map(messageJson -> {
                    try {
                        return objectMapper.readValue(messageJson, Message.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
    }

    @Override
    public void clear(String conversationId) {
        String chatMemoryKey = RedisKeyManger.getChatMemoryKey(conversationId);
        redisCache.delete(chatMemoryKey);
    }
}
