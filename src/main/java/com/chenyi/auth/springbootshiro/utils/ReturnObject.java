package com.chenyi.auth.springbootshiro.utils;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuykdev
 */
public class ReturnObject implements Serializable {
    private static final long serialVersionUID = -5606248083009572319L;

    private Integer code=200;
    private String msg="SUCCESS";
    private Object data;
    private List<Object> datas;
    private Page page;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }


}
