package com.chenyi.auth.springbootshiro.config;

import com.chenyi.auth.springbootshiro.bean.UserBean;
import lombok.Data;

@Data
public class ShiroUser {

    private static final long serialVersionUID = -6849794470754667710L;
    private Integer id;

    private String username;

    private String password;

    private String phoneNumber;

    //用户部门ID
    private String deptId;

    //用户部门级别
    private Integer deptLevel;

    private String account;

    public ShiroUser(){}
    public ShiroUser(UserBean userBean){
        this.id=userBean.getId();
        this.password=userBean.getPassword();
        this.username=userBean.getUsername();
        this.phoneNumber=userBean.getUsername();
    }

}
