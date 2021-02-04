package com.chenyi.auth.springbootshiro.config;


import com.chenyi.auth.springbootshiro.shiro.ShiroRealm;
import com.chenyi.auth.springbootshiro.shiro.ShiroRedisCacheManager;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@Order(3)
public class ShiroConfig {
    private Logger logger = LoggerFactory.getLogger(ShiroConfig.class);


    @Value("${shiro.login.url}")
    private String loginUrl;

    @Value("${shiro.unauthorized.url}")
    private String unauthorizedUrl;

    @Value("${shiro.cookie.path}")
    private String cookiePath;

    @Value("${shiro.cookie.name}")
    private String cookieName;
    /** */
    @Value("${shiro.anon.url}")
    private String anonurl;

    @Value("${shiro.active.session.cache.name}")
    private String shiroActiveSessionCacheName;


    /**
     * @param shiroCacheManager
     * @return
     */
    @Bean(name = "securityManager")
    public RealmSecurityManager securityManager(
            @Qualifier("shiroRealm") ShiroRealm shiroRealm,
            AbstractCacheManager shiroCacheManager,
            DefaultWebSessionManager sessionManager) {
        logger.info("创建Shiro安全管理器---start");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        securityManager.setRealm(shiroRealm);
        securityManager.setSessionManager(sessionManager);

        securityManager.setCacheManager(shiroCacheManager);
        logger.info("创建Shiro安全管理器---over");
        return securityManager;
    }


    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager(
            SimpleCookie shareSessionCookie, EnterpriseCacheSessionDAO shiroSessionDao) {
        logger.info("创建Shiro 回话管理器---star");
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setGlobalSessionTimeout(1000*60*60*24*2);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        shiroSessionDao.setActiveSessionsCacheName(shiroActiveSessionCacheName);
        sessionManager.setSessionDAO(shiroSessionDao);
        sessionManager.setSessionIdCookie(shareSessionCookie);
        logger.info("创建Shiro 回话管理器---over");
        return sessionManager;
    }

    @Bean(name = "shareSessionCookie")
    public SimpleCookie shareSessionCookie() {
        logger.info("设置Cookie--statr");
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setPath(cookiePath);
        simpleCookie.setName(cookieName);
        simpleCookie.setMaxAge(60*60*24*2);
        simpleCookie.setHttpOnly(false);
        logger.info("设置Cookie--over");
        return simpleCookie;
    }

    @Bean(name = "shiroSessionDao")
    public EnterpriseCacheSessionDAO shiroSessionDao() {
        return new EnterpriseCacheSessionDAO();
    }



    /**
     * 单机
     *
     * @return
     */
    @ConditionalOnProperty(name = "shiro.model", havingValue = "0")
    @Bean(name = "shiroCacheManager")
    public AbstractCacheManager shiroMemoryCacheManager() {
        logger.info("Shiro Model :单机");
        return new MemoryConstrainedCacheManager();
    }



    /**
     * 集群
     *
     * @return
     */
    @ConditionalOnProperty(name = "shiro.model", havingValue = "1")
    @Bean(name = "shiroCacheManager")
    public AbstractCacheManager shiroRedisCacheManager(RedissonClient redissonClient) {
        logger.info("Shiro Model :集群");
        ShiroRedisCacheManager shiroRedisCacheManager = new ShiroRedisCacheManager();
        shiroRedisCacheManager.setRedissonClient(redissonClient);

        return shiroRedisCacheManager;
    }



    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(RealmSecurityManager securityManager) {
        logger.info("shiroFilter---start");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        Map<String, String> filterUrls = new LinkedHashMap<>();
        String[] noSessionUrlsArray = anonurl.split(",");
        for (String url : noSessionUrlsArray) {
            filterUrls.put(url, "anon");
        }
        filterUrls.put("/logout", "logout");
        filterUrls.put("/**", "authc");
        Set<Map.Entry<String, String>> entries = filterUrls.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            logger.info("URL:" + entry.getKey() + ",Type:" + entry.getValue());
        }
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterUrls);
        logger.info("shiroFilter---end");
        return shiroFilterFactoryBean;
    }


    /**
     * 注解的权限控制
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            SecurityManager securityManager) {
        logger.info("创建Spring 基于注解控制Shiro权限拦截---start");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        logger.info("创建Spring 基于注解控制Shiro权限拦截---over");
        return authorizationAttributeSourceAdvisor;
    }


}
