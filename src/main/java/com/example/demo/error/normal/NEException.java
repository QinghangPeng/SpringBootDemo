package com.example.demo.error.normal;

/**
 * 系统异常，自定义异常必须继承本类,业务模块需要抛出业务异常时使用该异常。
 * 业务异常时指定义了对应的错误代码和错误消息的异常
 * User: Robin
 */
public class NEException extends RuntimeException {
    private static final long serialVersionUID = -6293662498600553602L;
    private IError error = NEError.SYSTEM_INTERNAL_ERROR;
    private String extMessage = null;

    public NEException() {
    }

    public NEException(String message) {
        super(message);
        this.extMessage = message;
    }

    public NEException(String message, Throwable cause) {
        super(message, cause);
        this.extMessage = message;
    }

    public NEException(Throwable cause) {
        super(cause);
        if (cause instanceof NEException) {
            NEException fe = (NEException) cause;
            this.error = fe.getError();
            this.extMessage = fe.getMessage();
        }
    }

    public NEException(IError error) {
        super(error.getErrorCode() + ":" + error.getErrorMessage());
        this.error = error;
    }

    public NEException(IError error, String message) {
        super(error.getErrorCode() + ":" + error.getErrorMessage());
        this.error = error;
        this.extMessage = message;
    }

    public NEException(Throwable cause, IError error, String message) {
        super(message, cause);
        this.extMessage = message;
        this.error = error;
    }

    public NEException(IError error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    public IError getError() {
        return error;
    }

    public String getExtMessage() {
        return extMessage;
    }

    public void setExtMessage(String extMessage) {
        this.extMessage = extMessage;
    }

    @Override
    public String toString() {
        return super.toString() + ",ErrorCode : " + error.getErrorCode() + ", ErrorMessage : " + error.getErrorMessage() + ", ExtMessage : " + extMessage;
    }
}
