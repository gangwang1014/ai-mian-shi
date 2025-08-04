package com.xxx.aimianshi.ai.agent;

import cn.hutool.core.util.StrUtil;
import com.xxx.aimianshi.ai.agent.status.AgentStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public abstract class BaseAgent {
    private String name;

    private String systemPrompt;
    private String nextStepPrompt;

    private AgentStatus agentStatus = AgentStatus.IDLE;

    // 控制
    private int maxStep = 10;
    private int currentStep = 0;


    private ChatClient chatClient;

    // memory
    private List<Message> messageList = new ArrayList<>();

    public String run(String userPrompt) {

        if (agentStatus != AgentStatus.IDLE) {
            throw new RuntimeException("cannot run, agent is not idle");
        }

        if (StrUtil.isBlank(userPrompt)) {
            throw new RuntimeException("cannot run with empty user prompt");
        }

        agentStatus = AgentStatus.RUNNING;

        messageList.add(new UserMessage(userPrompt));

        try {
            List<String> results = new ArrayList<>();
            for (int i = 0; i < maxStep && agentStatus != AgentStatus.FINISHED; i++) {
                currentStep++;
                log.info("executing step: {}/{}", currentStep, maxStep);
                String stepResult = step();
                String res = "Step " + currentStep + ": " + stepResult;
                results.add(res);
            }

            if (currentStep >= maxStep) {
                agentStatus = AgentStatus.FINISHED;
                results.add("Terminated: max step reached, maxStep: " + maxStep);
            }
            return String.join("\n", results);
        } catch (Exception e) {
            agentStatus = AgentStatus.ERROR;
            log.error("error executing step", e);
            return  "error executing step" + e.getMessage();
        } finally {
            cleanup();
        }
    }

    protected abstract String step();

    protected void cleanup() {

    }
}
