package com.chenyi.auth.springbootshiro.bean;

import com.alibaba.fastjson.JSONObject;
import tk.mybatis.mapper.entity.IDynamicTableName;

import javax.persistence.Transient;

public class BaseBean implements IDynamicTableName {
    @Transient
    protected String tableName = null;


    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getDynamicTableName() {
        return tableName;
    }
    public String toJsonString(){
        String s = JSONObject.toJSONString(this);
        return s;
    }
}
