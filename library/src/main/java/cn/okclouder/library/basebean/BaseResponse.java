package cn.okclouder.library.basebean;

import java.io.Serializable;


public class BaseResponse<T> implements Serializable {
    public static final String CODE_SUCCESS = "1";
    public String code;
    public String msg;

    public T data;

    public boolean isSuccess() {
        return CODE_SUCCESS.equals(code);
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code + ", " +
                "msg=" + msg + ", " +
                "data=" + data + "}";
    }
}
