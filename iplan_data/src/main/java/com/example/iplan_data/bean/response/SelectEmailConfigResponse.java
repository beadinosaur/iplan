package com.example.iplan_data.bean.response;

import com.example.iplan_data.entity.vo.EmailConfigVo;
import lombok.Data;

/**
 * select EmailConfig response
 */
@Data
public class SelectEmailConfigResponse extends BaseResponse {
    private EmailConfigVo data;
}
