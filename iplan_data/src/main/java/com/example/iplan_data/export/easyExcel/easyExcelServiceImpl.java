package com.example.iplan_data.export.easyExcel;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.common.base.Charsets;
import com.google.common.net.HttpHeaders;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.example.iplan_data.constant.ExportConstant.EasyExcelOperateEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@Service
public class easyExcelServiceImpl implements EasyExcelService {
    private static final String MICROSOFT_EXCEL = "application/vnd.ms-excel";

    private static final String exportTemplateFolderAddress = "export/easyTemplate/";

    private static final HashMap<String, String> exportFileNameMap = new HashMap<>();
    private static final HashMap<String, String> exportTemplateFileNameMap = new HashMap<>();
    private static final HashMap<String, byte[]> exportTempLateFileStreamMap = new HashMap<>();

    static {
        init();
    }

    @Override
    public boolean exportExcel(List list, EasyExcelOperateEnum operateEnum, HttpServletResponse response) throws Exception {
        InputStream inputStream = getExportStream(operateEnum);
        if (null == inputStream) {
            return false;
        }
        response.setContentType(MICROSOFT_EXCEL);
        response.setCharacterEncoding(Charsets.UTF_8.name());
        String filename = StrUtil.format("{}", URLEncoder.encode(
                        StringUtils.isBlank(exportFileNameMap.get(operateEnum.getKey())) ? "" :
                                exportFileNameMap.get(operateEnum.getKey()), "UTF-8"));
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, StrUtil.format("attachment;filename="+filename+".xlsx"));
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        Class<?> beanClass = CollectionUtil.isNotEmpty(list) ? list.get(0).getClass() : null;

        ExcelWriter excelWriter = beanClass != null ? EasyExcel.write(response.getOutputStream(), beanClass)
                .excelType(ExcelTypeEnum.XLSX).withTemplate(inputStream).build()
                : EasyExcel.write(response.getOutputStream())
                .excelType(ExcelTypeEnum.XLSX).withTemplate(inputStream).build();

        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        excelWriter.fill(list, writeSheet);
        excelWriter.finish();
        return true;
    }

    private static void init() {
        //export text name
        exportFileNameMap.put(EasyExcelOperateEnum.Plan_LIST.getKey(), "PlanList");

        //export template name
        exportTemplateFileNameMap.put(EasyExcelOperateEnum.Plan_LIST.getKey(), "PlanList.xlsx");
    }

    private InputStream getExportStream(EasyExcelOperateEnum operateEnum) throws Exception {
        if (null != exportTempLateFileStreamMap.get(operateEnum.getKey())) {
            return byte2InputStream(exportTempLateFileStreamMap.get(operateEnum.getKey()));
        } else if (null != exportTemplateFileNameMap.get(operateEnum.getKey())) {
            String address = exportTemplateFolderAddress + exportTemplateFileNameMap.get(operateEnum.getKey());
            InputStream inputStream = easyExcelServiceImpl.class.getClassLoader().getResourceAsStream(address);
            if (null == inputStream) {
                return null;
            }
            exportTempLateFileStreamMap.put(operateEnum.getKey(), inputStream2byte(inputStream));
            return byte2InputStream(exportTempLateFileStreamMap.get(operateEnum.getKey()));
        } else {
            return null;
        }
    }

    private InputStream byte2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    private byte[] inputStream2byte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
