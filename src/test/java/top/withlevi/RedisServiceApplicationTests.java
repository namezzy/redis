package top.withlevi;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.withlevi.service.RedisService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by Levi Zhao on 7/6/2023 3:56 PM
 *
 * @Author Levi
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisServiceApplicationTests {

    @Resource
    private RedisService redisService;

    @Test
    public void testString() throws Exception {
        redisService.set("小米椒", "小米椒");
        Assert.assertEquals("小米椒", redisService.get("小米椒"));
    }


    @Test
    public void testExpire() throws Exception {
        redisService.set("Hello", "Redis", new Long(10));
        Thread.sleep(4000);
        boolean expire = redisService.exists("Hello");
        if (expire) {
            System.out.println("expire is true");
        } else {
            System.out.println("expire is false");
        }
    }

}
