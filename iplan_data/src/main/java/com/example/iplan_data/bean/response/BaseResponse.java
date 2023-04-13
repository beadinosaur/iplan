package com.example.iplan_data.bean.response;

import com.example.iplan_data.constant.ErrorCode;
import lombok.Data;
/**
 * Base Response
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
@Data
public class BaseResponse {

    private int code = 0;

    private String message;

    public void setErrorCode(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
