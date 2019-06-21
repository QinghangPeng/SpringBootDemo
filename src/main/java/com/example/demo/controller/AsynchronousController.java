package com.example.demo.controller;

import com.example.demo.Response;
import com.example.demo.service.AsynchronousService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AsynchronousController
 * @Description
 * @Author jackson
 * @Date 2019/5/20 16:11
 * @Version 1.0
 **/
@RestController
@Api("异步请求")
@EnableAsync
@Slf4j
public class AsynchronousController {

    @Autowired
    private AsynchronousService service;

    @RequestMapping(value = "testAsynch",method = RequestMethod.GET)
    public Response testAsynch() {
        List<String> list = new ArrayList<>();
        String s = service.testAsynch();
        list.add(s);
        log.info("=========>" + Thread.currentThread().getName());
        return Response.success("异步，正在解析......");
    }

    @ApiOperation(value = "异步请求：printSomething方法不影响主线程的返回",notes = "适合发起一个请求，给用户迅速反应，然后在后台继续运算的操作")
    @RequestMapping(value = "testExecutor",method = RequestMethod.GET)
    public Response testExecutor() {
        List<String> list = new ArrayList<>();
        String s = service.getName();
        list.add(s);
        log.info("=========>" + Thread.currentThread().getName());
        return Response.success(list);
    }

}
