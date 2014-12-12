package com.jianfeng.xiaomianao.exception;

import com.jianfeng.xiaomianao.util.ErrorCode;

public class XiaoMianAoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    private Object errorValue = null;

    public XiaoMianAoException(String msg) {
        super(msg);
    }

    public XiaoMianAoException(String msg, Throwable e) {
        super(msg, e);
    }

    public XiaoMianAoException(ErrorCode errorCode) {
        super(errorCode.memo);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorValue() {
        return errorValue;
    }

    public void setErrorValue(Object errorValue) {
        this.errorValue = errorValue;
    }
}
