package com.lizq.school.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import java.io.IOException;
/**
 * Created by lizq on 2017/8/16.
 */
@Service
@PropertySource("classpath:redis.properties")
public class RedisService {
    private Environment env;
    private JedisPool jedisPool;
    private ObjectMapper objectMapper;

    private static final int timeout = 1000;

    //有参构造方法注入对象
    @Autowired
    public RedisService(Environment env) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(2);
        config.setMinIdle(0);
        config.setBlockWhenExhausted(true);
        jedisPool = new JedisPool(config, env.getProperty("redis.dev.host"),
                    Integer.parseInt(env.getProperty("redis.dev.port")), timeout);
        //判断操作系统
        String os= System.getProperty("os.name");

        //如果是Window系统则调用本地服务器的地址
        if (os.startsWith("Windows")) {
            System.out.println("当前是window系统");
            jedisPool = new JedisPool(config, env.getProperty("redis.dev.host"),
                    Integer.parseInt(env.getProperty("redis.dev.port")), timeout,
                    env.getProperty("redis.dev.pwd"));

       }
        //如果是其他系统则调用云服务器
       else {
            jedisPool = new JedisPool(config, env.getProperty("redis.prod.host"),
                    Integer.parseInt(env.getProperty("redis.prod.port")), timeout,
                    env.getProperty("redis.prod.pwd"));
        }
        objectMapper = new ObjectMapper();
    }

    //获取jedis对象
    public Jedis getResource() {
        return jedisPool.getResource();
    }

    //添加缓存数据
    public void set(String key, String value) {
        try (Jedis jedis = getResource()) {
            jedis.set(key, value);
        }
    }

    //添加缓存数据，带过期时间
    public void set(String key, String value, int expireInSeconds) {
        try (Jedis jedis = getResource()) {
            jedis.set(key, value);
            jedis.expire(key, expireInSeconds);
        }
    }

    //添加对象到缓存，带过期时间
    public void set(String key, Object obj, int expireInSeconds) {
        try (Jedis jedis = getResource()) {
            jedis.set(key, objectMapper.writeValueAsString(obj));
            jedis.expire(key, expireInSeconds);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    //根据key获取对应的value
    public String get(String key) {
        try (Jedis jedis = getResource()) {
            return jedis.get(key);
        }
    }

    //根据key获取对象
    public <T> T get(String key, Class<T> classOfT) {
        T obj = null;
        try (Jedis jedis = getResource()) {
            String value = jedis.get(key);
            if (value != null) {
                obj = objectMapper.readValue(value, classOfT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    //删除key
    public void del(String key) {
        try (Jedis jedis = getResource()) {
            jedis.del(key);
        }
    }


}
