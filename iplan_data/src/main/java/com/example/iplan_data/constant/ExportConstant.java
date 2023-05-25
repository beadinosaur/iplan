package com.example.iplan_data.constant;

public class ExportConstant {

    public enum EasyExcelOperateEnum{

        Plan_LIST("plan_list");

        private  String key;
        EasyExcelOperateEnum(String key){
            this.key = key;
        }

        public String getKey(){return key;}

    }
}
