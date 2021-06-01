package com.ian.redis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * 本類別詳細說明。
 * <p/>
 * Package: com.ian.utils <br>
 * File Name: RedisUtils <br>
 * <p/>
 * Purpose: <br>
 * 
 * @ClassName: com.ian.RedisUtils
 * @Description: RedisUtils 工具類
 * @Company: Team.
 * @author Ian
 * @version 1.0, April 01, 2021
 */
public class RedisUtils {

	@Autowired
	private RedisTemplate redisTemplate;

		/**
	 * 刪除對應的value
	 * 
	 * @param key
	 */
	public void delete(final String key) {
		if (hasKey(key)) {
			redisTemplate.delete(key);
		}
	}

		/**
	 * 判斷緩存中是否有對應的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasKey(final String key) {
		return redisTemplate.hasKey(key);
	}


	/**
	 * 批量刪除key
	 * 
	 * @param pattern
	 */
	public void deletePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 保存String資料
	 * 
	 * @Title: setStr
	 * @param key
	 * @param value
	 *
	 */
	public void setStr(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 讀取String資料
	 * 
	 * @Title: setStr
	 * @param key
	 *
	 */
	public String getString(String key) {
		return String.valueOf(redisTemplate.opsForValue().get(key));
	}

	/**
	 * 操作物件
	 * 
	 * @Title: setObject
	 * @param key
	 * @param obj
	 *
	 */
	public void setObject(String key, Object obj) {
		redisTemplate.opsForValue().set(key, obj);
	}

	/**
	 * 讀取物件
	 * 
	 * @Title: setObject
	 * @param key
	 *
	 */
	public Object setObject(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 保存List資料存儲
	 * 
	 * @param key
	 * @param listValue
	 * @return
	 */
	public void setList(String key, List<?> listValue) {
		redisTemplate.opsForList().leftPush(key, listValue);
	}

	/**
	 * 讀取List資料存儲
	 * 
	 * @param key
	 * @return
	 */
	public List<?> getList(String key) {
		return (List<?>) redisTemplate.opsForList().range(key, 0, -1);
	}

	/**
	 * 保存Map資料存儲
	 * 
	 * @param key
	 * @param mapValue
	 * @return
	 */
	public void setMap(String key, Map<Object, Object> mapValue) {
		redisTemplate.opsForHash().putAll(key, mapValue);
	}

	public Map<Object, Object> getMap(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 保存Set資料存儲
	 * 
	 * @param key
	 * @param setValue
	 * @return
	 */
	public void setSet(String key, Set<?> setValue) {
		redisTemplate.opsForSet().add(key, setValue);
	}

	/**
	 * 讀取Set資料存儲
	 * 
	 * @param key
	 * @return
	 */
	public Set<?> getSet(String key) {
		return (Set<?>) redisTemplate.opsForSet().members(key);
	}

	/**
	 * 批量刪除對應的value
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}





	/**
	 * 讀取緩存
	 * 
	 * @param key
	 * @return
	 */
	public Object get(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 寫入緩存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 寫入緩存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * redis的key 形式為： 表名:主鍵名:主鍵值:列名
	 *
	 * @param tableName     表名
	 * @param majorKey      主鍵名
	 * @param majorKeyValue 主鍵值
	 * @param column        列名
	 * @return
	 */
	public static String getKeyWithColumn(String tableName, String majorKey, String majorKeyValue, String column) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(tableName).append(":");
		buffer.append(majorKey).append(":");
		buffer.append(majorKeyValue).append(":");
		buffer.append(column);
		return buffer.toString();
	}

	/**
	 * redis的key 形式為： 表名:主鍵名:主鍵值
	 *
	 * @param tableName     表名
	 * @param majorKey      主鍵名
	 * @param majorKeyValue 主鍵值
	 * @return
	 */
	public static String getKey(String tableName, String majorKey, String majorKeyValue) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(tableName).append(":");
		buffer.append(majorKey).append(":");
		buffer.append(majorKeyValue).append(":");
		return buffer.toString();
	}

}
