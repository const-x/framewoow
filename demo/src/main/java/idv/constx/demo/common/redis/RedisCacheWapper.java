package idv.constx.demo.common.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class RedisCacheWapper {

	private JedisPool pool = null;
	
	


	public JedisPool getPool() {
		return pool;
	}

	public void setPool(JedisPool pool) {
		this.pool = pool;
	}

	public boolean exists(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.exists(key);
		} finally {
			pool.returnResource(jedis);
		}
	}
	
	public <T> T pop(String key){
		Jedis jedis = pool.getResource();
		try {
			Object val = SerializeUtil.unserialize(jedis.rpop(key.getBytes()));
			return (T) val;
		} finally {
			pool.returnResource(jedis);
		}
	}
	

	public <T> void push(String key,Collection<T> objs){
		Jedis jedis = pool.getResource();
		byte[] keybyte = key.getBytes();
		try {
			for(T value : objs){
				byte[] val = SerializeUtil.serialize(value);
				jedis.lpush(keybyte, val);
			}
		} finally {
			pool.returnResource(jedis);
		}
	}

	public String get(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.get(key);
		} finally {
			pool.returnResource(jedis);
		}
	}

	public void put(String key, String value) {
		this.putex(key, null, value);
	}

	public void putex(String key, Integer seconds, String value) {
		Jedis jedis = pool.getResource();
		try {
			if (seconds == null || seconds < 1) {
				jedis.set(key, value);
			} else {
				jedis.setex(key, seconds, value);
			}
		} finally {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 设置秒过期
	 * 
	 * @return 1：设置成功，0：key不存在或设置失败
	 */
	public Long expire(String key, int seconds) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.expire(key, seconds);
		} finally {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 设置毫秒过期
	 * 
	 * @return 1：设置成功，0：key不存在或设置失败
	 */
	public Long pexpire(String key, int milliseconds) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.pexpire(key, milliseconds);
		} finally {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 存储指定的value
	 * 
	 * @param seconds 过期时间（秒），如果时间小于1，表示永不过期
	 */
	public void putValueWrapper(String key, Object value, Integer seconds) {
		Jedis jedis = pool.getResource();
		try {
			byte[] val = SerializeUtil.serialize(value);
			if (seconds == null || seconds < 1) {
				jedis.set(key.getBytes(), val);
			} else {
				jedis.setex(key.getBytes(), seconds, val);
			}
		} finally {
			pool.returnResource(jedis);
		}
	}

	/** 存储指定的value */
	public void putValueWrapper(String key, Object value) {
		putValueWrapper(key, value, null);
	}

	/**
	 * 获取数据cache数据
	 * 
	 * @param key 值
	 * @return null 无缓存，其他有缓存
	 */
	public <T> T getValueWrapper(String key) {
		Jedis jedis = pool.getResource();
		try {
			byte[] keyBite = key.getBytes();
			if (!jedis.exists(keyBite)) {
				return null;
			}
			byte[] valBite = jedis.get(keyBite);
			return (T) SerializeUtil.unserialize(valBite);
		} finally {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 获取数据cache数据列表
	 * 
	 * @param pattern 值
	 * @return null 无缓存，其他有缓存
	 */
	public <T> List<T> getValueWrappers(String pattern) {
		Jedis jedis = pool.getResource();
		try {
			Set<byte[]> keys = jedis.keys(pattern.getBytes());
			List<T> list = new ArrayList<>(keys.size());
			for (byte[] bs : keys) {
				byte[] valBite = jedis.get(bs);
				T value = (T) SerializeUtil.unserialize(valBite);
				list.add(value);
			}
			return list;
		} finally {
			pool.returnResource(jedis);
		}
	}

	public void del(String key) {
		Jedis jedis = pool.getResource();
		try {
			jedis.del(key);
		} finally {
			pool.returnResource(jedis);
		}
	}
	
	/**
	 * 当且仅当 key 不存在，将 key 的值设为 value ，并返回1；若给定的 key 已经存在，则 SETNX 不做任何动作，并返回0
	 * 可与del结合 作为同步锁使用
	 */
	public boolean getLock(String key){
		Jedis jedis = pool.getResource();
		try {
			Long res = jedis.setnx(key, "locked");
			if(res != null && res == 1)
				jedis.expire(key, 60 * 60);
			return (res != null && res == 1);
		} finally {
			pool.returnResource(jedis);
		}
	} 
	
	public void releaseLock(String key){
		Jedis jedis = pool.getResource();
		try {
			jedis.del(key);
		} finally {
			pool.returnResource(jedis);
		}
	} 

}
