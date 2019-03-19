package com.example.demo.error.normal;


public enum NEError implements IError {
    SYSTEM_INTERNAL_ERROR("0000", "System Internal Error"),
    INVALID_PARAMETER("0001", "Invalid Parameter"),
    PAGER_IS_NULL("0002", "pager is null"),
    PAGER_PARAMETER_IS_NOT_CORRECT("0003", "pager parameter is not correct"),
    METHOD_NOT_SUPPORTED("0004", "method not supported"),
    SERVICE_NOT_FOUND("0005", "service not found"),
    INVALID_STATUS("0006", "invalid status"),
    WRITE_EXCEL_ERROR("0007", "write excel error"),
    READ_EXCEL_ERROR("0008", "read excel error"),
    CONFIG_IS_NOT_CORRECT("0009", "config is not correct"),
    NOT_SUPPORT("0010", "not support"),
    WRITE_JSON_ERROR("0011", "write json error"),
    READ_JSON_ERROR("0012", "read json error"),
    CALL_REMOTE_ERROR("0013", "call remote error"),
    CONTENT_TYPE_NOT_SUPPORT("0014", "content type is not support"),
    TOKEN_NOT_FOUND("0015", "token not found"),
    DATA_NOT_FOUND("0016", "data not found");


    String errorCode;
    String errorMessage;
    private static final String ns = "SYS";

    NEError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getNamespace() {
        return ns;
    }

    public String getErrorCode() {
        return ns + "." + errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
