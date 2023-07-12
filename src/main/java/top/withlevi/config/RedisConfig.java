package top.withlevi.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * Created by Levi Zhao on 7/4/2023 10:37 AM
 *
 * @Author Levi
 */

@Configuration
@EnableCaching
public class RedisConfig {

    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }


                return sb.toString();
            }
        };
    }
}
