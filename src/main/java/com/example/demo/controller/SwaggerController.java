package com.example.demo.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.demo.Response;
import com.example.demo.model.excel_model.VehicleEx;
import com.example.demo.model.strategy_pattern.IOrderService;
import com.example.demo.model.strategy_pattern.impl.OrderServiceImpl;
import com.example.demo.service.KafkaService;
import com.example.demo.service.MongoService;
import com.example.demo.service.MysqlService;
import com.example.demo.util.ExcelExportUtil;
import com.example.demo.vo.OrderDTO;
import com.example.demo.vo.Vehicle;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api("swagger-Demo")
public class SwaggerController {

    @Autowired
    private MysqlService mysqlService;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private MongoService mongoService;

    @Autowired
    @Qualifier("orderServiceImpl")
    private IOrderService orderService;

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

    @ApiOperation(value = "mysqlStorage")
    @RequestMapping(value = "/mysqlStorage", method = RequestMethod.GET)
    public Response mysqlStorage(@ApiParam("类型：1,调用第一个存储过程;2,调用第二个存储过程") @RequestParam(value = "type") Integer type,
                                 @ApiParam("组id") @RequestParam(value = "rootId") Integer rootId) {
        return Response.success(mysqlService.getDataFromStorage(type,rootId));
    }

    @ApiOperation(value = "kafka")
    @RequestMapping(value = "/basicKafkaInfo/{topic}", method = RequestMethod.POST)
    public Response testKafka(@RequestBody Vehicle vehicle, @PathVariable String topic) throws Exception{
        kafkaService.sendVehInfo(topic,vehicle);
        return Response.success();
    }

    @ApiOperation(value = "导出单个sheet的excel")
    @RequestMapping(value = "/writeExcel", method = RequestMethod.GET)
    public void writeExcel(@ApiIgnore HttpServletResponse response) throws IOException {
        List<VehicleEx> list = new ArrayList<>();
        String title = "车辆基本信息表";
        String sheetName = "车辆基本信息";
        /*ExcelExportUtil.writeExcel(response,list,title,sheetName,VehicleEx.class);*/
        String fileName = URLEncoder.encode(title, "UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xls");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        ExcelWriter writer = new ExcelWriter(response.getOutputStream(),ExcelTypeEnum.XLS);
        Sheet sheet = new Sheet(1,0,VehicleEx.class);
        sheet.setSheetName(sheetName);
        int count = 1;
        while (count < 10) {
            for (int i=0;i<5;i++) {
                VehicleEx vehicle = new VehicleEx("vin1"+i,"deviceId1"+i,"牵引"+i,"4*2"+i,"J6P"+i,"460Ps"+i);
                list.add(vehicle);
            }
            writer.write(list,sheet);
            sheet.setStartRow(5*count);
            count++;
        }
        writer.finish();
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

    @ApiOperation(value = "测试策略模式：消除冗杂的if else")
    @RequestMapping(value = "/testStrategy",method = RequestMethod.POST)
    public Response testStrategy(@RequestBody OrderDTO dto) {
        return Response.success(orderService.handle(dto));
    }

}
