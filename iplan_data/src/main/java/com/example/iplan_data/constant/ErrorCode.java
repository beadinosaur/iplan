package com.example.iplan_data.constant;

/**
 * set ErrorCode
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
public enum ErrorCode {
    //enumeration class restricts the instance objects of that class
    SUCCESS(0, "Successfully"),
    DB_ERROR(101, "Database operation exception");


    //enumeration class can have its own member variables and methods
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
