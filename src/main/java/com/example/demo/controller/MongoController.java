package com.example.demo.controller;

import com.example.demo.Response;
import com.example.demo.service.MongoService;
import com.example.demo.vo.mongoVo.IndexReq;
import com.example.demo.vo.mongoVo.Weather;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName MongoController
 * @Description
 * @Author jackson
 * @Date 2019/7/30 11:24
 * @Version 1.0
 **/
@RestController
@Api("mongo相关方法")
@RequestMapping("/mongo")
public class MongoController {

    @Autowired
    private MongoService service;

    @ApiOperation(value = "mongo基本查询")
    @RequestMapping(value = "/basicMongoInfo", method = RequestMethod.GET)
    public Response basicMongoInfo(@ApiParam("查询车辆数") @RequestParam Integer pageSize) {
        return Response.success(service.basicMongoInfo(pageSize));
    }

    @ApiOperation("利用mongotemplate自带api创建带索引的mongo表")
    @PostMapping("/createCol")
    public Response createCol(@RequestBody IndexReq indexReq) {
        service.createColWithIndex(indexReq);
        return Response.success();
    }

    @ApiOperation("利用实体类和注解创建带索引的mongo表")
    @PostMapping("/createColWithObj")
    public Response createColWithObj(@RequestBody Weather weather) {
        service.createColWithObje(weather);
        return Response.success();
    }

    @ApiOperation("新增或更新某条数据")
    @PostMapping("/saveOrUpdate")
    public Response saveOrUpdate(@RequestBody Weather weather) {
        service.saveOrUpdate(weather);
        return Response.success();
    }

    @ApiOperation("查询车辆数据")
    @GetMapping("getVehStatusInfoByTime")
    public Response getVehStatusInfoByTime(@ApiParam("开始时间") @RequestParam(value = "startTime") Long startTime,
                                           @ApiParam("结束时间") @RequestParam(value = "endTime") Long endTime) {
        return Response.success(service.getVehStatusInfoByTime(startTime,endTime));
    }
}
