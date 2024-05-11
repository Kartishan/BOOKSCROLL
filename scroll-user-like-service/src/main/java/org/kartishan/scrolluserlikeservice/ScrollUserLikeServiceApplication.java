package org.kartishan.scrolluserlikeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ScrollUserLikeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrollUserLikeServiceApplication.class, args);
    }

}
