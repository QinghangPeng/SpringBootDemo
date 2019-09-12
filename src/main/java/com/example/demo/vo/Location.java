package com.example.demo.vo;

import com.xuxueli.poi.excel.annotation.ExcelField;
import com.xuxueli.poi.excel.annotation.ExcelSheet;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * @ClassName Location
 * @Description
 * @Author jackson
 * @Date 2019/9/12 16:29
 * @Version 1.0
 **/
@ExcelSheet(name = "sheet2",headColor = HSSFColor.HSSFColorPredefined.LIGHT_GREEN)
public class Location {

    @ExcelField(name = "序号")
    private Integer index;

    @ExcelField(name = "身份类型")
    private String type;

    @ExcelField(name = "公司名称")
    private String compName;

    @ExcelField(name = "经销商代码")
    private String code;

    @ExcelField(name = "详细地址")
    private String addr;

    @ExcelField(name = "bdlongtitude")
    private Double bdlongtitude;

    @ExcelField(name = "bdlaititude")
    private Double bdlaititude;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Double getBdlongtitude() {
        return bdlongtitude;
    }

    public void setBdlongtitude(Double bdlongtitude) {
        this.bdlongtitude = bdlongtitude;
    }

    public Double getBdlaititude() {
        return bdlaititude;
    }

    public void setBdlaititude(Double bdlaititude) {
        this.bdlaititude = bdlaititude;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }
}
