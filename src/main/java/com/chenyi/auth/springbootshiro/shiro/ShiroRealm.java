package com.chenyi.auth.springbootshiro.shiro;

import com.chenyi.auth.springbootshiro.bean.UserBean;
import com.chenyi.auth.springbootshiro.config.ShiroUser;
import com.chenyi.auth.springbootshiro.service.MenuService;
import com.chenyi.auth.springbootshiro.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShiroRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;


    public ShiroRealm() {
        // 认证缓存开启
        super.setAuthenticationCachingEnabled(false);
        super.setAuthenticationCacheName("ChenYi-APPLIACTION-SYSTEM-AUTH_REALM_CACHE");
        // 授权缓存开启
        super.setAuthorizationCachingEnabled(true);
        super.setAuthorizationCacheName("ChenYi-APPLIACTION-SYSTEM-POWER_REALM_CACHE");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principalCollection);
            SecurityUtils.getSubject().logout();
            return null;
        }
        ShiroUser userUtils = UserSession.getUser();

        Integer userId = userUtils.getId();
        if (userId==null) {
            return null;
        }
        // 添加角色及权限信息
        SimpleAuthorizationInfo sazi = new SimpleAuthorizationInfo();
        try {
            sazi.addStringPermissions(menuService.queryAllRole(userId));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

        return sazi;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken user = (UsernamePasswordToken) token;
        String password = new String((char[]) token.getCredentials());
        UserBean userBean = new UserBean();
        userBean.setUsername(user.getUsername());
        ShiroUser userUtils =null;
        try {
            //查询用户
            UserBean getUser = null;
            try {
                getUser = userService.queryListByWhere(userBean).get(0);
            } catch (Exception e) {
                logger.error("查询用户出错");
                throw new AuthenticationException(e);
            }
            if(getUser==null){
                return null;
            }
            //解密用户密码
            //String decryptPassword = desUtil.decrypt(getUser.getPassword());


            String s = new Md5Hash(getUser.getPassword()).toString();


            logger.info("数据库查询密码MD5加密后:"+s);
            logger.info(user.getPassword().toString());
            String pwd=String.valueOf(user.getPassword());
            if(!s.equals(String.valueOf(user.getPassword()))){
                throw new AuthenticationException("用户名/密码不正确");
            }
            if("0".equals(getUser.getUserState())){
                throw  new LockedAccountException("您的账号已被禁用，暂时无法登陆.");
            }
            if(StringUtils.isEmpty(getUser.getDeptId())){
                throw new NoHaveDeptException();

            }
            userUtils=new ShiroUser(getUser);

            String deptId = getUser.getDeptId();
            userUtils.setDeptId(deptId);
//            TbDepartment tbDepartment = new TbDepartment();
//
//            tbDepartment.setId(Integer.valueOf(deptId));
//            TbDepartment result = null;
//            try {
//                result = tbDepartmentService.queryOne(tbDepartment);
//                Integer level = result.getLevel();
//                userUtils.setDeptId(result.getId());
//                userUtils.setDeptLevel(level);
//            } catch (Exception e) {
//                throw new NoHaveDeptException();
//            }




            logger.info("认证通过");
        } catch (AuthenticationException e) {
            logger.error("查询用户出现错误",e);
            throw e;
        }catch (Exception e){
            logger.error("出错",e);
            throw e;
        }

        userUtils.setPassword(new Md5Hash(userUtils.getPassword()).toHex());
        if(userUtils!=null){
            AuthenticationInfo simpleAuthorizationInfo = new SimpleAuthenticationInfo(userUtils,password,getName());
            return simpleAuthorizationInfo;

        }
        return null;
    }
}
