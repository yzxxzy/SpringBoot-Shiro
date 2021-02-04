package com.chenyi.auth.springbootshiro.service;


import com.chenyi.auth.springbootshiro.bean.BaseBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author liuykdev
 */
public interface MapperService<T extends BaseBean> {

    /**aa
     * @param id
     * @return
     * @throws Exception
     */
    T queryById(Long id)throws Exception;

    /**a
     * @return
     * @throws Exception
     */
     List<T> queryAll()throws Exception;


    /**aaa
     * @param record
     * @return
     * @throws Exception
     */
    T queryOne(T record)throws Exception;

    /**aa
     * @param record
     * @return
     * @throws Exception
     */
     List<T> queryListByWhere(T record)throws Exception;


    /**分页
     * @param page
     * @param record
     * @return
     * @throws Exception
     */
     PageInfo<T> queryPageListByWhere(Page page, T record)throws Exception;

    /**保存
     * @param record
     * @return
     * @throws Exception
     */
     Integer save(T record)throws Exception;
    /**
     * 保存非空
     * @param record
     * @return
     * @throws Exception
     */
     Integer saveSelective(T record)throws Exception;
    /**
     * 更新实体
     * @param record
     * @return
     * @throws Exception
     */
     Integer update(T record)throws Exception;
    /**
     * 更新非空
     * @param record
     * @return
     * @throws Exception
     */
    Integer updateSelective(T record)throws Exception;
    /**
     * 根据id删除
     * @param key
     * @return
     * @throws Exception
     */
     Integer deleteById(Object key)throws Exception;
    /**
     * 批量删除
     * @param clazz
     * @param property
     * @param values
     * @return
     * @throws Exception
     */
     Integer deleteByIds(Class clazz, String property, List<String> values)throws Exception;
    /**
     * 根据where条件删除
     * @param record
     * @return
     * @throws Exception
     */
     Integer deleteByWhere(T record)throws Exception;

     List<T> queryListByWhereByCode(T record,String filed)throws Exception;



     //List<T> queryListByExample(Example example);

}
