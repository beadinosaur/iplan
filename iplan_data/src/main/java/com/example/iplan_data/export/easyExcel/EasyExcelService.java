package com.example.iplan_data.export.easyExcel;


import com.example.iplan_data.constant.ExportConstant.EasyExcelOperateEnum;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface EasyExcelService {
    /**
     * easyExcel
     * @param list
     * @param operateEnum
     * @param response
     * @return
     * @throws Exception
     */
    boolean exportExcel(List list, EasyExcelOperateEnum operateEnum, HttpServletResponse response) throws  Exception;
}
