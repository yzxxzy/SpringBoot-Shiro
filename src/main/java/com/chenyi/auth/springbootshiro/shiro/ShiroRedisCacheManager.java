package com.chenyi.auth.springbootshiro.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author liuykdev
 */
public class ShiroRedisCacheManager extends AbstractCacheManager {
	private Logger logger= LoggerFactory.getLogger(ShiroRedisCacheManager.class);
    private RedissonClient redissonClient;
	@Override
	protected Cache createCache(String cacheName) throws CacheException {
		logger.info("<-------创建Shiro缓存对象-RedisonCache------->"+cacheName);
		return new ShiroRedisCache<String, Object>(cacheName,redissonClient);
	}
	public RedissonClient getRedissonClient() {
		return redissonClient;
	}
	public void setRedissonClient(RedissonClient redissonClient) {
		logger.info("<-------赋值Redisson对象=》Shiro使用------->");
		this.redissonClient = redissonClient;
	}

}
