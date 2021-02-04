package com.chenyi.auth.springbootshiro.shiro;

import com.chenyi.auth.springbootshiro.config.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author liuykdev
 */
public class UserSession {
    private static Logger logger= LoggerFactory.getLogger(UserSession.class);


    public static ShiroUser getUser(){
        Subject user = SecurityUtils.getSubject();
        if(user==null){
            return null;
        }
        ShiroUser shiroUser=(ShiroUser)user.getPrincipal();
        if (shiroUser == null) {
            return null;
        }
        return shiroUser;
    }
    public static Session getSession() {
        try {
            Subject user = SecurityUtils.getSubject();
            if (user == null) {
                return null;
            }
            return user.getSession();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    public static String getUsername() {
        try {
            ShiroUser shiroUser = getUser();
            if (shiroUser == null) {
                return null;
            }
            return shiroUser.getUsername();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    public static Integer getUserId() {
        try {
            ShiroUser shiroUser = getUser();
            if (shiroUser == null) {
                return null;
            }
            return shiroUser.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }
    public static String getMobile() {
        ShiroUser shiroUser = getUser();
        if (shiroUser == null) {
            return null;
        }
        return shiroUser.getPhoneNumber();
    }
}
