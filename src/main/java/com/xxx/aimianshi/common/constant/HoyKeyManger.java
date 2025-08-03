package com.xxx.aimianshi.common.constant;

public final class HoyKeyManger {
    private HoyKeyManger() {}

    public static final String BANK_DETAIL = "bank:detail";

    public static String getBankDetailKey(Long questionBankId) {
        return BANK_DETAIL + ":" + questionBankId;
    }
}
