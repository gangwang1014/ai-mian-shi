package com.xxx.aimianshi.ai.memory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.model.Media;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MessageDeserializer extends JsonDeserializer<Message> {

    @Override
    public Message deserialize(JsonParser p, DeserializationContext deserializationContext) {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = null;
        Message message = null;
        try {
            node = mapper.readTree(p);
            String messageType = node.get("messageType").asText();
            switch (messageType) {
                case "USER" -> message = new UserMessage(node.get("text").asText(),
                        mapper.convertValue(node.get("media"), new TypeReference<Collection<Media>>() {
                        }), mapper.convertValue(node.get("metadata"), new TypeReference<Map<String, Object>>() {
                }));
                case "ASSISTANT" -> message = new AssistantMessage(node.get("text").asText(),
                        mapper.convertValue(node.get("metadata"), new TypeReference<Map<String, Object>>() {
                        }), (List<AssistantMessage.ToolCall>) mapper.convertValue(node.get("toolCalls"),
                        new TypeReference<Collection<AssistantMessage.ToolCall>>() {
                        }),
                        (List<Media>) mapper.convertValue(node.get("media"), new TypeReference<Collection<Media>>() {
                        }));
                default -> throw new IllegalArgumentException("Unknown message type: " + messageType);
            }
            ;
        } catch (IOException e) {
            log.error("Error deserializing message", e);
        }
        return message;
    }

}