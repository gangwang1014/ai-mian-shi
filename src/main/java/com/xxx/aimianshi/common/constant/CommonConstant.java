package com.xxx.aimianshi.common.constant;

/**
 * 常量对象 禁止继承 和 实例化
 */
public final class CommonConstant {

    /**
     * 禁用构造函数 防止常量对象实例化
     */
    private CommonConstant() {
        throw new AssertionError("Cannot instantiate");
    }
    /**
     * 升序
     */
    public static final String SORT_ORDER_ASC = "ascend";

    /**
     * 降序
     */
    public static final String SORT_ORDER_DESC = " descend";

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
}
