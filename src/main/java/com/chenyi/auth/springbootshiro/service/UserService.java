package com.chenyi.auth.springbootshiro.service;

import com.chenyi.auth.springbootshiro.bean.UserBean;
import com.chenyi.auth.springbootshiro.config.ShiroUser;
import com.chenyi.auth.springbootshiro.utils.ReturnObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface UserService extends MapperService<UserBean> {

    List<UserBean> serchUserInfo(String username, Integer deptId) throws Exception;

    /**
     * 随机数校验
     * @param randomNum
     * @return
     */
    public boolean isCheckRandom(String randomNum);


    int getDepartmentLevel(String deptId) throws Exception;

    boolean isRepeatSelectDepartment(UserBean userBean) throws Exception;

    boolean isRepeatUsername(UserBean userBean) throws Exception;

    List<UserBean> queryAllUserNoEncode() throws Exception;

    PageInfo<UserBean> queryPageListByWhereSplitGroup(Page page, UserBean userBean, ShiroUser shiroUser) throws Exception;


    /**获取财务部门拥有的部门
     * @param userBean
     * @return
     * @throws Exception
     */
    List<String> getUserPowerDept( UserBean userBean) throws Exception;

    /**
     * 用户新增
     * @return
     * @throws Exception
     */
    ReturnObject addUserInfo(UserBean userBean, String roles)throws  Exception;

    ReturnObject deleteUserInfo(Integer id)throws  Exception;


    PageInfo<UserBean> queryPageListByExample(Page page, UserBean record) throws Exception;

}
