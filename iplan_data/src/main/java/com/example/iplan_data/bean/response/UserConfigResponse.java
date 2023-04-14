package com.example.iplan_data.bean.response;

import com.example.iplan_data.entity.vo.UserVo;
import lombok.Data;

/**
 * user config response
 */
@Data
public class UserConfigResponse extends BaseResponse{
   private UserVo userVo;
}
