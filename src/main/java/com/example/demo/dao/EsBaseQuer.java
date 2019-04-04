/*
package com.example.demo.dao;

import com.example.demo.vo.Page;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.bucket.range.ParsedRange;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

*/
/**
 * @ClassName EsBaseQuer
 * @Description
 * @Author jackson
 * @Date 2019/1/28 15:58
 * @Version 1.0
 **//*

@Service
public class EsBaseQuer {

    @Autowired
    private RestHighLevelClient client;

    public static final Logger logger =  LoggerFactory.getLogger(EsBaseQuer.class);

    public List<Map<String, Object>> quer(String index, String type, BoolQueryBuilder boolQueryBuilder, FieldSortBuilder fieldSortBuilder, Page page) {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //分页
        if (page != null && page.isPage()) {
            sourceBuilder.from(page.getStart());
            sourceBuilder.size(page.getPageSize());
        }

        //排序
        if (fieldSortBuilder != null) {
            sourceBuilder.sort(fieldSortBuilder);
        }

        //组装查询条件
        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        sourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);

        List<Map<String,Object>> list = new ArrayList<>();
        try{
            SearchResponse response = client.search(searchRequest);
            logger.info("es查询结果:{}",response);
            //如果分页设置总页数
            if (page != null && page.isPage()) {
                page.count(response.getHits().getTotalHits());
            }
            for (SearchHit hit : response.getHits().getHits()) {
                list.add(hit.getSourceAsMap());
            }
        }catch (Exception e) {
            logger.error("queryBusInfo is error:{}",e);
        }

        return list;
    }

    public List<StaticIovOpenResult> statisticOpenIovPerMonths(String index, String type, Map map) {

        //组装查询条件:时间范围
        long lastYear = MapUtils.getLong(map,"lastYear");
        long lastDay = MapUtils.getLong(map,"lastDay");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatLastYear = sdf.format(new Date(lastYear));
        String formatLastDay = sdf.format(new Date(lastDay));
        RangeQueryBuilder query = QueryBuilders.rangeQuery("iovServiceOpenDate").gte(lastYear).lte(lastDay);
        //按月分组,并且强制返回时间段内的所有空buckets
        AggregationBuilder aggregation = AggregationBuilders.dateHistogram("agg").field("iovOpenDay")
                .dateHistogramInterval(DateHistogramInterval.MONTH).format("yyyy-MM-dd").minDocCount(0L)
                .extendedBounds(new ExtendedBounds(formatLastYear,formatLastDay));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        sourceBuilder.query(query);
        sourceBuilder.aggregation(aggregation);
        sourceBuilder.size(0);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);

        List<StaticIovOpenResult> list = new ArrayList<>();
        try{
            SearchResponse response = client.search(searchRequest);
            logger.info("statisticOpenIovPerMonths:{}",response);
            Histogram agg = response.getAggregations().get("agg");
            for (Histogram.Bucket entry : agg.getBuckets()) {
                StaticIovOpenResult iovOpenResult = new StaticIovOpenResult(entry.getKeyAsString().substring(0,7),
                        entry.getDocCount());
                list.add(iovOpenResult);
            }
        } catch(Exception e) {
            logger.error("statisticOpenIovPerMonths error:{}",e);
        }
        return list;
    }

    public RealTimeOpenIovCount staticRealTimeOpenIovCount(String index,String type,Map monthMap,Map dayMap) {

        */
