package com.example.demo.controller;

import com.example.demo.Response;
import com.example.demo.service.KafkaService;
import com.example.demo.service.MysqlService;
import com.example.demo.vo.Page;
import com.example.demo.vo.Vehicle;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api("swagger-Demo")
public class SwaggerController {

    @Autowired
    private MysqlService mysqlService;

    @Autowired
    private KafkaService kafkaService;

    @ApiOperation(value = "sayHello")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String showSlogan() {
        return "Hello World!";
    }


    @ApiOperation(value = "mysql")
    @RequestMapping(value = "/basicMysqlInfo", method = RequestMethod.GET)
    /**@ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex",value = "第几页", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", paramType = "query")
    })*/
    /**@RequestMapping(value = "/basicMysqlInfo/{triggerGroup}", method = RequestMethod.GET)*/
    public Response basicMysqlInfo(@ApiParam("触发器组") @RequestParam(value = "triggerGroup") String triggerGroup
                                   /*@PathVariable("触发器组") String triggerGroup*/
                                   /*@ApiIgnore Page page*/) {
        return Response.success(mysqlService.getTriggerName(triggerGroup));
    }

    @ApiOperation(value = "kafka")
    @RequestMapping(value = "/basicKafkaInfo", method = RequestMethod.POST)
    public Response testKafka(@RequestBody Vehicle vehicle) {
        kafkaService.sendVehInfo("test_topic",vehicle);
        return Response.success();
    }

}
