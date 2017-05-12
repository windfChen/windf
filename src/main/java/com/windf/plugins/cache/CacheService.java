package com.windf.plugins.cache;

public interface CacheService {
	  /**
     * 放入缓存 默认缓存块,永不过期
     * @param key 缓存key
     * @param val 值
     * @return
     */
    boolean put(String key, Object val);

    /**
     * 放入缓存 默认缓存块
     * @param key  键
     * @param val  值
     * @param expire 过期时间（秒）
     * @return
     */
    boolean put(String key, Object val, int expire);
    
	/**
	 * 存入缓存，无限制时间
	 * @param cacheBlack	哪个缓存块
	 * @param key	缓存的key
	 * @param value	缓存的值
	 * @return 返回之前是否有缓存
	 */
	boolean put(String cacheBlack, String key, Object value);
	/**
	 * 存入缓存，无限制时间
	 * @param cacheBlack	哪个缓存块
	 * @param key	缓存的key
	 * @param value	缓存的值
	 * @param time	缓存的时间
	 * @return	返回之前是否有缓存
	 */
	boolean put(String cacheBlack, String key, Object value, int expire);
	/**
	 * 根据 key 获取缓存 默认缓存块
	 * @param key
	 * @return
	 */
	Object get(String key);
	/**
	 * 从缓存中取值
	 * @param cacheBlack 哪个缓存块
	 * @param key 缓存的key
	 * @return 缓存的值，如果没有或者过期则返回null
	 */
	Object get(String cacheBlack, String key);
    /**
     * 移除指定缓存 默认缓存块
     * @param key   键
     * @return
     */
    boolean remove(String key);

    /**
     * 移除指定缓存
     * @param cacheBlock 缓存块
     * @param key   键
     * @return
     */
    boolean remove(String cacheBlock, String key);
	/**
	 * 清空缓存
	 * @param cacheBlack 哪个缓存块
	 * @param key 缓存的key
	 * @return 返回之前是否有缓存
	 */
    boolean clean(String cacheBlack, String key);

    /**
     * 判断缓存是否存在
     * 默认缓存块
     * @param cacheKey
     * @return
     */
    boolean exists(String cacheKey);

    /**
     * 判断缓存是否存在
     * @param cacheBlock
     * @param cacheKey
     * @return
     */
    boolean exists(String cacheBlock, String cacheKey);
}
