package top.withlevi.service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
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


    /**
     * remove single key
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }


    /**
     * batch delete key
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }


    /**
     * batch delete with pattern
     *
     * @param pattern
     */

    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * hash set
     *
     * @param key
     * @param hashKey
     * @param value
     */

    public void hashSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }


    /**
     * list push
     *
     * @param k
     * @param v
     */
    public void push(String k, Object v) {
        ListOperations list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * list range
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */

    public List<Object> range(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, 1, l1);
    }


    /**
     * set add
     *
     * @param key
     * @param value
     */
    public void setAdd(String key, Object value) {
        SetOperations set = redisTemplate.opsForSet();
        set.add(key, value);
    }


    /**
     * set get
     *
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * ordered set add
     *
     * @param key
     * @param value
     * @param score
     */
    public void zAdd(String key, Object value, double score) {
        ZSetOperations zset = redisTemplate.opsForZSet();
        zset.add(key, value, score);

    }


    /**
     * rangeByScore
     *
     * @param key
     * @param score1
     * @param score2
     * @return
     */

    public Set<Object> rangeByScore(String key, double score1, double score2) {
        ZSetOperations zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, score1, score2);
    }


}
