package com.example.demo.vo.mongoVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName IndexReq
 * @Description
 * @Author jackson
 * @Date 2019/7/30 11:32
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@ApiModel("创建带索引collection入参")
public class IndexReq {

    private String colName;
    private List<String[]> list;

}
