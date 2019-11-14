package com.learn.springboot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/hello")
public class HelloController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String sayHello() {
        return "hello "+System.currentTimeMillis();
    }
}
