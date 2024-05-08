package org.kartishan.bookservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/book")
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "ura";
    }
}
