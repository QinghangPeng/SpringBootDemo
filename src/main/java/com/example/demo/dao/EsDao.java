package com.example.demo.dao;

import com.example.demo.Constants;
import com.example.demo.vo.es.Document;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @ClassName EsDao
 * @Description
 * @Author jackson
 * @Date 2019/1/25 11:11
 * @Version 1.0
 **/
@Service
public class EsDao {

    @Autowired
    private RestHighLevelClient client;

    public static final Logger logger =  LoggerFactory.getLogger(EsDao.class);

    public void createMapping(String index, String type, Class clazz) {
        try{
            if(!indexExists(index)) {
                createIndex(new Document(index,type));
            }
            PutMappingRequest mapping = Requests.putMappingRequest(index).type(type).source(converMapping(clazz));
            client.indices().putMapping(mapping).isAcknowledged();
        }catch (Exception e) {
            logger.error("createMapping error:{}",e);
        }
    }

    /**
     *  创建索引
     * @param document
     */
    public void createIndex(Document document) {
        try {
            if (!indexExists(document.getIndexName())) {
                CreateIndexRequest request = new CreateIndexRequest(document.getIndexName());
                request.settings(Settings.builder().put("index.number_of_shards",1)
                .put("index.number_of_replicas",1));
                request.mapping(document.getType());
                client.indices().create(request);
            }
        } catch (Exception e) {
            logger.error("createIndex error:{}",e);
        }
    }

    /**
     *  删除索引
     * @param document
     */
    public void deleteIndex(Document document) {
        try{
            if (indexExists(document.getIndexName())) {
                DeleteIndexRequest deleteRequest = new DeleteIndexRequest(document.getIndexName());
                client.indices().delete(deleteRequest);
            }
        }catch (Exception e) {
            logger.error("deleteIndex error:{}",e);
        }
    }

    /**
     * 修改索引
     * @param document
     */
    public void updateIndex(Document document) {
        document.getData().put(Constants.UPDATETIME,System.currentTimeMillis());
        UpdateRequest updateRequest = new UpdateRequest(document.getIndexName(),document.getType(),document.getId());
        updateRequest.doc(document.getData());
        try{
            client.update(updateRequest);
        }catch (Exception e) {
            logger.error("updateIndex error:{}",e);
        }
    }

    private static XContentBuilder converMapping(Class clazz) throws IOException {
        XContentBuilder mapping = null;
        Field[] fields = clazz.getDeclaredFields();
        String className = clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1);
        try {
            mapping = jsonBuilder()
                    .startObject().startObject("properties");

            for (Field f:fields) {
                String[] packageType = f.getGenericType().getTypeName().split("\\.");
                String typeName = packageType[packageType.length-1].toLowerCase();
                String paramName = f.getName();
                switch (typeName){
                    case "integer":

                    case "int":
                        mapping.startObject(f.getName())
                                .field("type","integer")
                                .endObject();
                        break;
                    case "string":
                        if (("OpenIovInfoForEs".equals(className) && "cityCode".equals(paramName)) ||
                                ("UseIovInfoForEs".equals(className) && "result".equals(paramName))) {
                            mapping.startObject(f.getName())
                                    .field("type","text")
                                    .field("fielddata",true)
                                    .endObject();
                        } else {
                            mapping.startObject(f.getName())
                                    .field("type","text")
                                    //     .field("index","not_analyzed")// 设置后不支持中文分词，仅支持中文的精准匹配
                                    .endObject();
                        }
                        break;
                    case "long":
                    case "short":
                    case "boolean":
                    case "double":
                    case "float":
                        mapping.startObject(f.getName())
                                .field("type",typeName)
                                .endObject();
                        break;
                    case "date":
                        mapping.startObject(f.getName())
                                .field("type",typeName)
                                .field("format","yyyy-MM-dd")
                                .endObject();
                        break;
                    default:
                        break;
                }
            }
            mapping.endObject().endObject();
            return mapping;
        } catch (Exception e) {
            logger.error("converMapping error:{}",e);
        }
        return null;
    }

    /**
     * 新增 es 数据
     *
     * @param document
     */
    public String insertIndex(Document document) {
        document.getData().put(Constants.CREATETIME,System.currentTimeMillis());
        document.getData().put(Constants.UPDATETIME,System.currentTimeMillis());
        IndexRequest request = new IndexRequest(document.getIndexName(),document.getType(),document.getId());

        try{
            request.source(document.getData());
            IndexResponse response = client.index(request);
            return response.getId();
        }catch (Exception e) {
            logger.error("IndexRequest insert:{}",e);
        }
        return null;
    }

    /**
     * 验证索引是否存在
     *
     * @param index 索引名称
     * @return
     * @throws Exception
     */
    private boolean indexExists(String index) throws Exception {

        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
        request.local(false);
        request.humanReadable(true);

        return client.indices().exists(request);
    }

    /**
     *  验证数据是否存在
     * @param index
     * @param type
     * @param id
     * @return
     */
    public boolean getDocExists(String index, String type, String id) {
        Map<String,Object> map = new HashMap<>();
        GetRequest getRequest = new GetRequest(index,type,id);
        try{
            GetResponse response = client.get(getRequest);
            return response.isExists();
        }catch (Exception e) {
            logger.error("queryid is error:{}",e);
        }
        return false;
    }

}
