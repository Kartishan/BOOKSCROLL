package org.kartishan.bookcommentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BookCommentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookCommentServiceApplication.class, args);
    }

}
