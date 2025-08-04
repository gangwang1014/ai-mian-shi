package com.xxx.aimianshi.ai.agent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public abstract class ReActAgent extends BaseAgent {

    /**
     * 判断是否需要acting
     * @return true/false
     */
    public abstract boolean think();

    /**
     * acting
     * @return acting result
     */
    public abstract String act();

    @Override
    protected String step() {
        try {
            if (!think()) {
                return "thinking over, No action";
            }
            return act();
        } catch (Exception e) {
            return "acting error" + e.getMessage();
        }
    }
}
