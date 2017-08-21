package com.lizq.school.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lizq on 2017/8/14.
 */
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping(value="hello",method = RequestMethod.GET)
    public String hello(){
        System.out.println("进入到了SpringMVC");
        return "Hello SpringMVC!";
    }

}
