package top.withlevi;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;
import top.withlevi.model.User;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Levi Zhao on 7/4/2023 10:44 AM
 *
 * @Author Levi
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisTemplate {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testString() {
        redisTemplate.opsForValue().set("Levi", "ityouknow");
        Assert.assertEquals("ityouknow", redisTemplate.opsForValue().get("Levi"));
    }


    @Test
    public void testObj() {
        User xmj = new User("xmj", 2, "xmj@gmail.com");
        ValueOperations<String, User> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("top.withlevi", xmj);
        User user = opsForValue.get("top.withlevi");
        System.out.println("user: " + user.toString());
    }

    @Test
    public void testExpire() throws InterruptedException {
        User wdx = new User("wdx", 30, "wdx@gmail.com");
        ValueOperations<String, User> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("expire", wdx, 100, TimeUnit.MILLISECONDS);
        Thread.sleep(1000);
        Boolean expire = redisTemplate.hasKey("expire");
        if (expire) {
            System.out.println("expire is true");
        } else {
            System.out.println("expire is false");
        }
    }

    @Test
    public void testDelete() {
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        redisTemplate.opsForValue().set("deleteKey", "hello");
        redisTemplate.delete("deleteKey");
        Boolean hasKey = redisTemplate.hasKey("deleteKey");
        if (hasKey) {
            System.out.println("hasKey is true");
        } else {
            System.out.println("hasKey is false");
        }

    }


    @Test
    public void testHash() {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put("hash", "you", "youvalue");
        String value = (String) hash.get("hash", "you");
        System.out.println("hash value: " + value);
    }


    @Test
    public void testList() {
        ListOperations<String, String> list = redisTemplate.opsForList();
        list.leftPush("list", "hello");
        list.leftPush("list", "world");
        list.leftPush("list", "xmj");
        List<String> values = list.range("list", 0, 2);
        for (String value : values) {
            System.out.println("list range: " + value);
        }
        String value = (String) list.leftPop("list");
        System.out.println("Get list value: " + value.toString());

    }


    @Test
    public void testSet() {
        String key = "set";
        SetOperations<String, String> set = redisTemplate.opsForSet();
        set.add(key, "it");
        set.add(key, "you");
        set.add(key, "xmj");
        set.add(key, "you");
        Set<String> values = set.members(key);
        for (String value : values) {
            System.out.println("Set value: " + value);
        }
    }


    @Test
    public void testDifferent() {
        SetOperations<String, String> set = redisTemplate.opsForSet();

        String key1 = "setMore1";
        String key2 = "setMore2";
        set.add(key1, "it");
        set.add(key1, "you");
        set.add(key1, "you");
        set.add(key1, "know");
        set.add(key2, "xx");
        set.add(key2, "know");
        Set<String> diffs = set.difference(key1, key2);
        for (String diff : diffs) {
            System.out.println("diffs set value: " + diff);
        }


    }


    @Test
    public void testUnions() {

        SetOperations<String, String> set = redisTemplate.opsForSet();
        String key3 = "setMore3";
        String key4 = "setMore4";

        set.add(key3, "it");
        set.add(key3, "you");
        set.add(key3, "xx");
        set.add(key4, "aa");
        set.add(key4, "bb");
        set.add(key4, "cc");

        Set<String> union = set.union(key3, key4);
        for (String s : union) {
            System.out.println("Union value: " + s);
        }


    }


    @Test
    public void testZset() {
        String key = "zset";
        redisTemplate.delete(key);
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        zset.add(key, "it", 1);
        zset.add(key, "you", 6);
        zset.add(key, "know", 4);
        zset.add(key, "xmj", 3);

        Set<String> zsets = zset.range(key, 0, 3);
        for (String s : zsets) {
            System.out.println("zset value: " + s);
        }

        Set<String> zsetB = zset.rangeByScore(key, 0, 3);
        for (String v : zsetB) {
            System.out.println("zsetB value: " + v);
        }

    }


}
