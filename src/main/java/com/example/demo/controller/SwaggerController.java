package com.example.demo.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerController {

    @ApiOperation(value = "sayHello")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String showSlogan() {
        return "Hello World!";
    }

}
