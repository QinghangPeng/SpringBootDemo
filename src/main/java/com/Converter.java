package com;

import com.example.demo.vo.Vehicle;
import com.example.demo.vo.VehicleUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * @ClassName Converter
 * @Description 利用mapstruct实现实体类直接的转换
 * @Author jackson
 * @Date 2019/8/27 9:46
 * @Version 1.0
 **/
@Mapper
public interface Converter {

    Converter INSTANCE = Mappers.getMapper(Converter.class);

    Vehicle from(VehicleUser user);
}
