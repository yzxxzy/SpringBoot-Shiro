package com.chenyi.auth.springbootshiro.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;



public class ShiroRedisCache<K, V>  implements Cache<K, V> {
	private Logger logger= LoggerFactory.getLogger(ShiroRedisCache.class);
	private String name;
	private RedissonClient redissonClient;

	
    private RMap<Object, Object> mapCache;
	

	public ShiroRedisCache(String name, RedissonClient redissonClient) {
		this.name = name;
		this.redissonClient = redissonClient;
		mapCache=redissonClient.getMap(name);
	}

	
	
	
	
	@Override
	public V get(K key) {
		try {
			if (key == null) {
				return null;
			} else {
			  V value=  (V)  mapCache.get(key);

 			  return   value;
			}
		} catch (Exception e) {
			logger.error("获取出错",e);
			throw  new CacheException(e);
		}


	}

	@Override
	public V put(K key, V value) throws CacheException {

		try {
		    mapCache.fastPut(key, value);
			return value;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CacheException(e);
		}
	}

	@Override
	public V remove(K key) throws CacheException {

		try {
		    mapCache.fastRemove(key);
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CacheException(e);
		}
	}

	@Override
	public void clear() throws CacheException {

		try {
		    mapCache.delete();
		   // mapCache=null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CacheException(e);
		}
	}

	@Override
	public int size() {
		try {
		    
		    return mapCache.size();
		    
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CacheException(e);
		}
	}

	
	@Override
	public Set<K> keys() {
		try {
		    
		    Set<K> keys = (Set<K>) mapCache.keySet();
			return keys;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	
	@Override
	public Collection<V> values() {
		try {
		    Collection<V> values = (Collection<V>) mapCache.values();
			return values;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}




}
