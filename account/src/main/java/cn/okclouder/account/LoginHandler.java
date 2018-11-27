package cn.okclouder.account;

public interface LoginHandler {
    void onLogin(AccountInfo account);
    void onError(ErrorStatus status);
}
