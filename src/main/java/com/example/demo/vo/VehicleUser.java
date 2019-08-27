package com.example.demo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName VehicleUser
 * @Description
 * @Author jackson
 * @Date 2019/8/27 9:46
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
public class VehicleUser {
    private String name;
    private String address;
    private String phone;
    private String vin;
    private String tboxId;
}
