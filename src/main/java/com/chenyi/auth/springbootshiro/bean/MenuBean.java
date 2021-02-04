package com.chenyi.auth.springbootshiro.bean;

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
@Table(name="menu")
public class MenuBean  extends BaseBean implements Serializable {

    private static final long serialVersionUID = -5606248083009572319L;
    @Id
    @Column(name="id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name="parentid")
    private Integer pid;

    @Column(name="menuname")
    private String name;

    @Column(name="url")
    private String pageurl;

    @Column(name="type")
    private Integer menutype;

    @Column(name="store")
    private Integer sortno ;

    @Column(name="icon")
    private String icon;

    @Column(name="active")
    private Integer active;

    @Column(name = "mark")
    private String mark;

    @Column(name="create_time")
    private Date createTime;

    @Column(name="update_time")
    private Date updateTime;

    @Column(name="staff_id")
    private String staff_id;

}
