package com.chenyi.auth.springbootshiro.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class UserBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = -5606248083009572319L;


    @Id
    @Column(name="seq")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name="lastest_login_time")
    private Date lastestLoginTime;

    @Column(name = "user_state")
    private String userState;

    @Column(name="lastest_pass_modify_time")
    private Date lastestPassModifyTime;

    @Column(name="dept_id")
    private String deptId;

    @Column(name="cert_code")
    private String certCode;

    @Column(name = "email")
    private String email;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "staff_id")
    private String staffId;

    @Column(name = "update_time")
    private Date updateTime;

}
