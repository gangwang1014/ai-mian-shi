package com.xxx.aimianshi.question.constant;

public class EsFieldConstants {
    private EsFieldConstants() {
        throw new AssertionError("Cannot instantiate");
    }

    public static final String INDEX_NAME = "question";

    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String TAGS = "tags";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String ANSWER = "answer";
    public static final String IS_DELETE = "isDelete";
    public static final String CREATE_TIME = "createTime";
    public static final String UPDATE_TIME = "updateTime";

    public static final String MINIMUM_SHOULD_MATCH = "1";
}