/*if (map != null) {
            //组装查询条件:时间范围
            RangeQueryBuilder query = QueryBuilders.rangeQuery("iovServiceOpenDate").gte(MapUtils.getLong(map,"start")).lte(MapUtils.getLong(map,"end"));
            sourceBuilder.query(query);
        }*//*


        AggregationBuilder aggregation = AggregationBuilders.range("this_day_and_month").field("iovServiceOpenDate")
                .keyed(true).addRange(Constants.THIS_DAY,MapUtils.getLongValue(dayMap,"start"),MapUtils.getLongValue(dayMap,"end"))
                .addRange(Constants.THIS_MONTH,MapUtils.getLongValue(monthMap,"start"),MapUtils.getLongValue(monthMap,"end"));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        sourceBuilder.size(0);
        sourceBuilder.aggregation(aggregation);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);

        RealTimeOpenIovCount realTimeOpenIovCount = new RealTimeOpenIovCount(0,0,0);
        //查询用于更新页面数据，事件用于页面数据相加
        realTimeOpenIovCount.setStatus(Constants.UPDATE);

        try{
            SearchResponse response = client.search(searchRequest);
            logger.info("staticRealTimeOpenIovCount:{}",response);
            long total = response.getHits().getTotalHits();
            ParsedRange range = response.getAggregations().get("this_day_and_month");
            realTimeOpenIovCount.setTotal(total);
            for (Range.Bucket bucket : range.getBuckets()) {
                switch (bucket.getKey().toString()) {
                    case Constants.THIS_DAY:
                        realTimeOpenIovCount.setDayTotal(bucket.getDocCount());
                        break;
                    case Constants.THIS_MONTH:
                        realTimeOpenIovCount.setMonthTotal(bucket.getDocCount());
                        break;
                        default:
                            break;
                }
            }
        } catch(Exception e) {
            logger.error("staticRealTimeOpenIovCount error:{}",e);
        }
        return realTimeOpenIovCount;
    }

    public RealTimeUseIovCount staticRealTimeUseIovCount(String index,String type,Map dayMap) {

        AggregationBuilder aggregation = AggregationBuilders.range("day_use_count").field("useIovServiceTime")
                .keyed(true).addRange(Constants.THIS_DAY,MapUtils.getLongValue(dayMap,"start"),
                        MapUtils.getLongValue(dayMap,"end"));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        sourceBuilder.size(0);
        sourceBuilder.aggregation(aggregation);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);

        RealTimeUseIovCount realTimeUseIovCount = new RealTimeUseIovCount(0,0);
        //查询用于更新页面数据，事件用于页面数据相加
        realTimeUseIovCount.setStatus(Constants.UPDATE);

        try{
            SearchResponse response = client.search(searchRequest);
            logger.info("staticRealTimeUseIovCount:{}",response);
            long total = response.getHits().getTotalHits();
            ParsedRange range = response.getAggregations().get("day_use_count");
            realTimeUseIovCount.setTotalCount(total);
            for (Range.Bucket bucket : range.getBuckets()) {
                switch (bucket.getKey().toString()) {
                    case Constants.THIS_DAY:
                        realTimeUseIovCount.setDayCount(bucket.getDocCount());
                        break;
                    default:
                        break;
                }
            }
        } catch(Exception e) {
            logger.error("staticRealTimeUseIovCount error:{}",e);
        }
        return realTimeUseIovCount;
    }

    public List<StaticSystemWarnResult> statisticSystemWarnPerTypes(String index, String type, Map map) {

        long lastDay = MapUtils.getLongValue(map,"lastDay");
        long lastThirtyDay = MapUtils.getLongValue(map,"lastThirtyDay");

        RangeQueryBuilder query = QueryBuilders.rangeQuery("alarmTime").gte(lastThirtyDay).lte(lastDay);

        //按照系统类型分组
        AggregationBuilder aggregation = AggregationBuilders.terms("per_type_static").field("systemPlatformType");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        sourceBuilder.query(query);
        sourceBuilder.aggregation(aggregation);
        sourceBuilder.size(0);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);

        List<StaticSystemWarnResult> list = new ArrayList<>();
        try{
            SearchResponse response = client.search(searchRequest);
            logger.info("statisticSystemWarnPerTypes:{}",response);
            ParsedLongTerms agg = response.getAggregations().get("per_type_static");
            for (Terms.Bucket entry : agg.getBuckets()) {
                StaticSystemWarnResult systemWarnResult = new StaticSystemWarnResult(Integer.parseInt(String.valueOf(entry.getKey())),
                        entry.getDocCount());
                list.add(systemWarnResult);
            }
        } catch(Exception e) {
            logger.error("statisticSystemWarnPerTypes error:{}",e);
        }
        return list;
    }

    public ProvinceInfoResult everyProvinceOpenIovInfo(String index,String type,String province,Map dayMap) {

        MatchQueryBuilder query = QueryBuilders.matchQuery("provinceCode",province);

        AggregationBuilder aggregation = AggregationBuilders.terms("city_of_province").
                field("cityCode");
        AggregationBuilder dayAggregation = AggregationBuilders.range("month_of_province")
                .field("iovServiceOpenDate").keyed(true).addRange(Constants.THIS_MONTH,MapUtils.getLongValue(dayMap,"start"),
                        MapUtils.getLongValue(dayMap,"end"));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        sourceBuilder.query(query);
        sourceBuilder.aggregation(aggregation);
        sourceBuilder.aggregation(dayAggregation);
        sourceBuilder.size(0);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);

        ProvinceInfoResult provinceInfoResult = new ProvinceInfoResult();

        try{
            SearchResponse response = client.search(searchRequest);
            logger.info("everyProvinceOpenIovInfo:{}",response);
            //总开通
            long total = response.getHits().getTotalHits();
            provinceInfoResult.setTotalOpenIovCount(total);
            //日开通
            ParsedRange range = response.getAggregations().get("month_of_province");
            for (Range.Bucket bucket : range.getBuckets()) {
                if(bucket.getKey().toString().equals(Constants.THIS_MONTH)) {
                    provinceInfoResult.setThismonthOpenIovCount(bucket.getDocCount());
                }
            }
            //每个城市
            List<CityOpenIovInfo> everyCityOpenIovInfo = new ArrayList<>();
            ParsedTerms terms = response.getAggregations().get("city_of_province");
            for (Terms.Bucket bucket : terms.getBuckets()) {
                CityOpenIovInfo cityOpenIovInfo = new CityOpenIovInfo();
                cityOpenIovInfo.setCityCode(bucket.getKey().toString());
                cityOpenIovInfo.setCityOpenIovCount(bucket.getDocCount());
                everyCityOpenIovInfo.add(cityOpenIovInfo);
            }
            provinceInfoResult.setEveryCityOpenIovInfo(everyCityOpenIovInfo);
        } catch(Exception e) {
            logger.error("everyProvinceOpenIovInfo error:{}",e);
        }
        return provinceInfoResult;
    }

    public List<UseIOVRes> statisticUseIovPerFunctions(String index,String type,Map map) {

        RangeQueryBuilder query = QueryBuilders.rangeQuery("useIovServiceTime")
                .gte(MapUtils.getLongValue(map,"lastThirtyDay")).lte(MapUtils.getLongValue(map,"lastHour"));

        //先按照服务类型分组，再按照服务功能分组
        AggregationBuilder aggregation = AggregationBuilders.terms("iov_service_type").field("iovServiceType")
                .subAggregation(AggregationBuilders.terms("iov_service_function").field("iovServiceFunction"));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        sourceBuilder.query(query);
        sourceBuilder.aggregation(aggregation);
        sourceBuilder.size(0);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);

        List<UseIOVRes> list = new ArrayList<>();
        try{
            SearchResponse response = client.search(searchRequest);
            logger.info("statisticUseIovPerFunctions:{}",response);
            ParsedTerms terms = response.getAggregations().get("iov_service_type");
            for (Terms.Bucket bucket : terms.getBuckets()) {
                if (bucket.getKey().toString().equals("3")) {
                    ParsedTerms functionTerms = bucket.getAggregations().get("iov_service_function");
                    for (Terms.Bucket functionBucket : functionTerms.getBuckets()) {
                        UseIOVRes iovRes = new UseIOVRes(functionBucket.getDocCount(),
                                IovServiceFunctions.getNameById(Integer.parseInt(functionBucket.getKey().toString())));
                        list.add(iovRes);
                    }
                } else {
                    UseIOVRes iovRes = new UseIOVRes(bucket.getDocCount(),
                            IovServiceTypes.getNameById(Integer.parseInt(bucket.getKey().toString())));
                    list.add(iovRes);
                }
            }
        } catch(Exception e) {
            logger.error("statisticUseIovPerFunctions error:{}",e);
        }
        return list;
    }

    public UseIovTmonthsResponse staticLastTmonthsUseCount(String index,String type,Map map) {

        //组装查询条件:时间范围
        long lastYear = MapUtils.getLong(map,"lastYear");
        long lastHour = MapUtils.getLong(map,"lastHour");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatLastYear = sdf.format(new Date(lastYear));
        String formatLastHour = sdf.format(new Date(lastHour));

        //组装时间查询范围
        RangeQueryBuilder query = QueryBuilders.rangeQuery("useIovServiceTime")
                .gte(lastYear).lte(lastHour);

        //按照服务类型和功能分组，再按月分组并且强制返回时间段内的所有空buckets
        AggregationBuilder typeAggregation = AggregationBuilders.terms("iov_service_type").field("iovServiceType")
                .minDocCount(0L)
                .subAggregation(AggregationBuilders.dateHistogram("agg").field("useIovDay")
                .dateHistogramInterval(DateHistogramInterval.MONTH).format("yyyy-MM-dd").minDocCount(0L)
                .extendedBounds(new ExtendedBounds(formatLastYear,formatLastHour)));
        AggregationBuilder functionAggregation = AggregationBuilders.terms("iov_service_function").field("iovServiceFunction")
                .minDocCount(0L)
                .subAggregation(AggregationBuilders.dateHistogram("agg").field("useIovDay")
                        .dateHistogramInterval(DateHistogramInterval.MONTH).format("yyyy-MM-dd").minDocCount(0L)
                        .extendedBounds(new ExtendedBounds(formatLastYear,formatLastHour)));
        AggregationBuilder monthAggregation = AggregationBuilders.dateHistogram("groupDate").field("useIovDay")
                .dateHistogramInterval(DateHistogramInterval.MONTH).format("yyyy-MM-dd").minDocCount(0L)
                .extendedBounds(new ExtendedBounds(formatLastYear,formatLastHour));


        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        sourceBuilder.query(query);
        sourceBuilder.aggregation(typeAggregation);
        sourceBuilder.aggregation(functionAggregation);
        sourceBuilder.aggregation(monthAggregation);
        sourceBuilder.size(0);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);

        UseIovTmonthsResponse useIovTmonthsResponse = new UseIovTmonthsResponse();
        List<String> months = new ArrayList<>();
        List<MonthsData> list = new ArrayList<>();
        try{
            SearchResponse response = client.search(searchRequest);
            logger.info("staticLastTmonthsUseCount:{}",response);
            //组装月份
            ParsedDateHistogram month = response.getAggregations().get("groupDate");
            for (Histogram.Bucket bucket : month.getBuckets()) {
                months.add(bucket.getKey().toString().substring(0,7));
            }

            //组装数据
            buildData(response,"iov_service_type","3",list);
            buildData(response,"iov_service_function","0",list);

            useIovTmonthsResponse.setMonths(months);
            useIovTmonthsResponse.setList(list);
        } catch(Exception e) {
            logger.error("staticLastTmonthsUseCount error:{}",e);
        }
        return useIovTmonthsResponse;
    }

    public List<TotalAndSuccessCount> latestTdaysCountAndRate(String index,String type,Map map) {

        RangeQueryBuilder query = QueryBuilders.rangeQuery("useIovServiceTime")
                .gte(MapUtils.getLongValue(map,"lastThirtyDay")).lte(MapUtils.getLongValue(map,"lastDay"));

        AggregationBuilder functionAggregation = AggregationBuilders.terms("functions").field("iovServiceFunction")
                .minDocCount(0L).order(BucketOrder.key(true))
                .subAggregation(AggregationBuilders.terms("func_result").field("result")
                        .minDocCount(0L));

        AggregationBuilder typeAggregation = AggregationBuilders.terms("types").field("iovServiceType")
                .minDocCount(0L).order(BucketOrder.key(true))
                .subAggregation(AggregationBuilders.terms("type_result").field("result")
                        .minDocCount(0L));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        sourceBuilder.query(query);
        sourceBuilder.aggregation(functionAggregation);
        sourceBuilder.aggregation(typeAggregation);
        sourceBuilder.size(0);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);

        List<TotalAndSuccessCount> list = new ArrayList<>();
        try{
            SearchResponse response = client.search(searchRequest);
            logger.info("latestTdaysCountAndRate:{}",response);

            //组合数据
            buildUseIovCount(response,"types","3","type_result",list);
            buildUseIovCount(response,"functions","0","func_result",list);

        } catch(Exception e) {
            logger.error("latestTdaysCountAndRate error:{}",e);
        }

        return list;
    }

    public List<StaticActiveVehResult> staticActiveVehicle(String index,String type,Long start,Long end,int flag) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatStart = sdf.format(new Date(start));
        String formatEnd = sdf.format(new Date(end));

        RangeQueryBuilder query = QueryBuilders.rangeQuery("firstActiveTime")
                .gte(start).lte(end);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        if (Constants.DAILY_ACTIVE == flag) {
            AggregationBuilder aggregation = AggregationBuilders.dateHistogram("groupByDate").field("activeDate")
                    .dateHistogramInterval(DateHistogramInterval.DAY).format("yyyy-MM-dd").minDocCount(0L)
                    .extendedBounds(new ExtendedBounds(formatStart,formatEnd));
            sourceBuilder.aggregation(aggregation);
        } else if (Constants.MONTHLY_ACTIVE == flag){
            AggregationBuilder aggregation = AggregationBuilders.dateHistogram("groupByDate").field("activeDate")
                    .dateHistogramInterval(DateHistogramInterval.MONTH).format("yyyy-MM-dd").minDocCount(0L)
                    .extendedBounds(new ExtendedBounds(formatStart,formatEnd));
            sourceBuilder.aggregation(aggregation);
        }


        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        sourceBuilder.query(query);
        sourceBuilder.size(0);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);

        List<StaticActiveVehResult> list = new ArrayList<>();
        try{
            SearchResponse response = client.search(searchRequest);
            logger.info("staticActiveVehicle:{}",response);
            ParsedDateHistogram histograms = response.getAggregations().get("groupByDate");
            if (Constants.DAILY_ACTIVE == flag) {
                for (Histogram.Bucket bucket : histograms.getBuckets()) {
                    StaticActiveVehResult result = new StaticActiveVehResult(bucket.getDocCount(),
                            bucket.getKeyAsString());
                    list.add(result);
                }
            } else if (Constants.MONTHLY_ACTIVE == flag) {
                for (Histogram.Bucket bucket : histograms.getBuckets()) {
                    StaticActiveVehResult result = new StaticActiveVehResult(bucket.getDocCount(),
                            bucket.getKeyAsString().substring(0,7));
                    list.add(result);
                }
            }

        } catch(Exception e) {
            logger.error("staticActiveVehicle error:{}",e);
        }
        return list;
    }

    public void buildUseIovCount(SearchResponse response,String termsName,String filterName,
                                 String sonTermsName,List<TotalAndSuccessCount> list) {
        ParsedTerms funcTerms = response.getAggregations().get(termsName);
        for (Terms.Bucket bucket : funcTerms.getBuckets()) {
            if (!bucket.getKey().toString().equals(filterName)) {
                TotalAndSuccessCount count = new TotalAndSuccessCount();
                count.setTotalCount(bucket.getDocCount());
                if (termsName.equals("types")) {
                    count.setKey(IovServiceTypes.getNameById(Integer.parseInt(bucket.getKey().toString())));
                } else {
                    count.setKey(IovServiceFunctions.getNameById(Integer.parseInt(bucket.getKey().toString())));
                }
                if (bucket.getDocCount() != 0) {
                    ParsedTerms terms = bucket.getAggregations().get(sonTermsName);
                    for (Terms.Bucket resultBucket : terms.getBuckets()) {
                        if (resultBucket.getKey().toString().equals("success")) {
                            count.setSuccessCount(resultBucket.getDocCount());
                        }
                    }
                }
                list.add(count);
            }
        }
    }

    public void buildData(SearchResponse response,String termsName,String filterName,List<MonthsData> list) {
        ParsedTerms terms = response.getAggregations().get(termsName);
        for (Terms.Bucket bucket : terms.getBuckets()) {
            if (!bucket.getKey().toString().equals(filterName)) {
                ParsedDateHistogram monthTerms = bucket.getAggregations().get("agg");
                List<Long> values = new ArrayList<>();
                for (Histogram.Bucket monthBucket : monthTerms.getBuckets()) {
                    values.add(monthBucket.getDocCount());
                }
                MonthsData monthsData = new MonthsData();
                if (termsName.equals("iov_service_type")) {
                    monthsData.setName(IovServiceTypes.getNameById(Integer.parseInt(bucket.getKey().toString())));
                } else {
                    monthsData.setName(IovServiceFunctions.getNameById(Integer.parseInt(bucket.getKey().toString())));
                }
                monthsData.setValues(values);
                list.add(monthsData);
            }
        }
    }
}
*/
