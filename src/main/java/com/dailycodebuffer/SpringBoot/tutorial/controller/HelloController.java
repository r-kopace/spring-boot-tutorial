package com.dailycodebuffer.SpringBoot.tutorial.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

//    @RequestMapping(value="/", method= RequestMethod.GET)


//    @GetMapping("/")
//    public String HelloWorld(){
//        return "Witaj Swiecie !!";
//    }

    @Value("${welcome.message}")
    private String welcomeMessage;

    @GetMapping("/")
    public String HelloWorld(){
        return welcomeMessage;
    }
}
