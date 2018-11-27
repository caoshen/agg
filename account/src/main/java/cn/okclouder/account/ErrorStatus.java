package cn.okclouder.account;

public class ErrorStatus {
    public static final int LOGIN_CANCEL = 1;
    public static final String LOGIN_CANCEL_REASON = "login cancel";
    int errorCode;
    String errorReason;

    public ErrorStatus(int code, String reason) {
        errorCode = code;
        errorReason = reason;
    }
}
