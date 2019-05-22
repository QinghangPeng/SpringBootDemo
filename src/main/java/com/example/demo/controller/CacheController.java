package com.example.demo.controller;

import com.example.demo.Response;
import com.example.demo.service.MysqlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CacheController
 * @Description
 * @Author jackson
 * @Date 2019/5/14 16:26
 * @Version 1.0
 **/
@RestController
@Api("springboot整合cache")
public class CacheController {

    @Autowired
    private MysqlService service;

    @ApiOperation(value = "cache")
    @RequestMapping(value = "/testCache", method = RequestMethod.GET)
    public Response testCache() {
        return Response.success(service.getQrtzLocks());
    }
}
