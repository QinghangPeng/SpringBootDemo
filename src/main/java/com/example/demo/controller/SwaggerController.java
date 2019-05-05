package com.example.demo.controller;

import com.example.demo.Response;
import com.example.demo.model.excel_model.VehicleEx;
import com.example.demo.service.KafkaService;
import com.example.demo.service.MysqlService;
import com.example.demo.util.ExcelExportUtil;
import com.example.demo.vo.Vehicle;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @RequestMapping(value = "/basicKafkaInfo/{topic}", method = RequestMethod.POST)
    public Response testKafka(@RequestBody Vehicle vehicle, @PathVariable String topic) {
        kafkaService.sendVehInfo(topic,vehicle);
        return Response.success();
    }

    @ApiOperation(value = "导出单个sheet的excel")
    @RequestMapping(value = "/writeExcel", method = RequestMethod.GET)
    public void writeExcel(@ApiIgnore HttpServletResponse response) throws IOException {
        List<VehicleEx> list = new ArrayList<>();
        for (int i=0;i<5;i++) {
            VehicleEx vehicle = new VehicleEx("vin1"+i,"deviceId1"+i,"牵引"+i,"4*2"+i,"J6P"+i,"460Ps"+i);
            list.add(vehicle);
        }
        String title = "车辆基本信息表";
        String sheetName = "车辆基本信息";
        ExcelExportUtil.writeExcel(response,list,title,sheetName,VehicleEx.class);
    }

    @ApiOperation(value = "导出多个sheet的excel")
    @RequestMapping(value = "/writeExcelWithSheets", method = RequestMethod.GET)
    public void writeExcelWithSheets(@ApiIgnore HttpServletResponse response) throws IOException{
        List<VehicleEx> list = new ArrayList<>();
        for (int i=0;i<5;i++) {
            VehicleEx vehicle = new VehicleEx("vin1"+i,"deviceId1"+i,"牵引"+i,"4*2"+i,"J6P"+i,"460Ps"+i);
            list.add(vehicle);
        }
        String title = "车辆基本信息表";
        String sheetName1 = "车辆基本信息1";
        String sheetName2 = "车辆基本信息2";
        String sheetName3 = "车辆基本信息3";
        ExcelExportUtil.writeExcelWithSheets(response,list,title,sheetName1,VehicleEx.class)
                .write(list,sheetName2,VehicleEx.class)
                .write(list,sheetName3,VehicleEx.class)
                .finish();
    }

}
