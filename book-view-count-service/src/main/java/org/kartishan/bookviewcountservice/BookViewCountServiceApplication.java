package org.kartishan.bookviewcountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BookViewCountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookViewCountServiceApplication.class, args);
    }

}
