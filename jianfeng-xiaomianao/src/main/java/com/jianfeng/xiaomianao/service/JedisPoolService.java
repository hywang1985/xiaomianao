package com.jianfeng.xiaomianao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.exception.XiaoMianAoException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class JedisPoolService {

    private static Logger logger = LoggerFactory.getLogger(JedisPoolService.class);

    @Value("${sharded.redis.store.server}")
    private String storeServer = null;

    @Value("${sharded.redis.store.pwd}")
    private String storePwd = "abc123";

    private Integer cachePoolTimeout = 4000;

    @Value("${sharded.redis.pool.max.active}")
    private Integer cachePoolMaxActive = 200;

    @Value("${sharded.redis.pool.max.idle}")
    private Integer cachePoolMaxIdle = 50;

    @Value("${sharded.redis.pool.max.wait}")
    private Long cachePoolMaxWait = 2000L;

    @Value("${sharded.redis.pool.test.on.borrow}")
    private Boolean cachePoolTestOnBorrow = false;

    private JedisPoolConfig jedisPoolConfig = null;

    private JedisPool jedisPool = null;

    public static final int DAY = 86400;

    public static final int WEEK = 604800;

    private void initJedisPoolConfig() {
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxActive(cachePoolMaxActive);
        jedisPoolConfig.setMaxIdle(cachePoolMaxIdle);
        jedisPoolConfig.setMaxWait(cachePoolMaxWait);
        jedisPoolConfig.setTestOnBorrow(cachePoolTestOnBorrow);
    }

    @PostConstruct
    public void init() {
        try {
            initJedisPoolConfig();
            String host = storeServer.split(":")[0];
            String port = storeServer.split(":")[1];
            jedisPool = new JedisPool(jedisPoolConfig, host, Integer.parseInt(port), cachePoolTimeout, storePwd);
            logger.info("cache 初始化成功");
        } catch (Exception e) {
            logger.error("cache 初始化失败", e);
        }
    }

    /**
     * 获取缓存服务器连接对象
     * 
     * @return
     */
    public Jedis getConn() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            logger.error("获取缓存连接出现异常", e);
        }
        return jedis;
    }

    public void closeConn(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    public boolean exists(String key) {
        Jedis j = null;
        try {
            j = getConn();
            return j.exists(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
        return false;
    }

    public void setex(String key, String value, int seconds) {
        Jedis j = null;
        try {
            j = getConn();
            j.setex(key, seconds, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
    }

    public void set(String key, String value) {
        Jedis j = null;
        try {
            j = getConn();
            j.set(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
    }

    public void hset(String key, String field, String value) {
        Jedis j = null;
        try {
            j = getConn();
            j.hset(key, field, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new XiaoMianAoException("hset出错");
        } finally {
            closeConn(j);
        }
    }

    public void hset(String key, String field, String value, int seconds) {
        Jedis j = null;
        try {
            j = getConn();
            j.hset(key, field, value);
            j.expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new XiaoMianAoException("hset出错");
        } finally {
            closeConn(j);
        }
    }

    public String hget(String key, String field) {
        Jedis j = null;
        try {
            j = getConn();
            if(j!=null){
                return j.hget(key, field);
            }
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new XiaoMianAoException("hget出错");
        } finally {
            closeConn(j);
        }
    }

    public List<String> hmget(String key, String... fields) {
        Jedis j = null;
        try {
            j = getConn();
            return j.hmget(key, fields);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
        return new ArrayList<String>();
    }

    public long hdel(String key, String... fields) {
        Jedis j = null;
        try {
            j = getConn();
            return j.hdel(key, fields);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new XiaoMianAoException("hdel出错");
        } finally {
            closeConn(j);
        }
    }

    public boolean hexists(String key, String field) {
        Jedis j = null;
        try {
            j = getConn();
            return j.hexists(key, field);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new XiaoMianAoException("hexists出错");
        } finally {
            closeConn(j);
        }
    }

    public String get(String key) {
        Jedis j = null;
        try {
            j = getConn();
            return j.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
        return null;
    }

    public List<String> get(String... key) {
        Jedis j = null;
        try {
            j = getConn();
            List<String> list = new ArrayList<String>();
            for (String k : key) {
                String v = j.get(k);
                if (StringUtils.isNotEmpty(v))
                    list.add(v);
            }
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
        return null;
    }

    public void del(String key) {
        Jedis j = null;
        try {
            j = getConn();
            j.del(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
    }

    public void del(String... keys) {
        Jedis j = null;
        try {
            j = getConn();
            j.del(keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
    }

    public void rpush(String key, String... values) {
        Jedis j = null;
        try {
            j = getConn();
            j.rpush(key, values);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
    }

    public void rpush(String key, int seconds, String... values) {
        Jedis j = null;
        try {
            j = getConn();
            j.rpush(key, values);
            j.expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
    }

    public long llen(String key) {
        Jedis j = null;
        try {
            j = getConn();
            return j.llen(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
        return -1;
    }

    public Long lpush(String key, String... values) {
        Jedis j = null;
        try {
            j = getConn();
            return j.lpush(key, values);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
        return null;
    }

    public void lpush(String key, int seconds, String... values) {
        Jedis j = null;
        try {
            j = getConn();
            j.lpush(key, values);
            j.expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
    }

    public List<String> lrange(String key, long start, long end) {
        Jedis j = null;
        try {
            j = getConn();
            List<String> list = j.lrange(key, start, end);
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
        return null;
    }

    public List<String> lpop(String key, int size) {
        Jedis j = null;
        try {
            j = getConn();
            List<String> list = null;
            for (int i = 0; i < size; i++) {
                String str = j.lpop(key);
                if (str == null)
                    return list;
                if (list == null)
                    list = new ArrayList<String>(size);
                list.add(str);
            }
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
        return null;
    }

    public Set<String> keys(String pattern) {
        Jedis j = null;
        try {
            j = getConn();
            return j.keys(pattern);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
        return null;
    }

    public String getStoreServer() {
        return storeServer;
    }

    public void setStoreServer(String storeServer) {
        this.storeServer = storeServer;
    }

    public Long counterIncr(String key, int expire) {
        Jedis j = null;
        try {
            Long count = null;
            j = getConn();
            count = j.incr(key);
            if (count == 1) {
                j.expire(key, expire);
            }
            return count;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
        return null;
    }

    public Set<String> hkeys(String key) {
        Jedis j = null;
        try {
            j = getConn();
            return j.hkeys(key);
        } catch (Exception e) {
            logger.error("hkeys出错", e);
        } finally {
            closeConn(j);
        }
        return null;
    }

    public void expire(String key, int seconds) {
        Jedis j = null;
        try {
            j = getConn();
            j.expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeConn(j);
        }
    }
}
