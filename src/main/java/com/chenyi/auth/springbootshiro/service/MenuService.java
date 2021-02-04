package com.chenyi.auth.springbootshiro.service;

import com.chenyi.auth.springbootshiro.bean.MenuBean;
import com.chenyi.auth.springbootshiro.bean.Tree;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liuykdev
 */
public interface MenuService extends MapperService<MenuBean> {
    /**得到用户所有quanxian
     * @param userId
     * @return
     * @throws Exception
     */
    Set<String> queryAllRole(Integer userId) throws Exception;

    /**查询用户菜单
     * @param userid
     * @return
     * @throws Exception
     */
    List<MenuBean> queryUserMenu(Integer userid) throws Exception;

    /**查询用户菜单Tree
     * @param userid
     * @return
     * @throws Exception
     */
    List<MenuBean> getMenuTree(Integer userid) throws Exception;

    /**查询用户Tree并且封装成JSTree
     * @param params
     * @return
     */
    List<Tree<MenuBean>> getMenuJsTree(Map<String,Object> params);


    PageInfo<MenuBean> queryPageListByExample(Page page, MenuBean record);

}
