package com.example.demo.service;

import com.example.demo.dao.MysqlDao;
import com.example.demo.vo.*;
import com.xuxueli.poi.excel.ExcelImportUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ClassName MysqlService
 * @Description
 * @Author jackson
 * @Date 2019/3/19 18:16
 * @Version 1.0
 **/
@Service
@Slf4j
public class MysqlService {

    @Autowired
    private MysqlDao mysqlDao;

    public List<String> getTriggerName(String triggerGroup) {
        List<String> list = mysqlDao.getTriggerName(triggerGroup);
        return list;
    }

    public List<OrgRelation> getDataFromStorage(Integer type,Integer rootId) {
        List<OrgRelation> list;
        if (type.intValue() == 1) {
            list = mysqlDao.childList(rootId);
        } else {
            list = mysqlDao.getLevel42Child(rootId);
        }
        return list;
    }

    @Cacheable(value = "qrtzlocks")
    public List<Qrtzlocks> getQrtzLocks() {
        List<Qrtzlocks> list = mysqlDao.getQrtzLocks();
        return list;
    }

    public void insertData() {
        String filePath = "D:\\file\\location.xlsx";
        List list = ExcelImportUtil.importExcel(filePath, Location.class);
        log.info("excel info:{}",list.toString());
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(o -> {
                Location lo = (Location) o;
                mysqlDao.updateDealer(lo.getCode(),lo.getAddr(),lo.getBdlongtitude(),lo.getBdlaititude());
            });
        }
    }

    public void jsonToList() {
        String line = "[\n" +
                "    {\n" +
                "        \"station_code\": \"0120\",\n" +
                "        \"station_name\": \"韦家碾\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.723299,\n" +
                "            \"lng\": 104.093568\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0121\",\n" +
                "        \"station_name\": \"升仙湖\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.710639,\n" +
                "            \"lng\": 104.089935\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0122\",\n" +
                "        \"station_name\": \"火车北站\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.701717,\n" +
                "            \"lng\": 104.080302\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0123\",\n" +
                "        \"station_name\": \"人民北路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.691421,\n" +
                "            \"lng\": 104.079646\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0124\",\n" +
                "        \"station_name\": \"文殊院\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.681353,\n" +
                "            \"lng\": 104.079149\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0125\",\n" +
                "        \"station_name\": \"骡马市\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.672685,\n" +
                "            \"lng\": 104.072166\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0126\",\n" +
                "        \"station_name\": \"天府广场\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.66342,\n" +
                "            \"lng\": 104.072329\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0127\",\n" +
                "        \"station_name\": \"锦江宾馆\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.654591,\n" +
                "            \"lng\": 104.071189\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0128\",\n" +
                "        \"station_name\": \"华西坝\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.649044,\n" +
                "            \"lng\": 104.072743\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0129\",\n" +
                "        \"station_name\": \"省体育馆\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.639472,\n" +
                "            \"lng\": 104.07278\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0130\",\n" +
                "        \"station_name\": \"倪家桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.630892,\n" +
                "            \"lng\": 104.073334\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0131\",\n" +
                "        \"station_name\": \"桐梓林\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.621375,\n" +
                "            \"lng\": 104.073642\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0132\",\n" +
                "        \"station_name\": \"火车南站\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.610991,\n" +
                "            \"lng\": 104.073275\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0133\",\n" +
                "        \"station_name\": \"高新\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.600337,\n" +
                "            \"lng\": 104.070277\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0134\",\n" +
                "        \"station_name\": \"金融城\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.588679,\n" +
                "            \"lng\": 104.070573\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0135\",\n" +
                "        \"station_name\": \"孵化园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.581795,\n" +
                "            \"lng\": 104.070815\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0136\",\n" +
                "        \"station_name\": \"锦城广场\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.573732,\n" +
                "            \"lng\": 104.073413\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0137\",\n" +
                "        \"station_name\": \"世纪城\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.560878,\n" +
                "            \"lng\": 104.074947\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0138\",\n" +
                "        \"station_name\": \"天府三街\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.552622,\n" +
                "            \"lng\": 104.075175\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0139\",\n" +
                "        \"station_name\": \"天府五街\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.542862,\n" +
                "            \"lng\": 104.07547\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0140\",\n" +
                "        \"station_name\": \"华府大道\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.531749,\n" +
                "            \"lng\": 104.075945\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0141\",\n" +
                "        \"station_name\": \"四河\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.522804,\n" +
                "            \"lng\": 104.076363\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0142\",\n" +
                "        \"station_name\": \"广都\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.516662,\n" +
                "            \"lng\": 104.082319\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0143\",\n" +
                "        \"station_name\": \"五根松\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.507644,\n" +
                "            \"lng\": 104.087978\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0144\",\n" +
                "        \"station_name\": \"华阳\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.50938,\n" +
                "            \"lng\": 104.077037\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0145\",\n" +
                "        \"station_name\": \"海昌路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.49686,\n" +
                "            \"lng\": 104.086512\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0146\",\n" +
                "        \"station_name\": \"广福\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.484318,\n" +
                "            \"lng\": 104.075886\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0147\",\n" +
                "        \"station_name\": \"红石公园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.470238,\n" +
                "            \"lng\": 104.07914\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0148\",\n" +
                "        \"station_name\": \"麓湖\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.469321,\n" +
                "            \"lng\": 104.070708\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0149\",\n" +
                "        \"station_name\": \"武汉路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.448696,\n" +
                "            \"lng\": 104.079948\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0150\",\n" +
                "        \"station_name\": \"天府公园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.443121,\n" +
                "            \"lng\": 104.085714\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0151\",\n" +
                "        \"station_name\": \"西博城\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.43045,\n" +
                "            \"lng\": 104.084087\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0152\",\n" +
                "        \"station_name\": \"广州路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.424744,\n" +
                "            \"lng\": 104.083901\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0153\",\n" +
                "        \"station_name\": \"兴隆湖\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.403876,\n" +
                "            \"lng\": 104.093747\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0154\",\n" +
                "        \"station_name\": \"科学城\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.397344,\n" +
                "            \"lng\": 104.078763\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0221\",\n" +
                "        \"station_name\": \"犀浦\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.763697,\n" +
                "            \"lng\": 103.978059\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0222\",\n" +
                "        \"station_name\": \"天河路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.751585,\n" +
                "            \"lng\": 103.982891\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0223\",\n" +
                "        \"station_name\": \"百草路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.7388,\n" +
                "            \"lng\": 103.985905\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0224\",\n" +
                "        \"station_name\": \"金周路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.727484,\n" +
                "            \"lng\": 104.002761\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0225\",\n" +
                "        \"station_name\": \"金科北路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.726628,\n" +
                "            \"lng\": 104.013327\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0226\",\n" +
                "        \"station_name\": \"迎宾大道\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.712916,\n" +
                "            \"lng\": 104.007132\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0227\",\n" +
                "        \"station_name\": \"茶店子客运站\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.71168,\n" +
                "            \"lng\": 104.020353\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0228\",\n" +
                "        \"station_name\": \"羊犀立交\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.707087,\n" +
                "            \"lng\": 104.013508\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0229\",\n" +
                "        \"station_name\": \"一品天下\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.695109,\n" +
                "            \"lng\": 104.026629\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0230\",\n" +
                "        \"station_name\": \"蜀汉路东\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.689167,\n" +
                "            \"lng\": 104.035524\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0231\",\n" +
                "        \"station_name\": \"白果林\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.679958,\n" +
                "            \"lng\": 104.039619\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0232\",\n" +
                "        \"station_name\": \"中医大省医院\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.672244,\n" +
                "            \"lng\": 104.047241\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0233\",\n" +
                "        \"station_name\": \"通惠门\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.66842,\n" +
                "            \"lng\": 104.054968\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0234\",\n" +
                "        \"station_name\": \"人民公园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.663607,\n" +
                "            \"lng\": 104.063684\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0235\",\n" +
                "        \"station_name\": \"天府广场\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.66342,\n" +
                "            \"lng\": 104.072329\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0236\",\n" +
                "        \"station_name\": \"春熙路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.659172,\n" +
                "            \"lng\": 104.085739\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0237\",\n" +
                "        \"station_name\": \"东门大桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.654566,\n" +
                "            \"lng\": 104.092518\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0238\",\n" +
                "        \"station_name\": \"牛王庙\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.650728,\n" +
                "            \"lng\": 104.101116\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0239\",\n" +
                "        \"station_name\": \"牛市口\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.64368,\n" +
                "            \"lng\": 104.114368\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0240\",\n" +
                "        \"station_name\": \"东大路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.638988,\n" +
                "            \"lng\": 104.119428\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0241\",\n" +
                "        \"station_name\": \"塔子山公园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.640482,\n" +
                "            \"lng\": 104.130484\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0242\",\n" +
                "        \"station_name\": \"成都东客站\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.635366,\n" +
                "            \"lng\": 104.147367\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0243\",\n" +
                "        \"station_name\": \"成渝立交\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.631289,\n" +
                "            \"lng\": 104.15365\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0244\",\n" +
                "        \"station_name\": \"惠王陵\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.61827,\n" +
                "            \"lng\": 104.158677\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0245\",\n" +
                "        \"station_name\": \"洪河\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.611102,\n" +
                "            \"lng\": 104.164147\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0246\",\n" +
                "        \"station_name\": \"成都行政学院\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.603379,\n" +
                "            \"lng\": 104.171407\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0247\",\n" +
                "        \"station_name\": \"大面铺\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.598369,\n" +
                "            \"lng\": 104.207652\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0248\",\n" +
                "        \"station_name\": \"连山坡\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.593268,\n" +
                "            \"lng\": 104.219439\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0249\",\n" +
                "        \"station_name\": \"界牌\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.588482,\n" +
                "            \"lng\": 104.231745\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0250\",\n" +
                "        \"station_name\": \"书房\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.581505,\n" +
                "            \"lng\": 104.248644\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0251\",\n" +
                "        \"station_name\": \"龙平路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.579156,\n" +
                "            \"lng\": 104.265308\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0252\",\n" +
                "        \"station_name\": \"龙泉驿\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.562698,\n" +
                "            \"lng\": 104.281394\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0312\",\n" +
                "        \"station_name\": \"成都医学院\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.822909,\n" +
                "            \"lng\": 104.201255\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0313\",\n" +
                "        \"station_name\": \"石油大学\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.824883,\n" +
                "            \"lng\": 104.192835\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0314\",\n" +
                "        \"station_name\": \"钟楼\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.830046,\n" +
                "            \"lng\": 104.176214\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0315\",\n" +
                "        \"station_name\": \"马超西路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.818691,\n" +
                "            \"lng\": 104.173272\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0316\",\n" +
                "        \"station_name\": \"团结新区\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.810864,\n" +
                "            \"lng\": 104.165242\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0317\",\n" +
                "        \"station_name\": \"锦水河\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.821294,\n" +
                "            \"lng\": 104.112492\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0318\",\n" +
                "        \"station_name\": \"三河场\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.790819,\n" +
                "            \"lng\": 104.145345\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0319\",\n" +
                "        \"station_name\": \"金华寺东路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.776159,\n" +
                "            \"lng\": 104.131835\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0320\",\n" +
                "        \"station_name\": \"植物园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.77055,\n" +
                "            \"lng\": 104.136471\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0321\",\n" +
                "        \"station_name\": \"军区总医院\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.75037,\n" +
                "            \"lng\": 104.120704\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0322\",\n" +
                "        \"station_name\": \"熊猫大道\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.73136,\n" +
                "            \"lng\": 104.114587\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0323\",\n" +
                "        \"station_name\": \"动物园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.715819,\n" +
                "            \"lng\": 104.112577\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0324\",\n" +
                "        \"station_name\": \"昭觉寺南路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.708619,\n" +
                "            \"lng\": 104.103412\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0325\",\n" +
                "        \"station_name\": \"驷马桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.70132,\n" +
                "            \"lng\": 104.100173\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0326\",\n" +
                "        \"station_name\": \"李家沱\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.691461,\n" +
                "            \"lng\": 104.097223\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0327\",\n" +
                "        \"station_name\": \"前锋路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.68466,\n" +
                "            \"lng\": 104.09863\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0328\",\n" +
                "        \"station_name\": \"红星桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.676898,\n" +
                "            \"lng\": 104.096997\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0329\",\n" +
                "        \"station_name\": \"市二医院\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.666502,\n" +
                "            \"lng\": 104.090878\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0330\",\n" +
                "        \"station_name\": \"春熙路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.659172,\n" +
                "            \"lng\": 104.085739\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0331\",\n" +
                "        \"station_name\": \"新南门\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.650261,\n" +
                "            \"lng\": 104.081673\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0332\",\n" +
                "        \"station_name\": \"磨子桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.643253,\n" +
                "            \"lng\": 104.08249\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0333\",\n" +
                "        \"station_name\": \"省体育馆\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.639472,\n" +
                "            \"lng\": 104.07278\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0334\",\n" +
                "        \"station_name\": \"衣冠庙\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.640698,\n" +
                "            \"lng\": 104.05865\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0335\",\n" +
                "        \"station_name\": \"高升桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.646366,\n" +
                "            \"lng\": 104.048902\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0336\",\n" +
                "        \"station_name\": \"红牌楼\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.640816,\n" +
                "            \"lng\": 104.036047\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0337\",\n" +
                "        \"station_name\": \"太平园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.631524,\n" +
                "            \"lng\": 104.027818\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0338\",\n" +
                "        \"station_name\": \"川藏立交\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.623445,\n" +
                "            \"lng\": 104.020649\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0339\",\n" +
                "        \"station_name\": \"武侯立交\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.633904,\n" +
                "            \"lng\": 104.006794\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0340\",\n" +
                "        \"station_name\": \"武青南路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.646243,\n" +
                "            \"lng\": 103.986177\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0341\",\n" +
                "        \"station_name\": \"双凤桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.62162,\n" +
                "            \"lng\": 103.975718\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0342\",\n" +
                "        \"station_name\": \"龙桥路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.609506,\n" +
                "            \"lng\": 103.94857\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0343\",\n" +
                "        \"station_name\": \"航都大街\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.597357,\n" +
                "            \"lng\": 103.943351\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0344\",\n" +
                "        \"station_name\": \"迎春桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.586988,\n" +
                "            \"lng\": 103.941531\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0345\",\n" +
                "        \"station_name\": \"东升\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.577694,\n" +
                "            \"lng\": 103.931942\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0346\",\n" +
                "        \"station_name\": \"双流广场\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.570924,\n" +
                "            \"lng\": 103.926752\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0347\",\n" +
                "        \"station_name\": \"三里坝\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.559903,\n" +
                "            \"lng\": 103.920947\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0348\",\n" +
                "        \"station_name\": \"双流西站\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.549012,\n" +
                "            \"lng\": 103.925345\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0413\",\n" +
                "        \"station_name\": \"万盛\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.68175,\n" +
                "            \"lng\": 103.825764\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0414\",\n" +
                "        \"station_name\": \"杨柳河\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.705047,\n" +
                "            \"lng\": 103.829319\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0415\",\n" +
                "        \"station_name\": \"凤溪河\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.692054,\n" +
                "            \"lng\": 103.849829\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0416\",\n" +
                "        \"station_name\": \"南熏大道\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.695074,\n" +
                "            \"lng\": 103.858686\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0417\",\n" +
                "        \"station_name\": \"光华公园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.692863,\n" +
                "            \"lng\": 103.866483\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0418\",\n" +
                "        \"station_name\": \"涌泉\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.684147,\n" +
                "            \"lng\": 103.879691\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0419\",\n" +
                "        \"station_name\": \"凤凰大街\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.683015,\n" +
                "            \"lng\": 103.895721\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0420\",\n" +
                "        \"station_name\": \"马厂坝\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.683817,\n" +
                "            \"lng\": 103.920014\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0421\",\n" +
                "        \"station_name\": \"非遗博览园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.68057,\n" +
                "            \"lng\": 103.928449\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0422\",\n" +
                "        \"station_name\": \"蔡桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.675962,\n" +
                "            \"lng\": 103.951193\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0423\",\n" +
                "        \"station_name\": \"中坝\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.679178,\n" +
                "            \"lng\": 103.970435\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0424\",\n" +
                "        \"station_name\": \"成都西站\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.690991,\n" +
                "            \"lng\": 103.985507\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0425\",\n" +
                "        \"station_name\": \"清江西路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.68235,\n" +
                "            \"lng\": 104.005227\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0426\",\n" +
                "        \"station_name\": \"文化宫\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.619151,\n" +
                "            \"lng\": 104.070506\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0427\",\n" +
                "        \"station_name\": \"西南财大\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.676565,\n" +
                "            \"lng\": 104.02694\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0428\",\n" +
                "        \"station_name\": \"草堂北路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.675043,\n" +
                "            \"lng\": 104.035794\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0429\",\n" +
                "        \"station_name\": \"中医大省医院\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.672244,\n" +
                "            \"lng\": 104.047241\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0430\",\n" +
                "        \"station_name\": \"宽窄巷子\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.669938,\n" +
                "            \"lng\": 104.059763\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0431\",\n" +
                "        \"station_name\": \"骡马市\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.672685,\n" +
                "            \"lng\": 104.072166\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0432\",\n" +
                "        \"station_name\": \"太升南路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.666807,\n" +
                "            \"lng\": 104.081602\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0433\",\n" +
                "        \"station_name\": \"市二医院\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.666502,\n" +
                "            \"lng\": 104.090878\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0434\",\n" +
                "        \"station_name\": \"玉双路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.660197,\n" +
                "            \"lng\": 104.103715\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0435\",\n" +
                "        \"station_name\": \"双桥路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.658054,\n" +
                "            \"lng\": 104.108208\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0436\",\n" +
                "        \"station_name\": \"万年场\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.64984,\n" +
                "            \"lng\": 104.12622\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0437\",\n" +
                "        \"station_name\": \"槐树店\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.655811,\n" +
                "            \"lng\": 104.145882\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0438\",\n" +
                "        \"station_name\": \"来龙\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.653359,\n" +
                "            \"lng\": 104.170525\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0439\",\n" +
                "        \"station_name\": \"十陵\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.652201,\n" +
                "            \"lng\": 104.179148\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0440\",\n" +
                "        \"station_name\": \"成都大学\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.656051,\n" +
                "            \"lng\": 104.196613\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0441\",\n" +
                "        \"station_name\": \"明蜀王陵\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.649089,\n" +
                "            \"lng\": 104.195407\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0442\",\n" +
                "        \"station_name\": \"西河\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.652991,\n" +
                "            \"lng\": 104.226824\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0521\",\n" +
                "        \"station_name\": \"华桂路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.823318,\n" +
                "            \"lng\": 104.152787\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0522\",\n" +
                "        \"station_name\": \"柏水场\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.685129,\n" +
                "            \"lng\": 104.047556\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0523\",\n" +
                "        \"station_name\": \"廖家湾\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.797321,\n" +
                "            \"lng\": 104.017807\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0524\",\n" +
                "        \"station_name\": \"北部商贸城\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.786785,\n" +
                "            \"lng\": 104.152617\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0525\",\n" +
                "        \"station_name\": \"幸福桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.785387,\n" +
                "            \"lng\": 104.094417\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0526\",\n" +
                "        \"station_name\": \"九道堰\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.773275,\n" +
                "            \"lng\": 104.070478\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0527\",\n" +
                "        \"station_name\": \"杜家碾\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.809392,\n" +
                "            \"lng\": 104.209444\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0528\",\n" +
                "        \"station_name\": \"大丰\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.776227,\n" +
                "            \"lng\": 104.069999\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0529\",\n" +
                "        \"station_name\": \"石犀公园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.769107,\n" +
                "            \"lng\": 104.065261\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0530\",\n" +
                "        \"station_name\": \"皇花园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.756191,\n" +
                "            \"lng\": 104.064324\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0531\",\n" +
                "        \"station_name\": \"陆家桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.746414,\n" +
                "            \"lng\": 104.071691\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0532\",\n" +
                "        \"station_name\": \"泉水路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.732377,\n" +
                "            \"lng\": 104.064586\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0533\",\n" +
                "        \"station_name\": \"洞子口\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.728807,\n" +
                "            \"lng\": 104.063329\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0534\",\n" +
                "        \"station_name\": \"福宁路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.721695,\n" +
                "            \"lng\": 104.068043\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0535\",\n" +
                "        \"station_name\": \"五块石\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.658734,\n" +
                "            \"lng\": 104.051168\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0536\",\n" +
                "        \"station_name\": \"赛云台\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.710751,\n" +
                "            \"lng\": 104.076661\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0537\",\n" +
                "        \"station_name\": \"北站西二路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.697449,\n" +
                "            \"lng\": 104.072707\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0538\",\n" +
                "        \"station_name\": \"西北桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.692043,\n" +
                "            \"lng\": 104.066562\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0539\",\n" +
                "        \"station_name\": \"花牌坊\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.687801,\n" +
                "            \"lng\": 104.051324\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0540\",\n" +
                "        \"station_name\": \"抚琴\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.682512,\n" +
                "            \"lng\": 104.04611\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0541\",\n" +
                "        \"station_name\": \"中医大省医院\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.672244,\n" +
                "            \"lng\": 104.047241\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0542\",\n" +
                "        \"station_name\": \"青羊宫\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.666372,\n" +
                "            \"lng\": 104.047551\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0543\",\n" +
                "        \"station_name\": \"省骨科医院\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.657384,\n" +
                "            \"lng\": 104.04669\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0544\",\n" +
                "        \"station_name\": \"高升桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.646366,\n" +
                "            \"lng\": 104.048902\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0545\",\n" +
                "        \"station_name\": \"科园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.615332,\n" +
                "            \"lng\": 104.041489\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0546\",\n" +
                "        \"station_name\": \"九兴大道\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.628606,\n" +
                "            \"lng\": 104.043443\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0547\",\n" +
                "        \"station_name\": \"神仙树\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.615442,\n" +
                "            \"lng\": 104.05387\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0548\",\n" +
                "        \"station_name\": \"石羊立交\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.604729,\n" +
                "            \"lng\": 104.055036\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0549\",\n" +
                "        \"station_name\": \"市一医院\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.59617,\n" +
                "            \"lng\": 104.058242\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0550\",\n" +
                "        \"station_name\": \"交子大道\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.588386,\n" +
                "            \"lng\": 104.053289\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0551\",\n" +
                "        \"station_name\": \"锦城大道\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.582179,\n" +
                "            \"lng\": 104.048225\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0552\",\n" +
                "        \"station_name\": \"锦城湖\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.578477,\n" +
                "            \"lng\": 104.047943\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0553\",\n" +
                "        \"station_name\": \"大源\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.558233,\n" +
                "            \"lng\": 104.033535\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0554\",\n" +
                "        \"station_name\": \"民乐\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.651483,\n" +
                "            \"lng\": 104.009413\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0555\",\n" +
                "        \"station_name\": \"骑龙\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.537958,\n" +
                "            \"lng\": 104.05096\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0556\",\n" +
                "        \"station_name\": \"警官学院\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.518782,\n" +
                "            \"lng\": 104.04124\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0557\",\n" +
                "        \"station_name\": \"二江寺\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.508098,\n" +
                "            \"lng\": 104.057156\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0558\",\n" +
                "        \"station_name\": \"南湖立交\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.491818,\n" +
                "            \"lng\": 104.041953\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0559\",\n" +
                "        \"station_name\": \"怡心湖\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.485312,\n" +
                "            \"lng\": 104.024566\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0560\",\n" +
                "        \"station_name\": \"龙马路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.463904,\n" +
                "            \"lng\": 104.039569\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0561\",\n" +
                "        \"station_name\": \"回龙\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.310682,\n" +
                "            \"lng\": 103.69282\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0721\",\n" +
                "        \"station_name\": \"火车北站\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.701717,\n" +
                "            \"lng\": 104.080302\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0722\",\n" +
                "        \"station_name\": \"驷马桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.70132,\n" +
                "            \"lng\": 104.100173\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0723\",\n" +
                "        \"station_name\": \"府青路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.692195,\n" +
                "            \"lng\": 104.109116\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0724\",\n" +
                "        \"station_name\": \"八里庄\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.699955,\n" +
                "            \"lng\": 104.120012\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0725\",\n" +
                "        \"station_name\": \"二仙桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.686612,\n" +
                "            \"lng\": 104.133064\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0726\",\n" +
                "        \"station_name\": \"理工大学\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.68016,\n" +
                "            \"lng\": 104.141806\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0727\",\n" +
                "        \"station_name\": \"崔家店\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.671593,\n" +
                "            \"lng\": 104.142952\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0728\",\n" +
                "        \"station_name\": \"双店路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.663744,\n" +
                "            \"lng\": 104.143432\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0729\",\n" +
                "        \"station_name\": \"槐树店\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.655811,\n" +
                "            \"lng\": 104.145882\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0730\",\n" +
                "        \"station_name\": \"迎晖路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.643095,\n" +
                "            \"lng\": 104.14532\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0731\",\n" +
                "        \"station_name\": \"成都东客站\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.635366,\n" +
                "            \"lng\": 104.147367\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0732\",\n" +
                "        \"station_name\": \"大观\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.627654,\n" +
                "            \"lng\": 104.139225\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0733\",\n" +
                "        \"station_name\": \"狮子山\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.621828,\n" +
                "            \"lng\": 104.130838\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0734\",\n" +
                "        \"station_name\": \"四川师大\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.615099,\n" +
                "            \"lng\": 104.122605\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0735\",\n" +
                "        \"station_name\": \"琉璃场\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.595868,\n" +
                "            \"lng\": 104.10107\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0736\",\n" +
                "        \"station_name\": \"三瓦窑\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.610039,\n" +
                "            \"lng\": 104.084064\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0737\",\n" +
                "        \"station_name\": \"火车南站\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.610991,\n" +
                "            \"lng\": 104.073275\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0738\",\n" +
                "        \"station_name\": \"神仙树\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.615442,\n" +
                "            \"lng\": 104.05387\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0739\",\n" +
                "        \"station_name\": \"高朋大道\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.623609,\n" +
                "            \"lng\": 104.039177\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0740\",\n" +
                "        \"station_name\": \"太平园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.631524,\n" +
                "            \"lng\": 104.027818\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0741\",\n" +
                "        \"station_name\": \"武侯大道\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.6434,\n" +
                "            \"lng\": 104.019305\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0742\",\n" +
                "        \"station_name\": \"龙爪堰\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.658023,\n" +
                "            \"lng\": 104.014938\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0743\",\n" +
                "        \"station_name\": \"东坡路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.666863,\n" +
                "            \"lng\": 104.014457\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0744\",\n" +
                "        \"station_name\": \"文化宫\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.619151,\n" +
                "            \"lng\": 104.070506\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0745\",\n" +
                "        \"station_name\": \"金沙博物馆\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.683289,\n" +
                "            \"lng\": 104.018944\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0746\",\n" +
                "        \"station_name\": \"一品天下\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.695109,\n" +
                "            \"lng\": 104.026629\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0747\",\n" +
                "        \"station_name\": \"茶店子\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.702413,\n" +
                "            \"lng\": 104.03713\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0748\",\n" +
                "        \"station_name\": \"花照壁\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.702914,\n" +
                "            \"lng\": 104.047249\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0749\",\n" +
                "        \"station_name\": \"西南交大\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.770399,\n" +
                "            \"lng\": 103.993214\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0750\",\n" +
                "        \"station_name\": \"九里堤\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.700748,\n" +
                "            \"lng\": 104.06449\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"0751\",\n" +
                "        \"station_name\": \"北站西二路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.697449,\n" +
                "            \"lng\": 104.072707\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1021\",\n" +
                "        \"station_name\": \"太平园\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.631524,\n" +
                "            \"lng\": 104.027818\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1022\",\n" +
                "        \"station_name\": \"簇锦\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.617198,\n" +
                "            \"lng\": 104.014695\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1023\",\n" +
                "        \"station_name\": \"华兴\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.604105,\n" +
                "            \"lng\": 104.007535\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1024\",\n" +
                "        \"station_name\": \"金花\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.597184,\n" +
                "            \"lng\": 103.976565\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1025\",\n" +
                "        \"station_name\": \"双流机场1航站楼\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.584444,\n" +
                "            \"lng\": 103.965899\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1026\",\n" +
                "        \"station_name\": \"双流机场2航站楼\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.575473,\n" +
                "            \"lng\": 103.96281\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1027\",\n" +
                "        \"station_name\": \"双流西站\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.549012,\n" +
                "            \"lng\": 103.925345\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1028\",\n" +
                "        \"station_name\": \"应天寺\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.538551,\n" +
                "            \"lng\": 103.916486\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1029\",\n" +
                "        \"station_name\": \"黄水\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.533766,\n" +
                "            \"lng\": 103.887842\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1030\",\n" +
                "        \"station_name\": \"花源\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.490655,\n" +
                "            \"lng\": 103.889876\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1031\",\n" +
                "        \"station_name\": \"新津\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.416363,\n" +
                "            \"lng\": 103.817437\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1032\",\n" +
                "        \"station_name\": \"花桥\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.443573,\n" +
                "            \"lng\": 103.856894\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1033\",\n" +
                "        \"station_name\": \"五津\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.421451,\n" +
                "            \"lng\": 103.815193\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1034\",\n" +
                "        \"station_name\": \"儒林路\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.433362,\n" +
                "            \"lng\": 103.803062\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1035\",\n" +
                "        \"station_name\": \"刘家碾\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.65186,\n" +
                "            \"lng\": 103.949108\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"station_code\": \"1036\",\n" +
                "        \"station_name\": \"新平\",\n" +
                "        \"location\": {\n" +
                "            \"lat\": 30.415002,\n" +
                "            \"lng\": 103.785977\n" +
                "        }\n" +
                "    }\n" +
                "]";
        List<StationInfo> list = (List<StationInfo>) JSONArray.toCollection(JSONArray.fromObject(line),StationInfo.class);
        list.forEach(s -> {
            String stationCode = s.getStation_code();
            Coordinates location = s.getLocation();
        });
    }
}
