package com.windf.plugins.cache.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.whaty.framework.cache.core.model.Cache;
import com.whaty.framework.cache.core.service.CacheService;

@Service("cacheService")
public class WhatyCacheServiceImpl implements com.windf.plugins.cache.CacheService {

    private static final Logger logger = Logger.getLogger(WhatyCacheServiceImpl.class);
    @Resource(name = "cacheService")
    private CacheService cacheService;
    private static final String  DEFAULTCACHE ="com.ucenter.defaultCache";
    private static Cache cache;

    private static boolean dev = false;

    /**
     * 设置默认缓存块
     */
    private void initDefaultCache(){
        if(null != cacheService && null == cache ){
            cache = cacheService.getCache(DEFAULTCACHE);
        }
    }

    /**
     * 初始化站点信息缓存块
     */
    private Cache getCache(String cacheBlock){
        if(null != cacheService){
            Cache cache = cacheService.getCache(cacheBlock);
            logger.debug(ReflectionToStringBuilder.toString(cache));
            return cache;
        }
        return null;
    }


    @Override
    public boolean put(String key, Object val) {
        return put(key, val, 0);
    }

    @Override
    public boolean put(String key, Object val, int expire) {
        return put(null, key, val, expire);
    }

	@Override
	public boolean put(String cacheBlack, String key, Object value) {
		 return put(cacheBlack, key, value, 0);
	}
    
    @Override
    public boolean put(String cacheBlock, String key, Object val, int expire){
        logger.debug(key + ReflectionToStringBuilder.toString(val));
        if(StringUtils.isBlank(cacheBlock)){
            //使用默认缓存块
            initDefaultCache();
            return cache.put(key, val, expire);
        } else {
            Cache cache = getCache(cacheBlock);
            return cache.put(key, val, expire);
        }
    }

    @Override
    public boolean remove(String key) {
        return remove(null, key);
    }

    @Override
    public boolean remove(String cacheBlock, String key) {
        if(StringUtils.isBlank(cacheBlock)){
            initDefaultCache();
            Object result = cache.remove(key);
            if(null != result){
                return true;
            }
            return false;
        }else{
            Cache cache = getCache(cacheBlock);
            Object val = cache.remove(key);
            if(null != val){
                return true;
            }
            return false;
        }
    }

    @Override
    public Object get(String key) {
        return get(null, key);
    }

    @Override
    public Object get(String cacheBlock, String key) {
        if(dev){
            return null;
        }
        Object val = null;
        if(StringUtils.isBlank(cacheBlock)){
            initDefaultCache();
            val = cache.get(key);
        }else{
            Cache cache = getCache(cacheBlock);
            val = cache.get(key);
        }
        logger.debug(key + ":" + ReflectionToStringBuilder.toString(val));
        return val;
    }

    @Override
    public boolean exists(String cacheKey){
        return exists(null, cacheKey);
    }

    @Override
    public boolean exists(String cacheBlock, String cacheKey){
        try {
            if(StringUtils.isBlank(cacheBlock)){
                initDefaultCache();
                return cache.exists(cacheKey);
            }else{
                Cache cache = getCache(cacheBlock);
                if(null != cache){
                    return cache.exists(cacheKey);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


	@Override
	public boolean clean(String cacheBlack, String key) {
		// TODO Auto-generated method stub
		return false;
	}
}
