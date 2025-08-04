package com.xxx.aimianshi.ai.agent;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.ToolCallback;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class ToolCallAgent extends ReActAgent {

    private final ToolCallback[] availableTools;

    // 工具调用响应信息
    private ChatResponse toolCallChatResponse;

    // 工具调用管理者
    private final ToolCallingManager toolCallingManager;

    // 禁用 spring dashscope 内置工具调用, 自己维护上下文
    private final ChatOptions chatOptions;

    public ToolCallAgent(ToolCallback[] availableTools) {
        this.availableTools = availableTools;
        this.toolCallingManager = ToolCallingManager.builder().build();
        this.chatOptions = DashScopeChatOptions.builder()
                // true 需要自己维护上下文
                .withProxyToolCalls(true)
                .build();
    }

    @Override
    public boolean think() {
        if (getNextStepPrompt() != null && !getNextStepPrompt().isEmpty()) {
            // 添加下一步的prompt
            getMessageList().add(new UserMessage(getNextStepPrompt()));
        }

        List<Message> messageList = getMessageList();
        // 完整的对话 prompt
        Prompt prompt = new Prompt(messageList, chatOptions);

        try {
            ChatResponse chatResponse = getChatClient().prompt(prompt)
                    .system(getSystemPrompt())
                    .tools(availableTools)
                    .call()
                    .chatResponse();
            this.toolCallChatResponse = chatResponse;
            AssistantMessage assistantMessage = chatResponse.getResult().getOutput();

            String result = assistantMessage.getText();
            List<AssistantMessage.ToolCall> toolCalls = assistantMessage.getToolCalls();

            log.info("{} thinking result: {}", getName(), result);
            log.info("{} thinking tool calls, calls size: {}", getName(), toolCalls.size());

            String toolCallInfo = toolCalls.stream()
                    .map(t -> String.format("tool call name: %s, args: %s", t.name(), t.arguments()))
                    .collect(Collectors.joining("\n"));

            log.info("tool call info: {}", toolCallInfo);

            if (toolCalls.isEmpty()) {
                // 只有不调用工具是 才记录助手信息
                getMessageList().add(assistantMessage);
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            log.error("{} thinking error: {}", getName(), e.getMessage());
            getMessageList().add(new AssistantMessage("thinking error: " + e.getMessage()));
            return false;
        }
    }

    @Override
    public String act() {
        if (!toolCallChatResponse.hasToolCalls()) {
            return "no tool calls";
        }

        Prompt prompt = new Prompt(getMessageList(), chatOptions);
        ToolExecutionResult toolExecutionResult = toolCallingManager.executeToolCalls(prompt, toolCallChatResponse);
        // 记录消息上下文 conversationHistory 已包含助手消息返回
        setMessageList(toolExecutionResult.conversationHistory());

        ToolResponseMessage toolResponseMessage = (ToolResponseMessage) CollUtil.getLast(toolExecutionResult.conversationHistory());
        String results = toolResponseMessage.getResponses()
                .stream()
                .map(resp -> String.format("tool %s Complete the task response: %s", resp.name(), resp.responseData()))
                .collect(Collectors.joining("\n"));
        log.info("tools response: {}", results);
        return results;
    }
}
