package com.ascendingdc.training.project2020.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloWorldController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
//    @ResponseBody
    public String retrieveHelloWorld() {
        return "hello123";
    }

    @GetMapping(value = "/hello/{name}")
    @ResponseBody
    public String sayHelloWorld(@PathVariable("name") String username) {
        return "Welcome   " + username;
    }

}
