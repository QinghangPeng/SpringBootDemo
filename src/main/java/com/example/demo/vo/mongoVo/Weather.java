package com.example.demo.vo.mongoVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName Weather
 * @Description
 * @Author jackson
 * @Date 2019/7/30 11:41
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@ApiModel("天气实体")
@Document(collection = "weather")
@CompoundIndexes({
        @CompoundIndex(name = "location_index",def = "{'province':1,'city':1}",unique = false)
})
public class Weather {

    @Id
    private String id;
    private String province;
    private String city;
    private Integer minTemp;
    private Integer maxTemp;
    private String windDesc;

    /**
     * 该注解为映射忽略该字段，不会保存到mongo中
     */
    @Transient
    private String field;
}
