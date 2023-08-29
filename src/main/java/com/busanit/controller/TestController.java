package com.busanit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test1")
    public String test1(){
        return "hello~";
    }

    @GetMapping("/test2")
    public String test2(){
        int a = 1;

        String ex = "";
        System.out.println("ex = " + ex);

        return "안녕하세요";

    }

}
