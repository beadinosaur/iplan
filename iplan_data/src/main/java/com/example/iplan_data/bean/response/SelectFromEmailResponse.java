package com.example.iplan_data.bean.response;

import lombok.Data;

import java.util.List;

/**
 * FromEmail Response
 */
@Data
public class SelectFromEmailResponse extends BaseResponse {
    private List<String> data;
}
