package com.example.demo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: StationInfo
 * @Description:
 * @Author: jackson
 * @Date: 2019/11/11 14:58
 * @Version: v1.0
 */
@Data
@NoArgsConstructor
public class StationInfo {

    private String station_code;
    private String station_name;
    private Coordinates location;

}
