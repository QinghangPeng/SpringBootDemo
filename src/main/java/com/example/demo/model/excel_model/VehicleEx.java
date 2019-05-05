package com.example.demo.model.excel_model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @ClassName Vehicle
 * @Description
 * @Author jackson
 * @Date 2019/5/5 11:29
 * @Version 1.0
 **/
public class VehicleEx extends BaseRowModel {

    @ExcelProperty(value = "vin",index = 0)
    private String vin;

    @ExcelProperty(value = "tboxId",index = 1)
    private String deviceId;

    @ExcelProperty(value = "品系",index = 2)
    private String series;

    @ExcelProperty(value = "驱动",index = 3)
    private String drive;

    @ExcelProperty(value = "驾驶室",index = 4)
    private String cab;

    @ExcelProperty(value = "马力",index = 5)
    private String mali;

    public VehicleEx() {
    }

    public VehicleEx(String vin, String deviceId, String series, String drive, String cab, String mali) {
        this.vin = vin;
        this.deviceId = deviceId;
        this.series = series;
        this.drive = drive;
        this.cab = cab;
        this.mali = mali;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public String getCab() {
        return cab;
    }

    public void setCab(String cab) {
        this.cab = cab;
    }

    public String getMali() {
        return mali;
    }

    public void setMali(String mali) {
        this.mali = mali;
    }
}
