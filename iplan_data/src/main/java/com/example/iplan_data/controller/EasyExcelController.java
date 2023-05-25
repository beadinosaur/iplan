package com.example.iplan_data.controller;
import com.example.iplan_data.bean.request.SelectDailyPlanByMonthRangeRequest;
import com.example.iplan_data.entity.PlanData;
import com.example.iplan_data.export.easyExcel.EasyExcelService;
import com.example.iplan_data.service.IPlanDataService;
import com.example.iplan_data.constant.ExportConstant.EasyExcelOperateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/export")
public class EasyExcelController {

    @Autowired
    private EasyExcelService easyExcelService;

    @Resource
    private IPlanDataService planDataService;


    @RequestMapping(value = "/easyExcelReport", produces = "application/json;charset=utf-8")
    public void easyExcelReport(HttpServletResponse response, @RequestBody SelectDailyPlanByMonthRangeRequest request) throws IOException{
        List<PlanData> list = planDataService.selectByMonthRange(request.getUserName());
        try {
          EasyExcelOperateEnum x = EasyExcelOperateEnum.Plan_LIST;
            easyExcelService.exportExcel(list,EasyExcelOperateEnum.Plan_LIST,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
