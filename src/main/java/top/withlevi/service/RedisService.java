package top.withlevi.service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;


/**
 * Created by Levi Zhao on 7/5/2023 1:57 PM
 *
 * @Author Levi
 */

@Service
public class RedisService {
    private Logger logger = LoggerFactory.getLogger(RedisService.class);


    @Autowired
    private RedisTemplate redisTemplate;


    /**
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
            logger.error("set error: key {}, value {}", key, value, e);

        }

        return result;
    }


    /**
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
     * @param key
     * @param value
     * @param expireTime
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
            logger.error("set error: key {}, value {}, expireTime {}", key, value, expireTime, e);
        }

        return result;
    }


    /**
     * @param key
     * @return
     */

    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }


}
