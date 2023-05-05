package com.example.iplan_data.bean.response;

import com.example.iplan_data.entity.TitleFrequency;
import lombok.Data;

import java.util.List;

@Data
public class SelectHotWordsResponse extends BaseResponse {
    private List<TitleFrequency> data;
}