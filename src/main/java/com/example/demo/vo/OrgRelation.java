package com.example.demo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName OrgRelation
 * @Description
 * @Author jackson
 * @Date 2019/6/11 9:55
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
public class OrgRelation {

    private Integer id;
    private Integer pid;
    private String name;
    private String level;

}
