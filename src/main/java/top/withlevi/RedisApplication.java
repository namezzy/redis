package top.withlevi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
         System.out.println("hello");
        SpringApplication.run(RedisApplication.class, args);
    }

}
