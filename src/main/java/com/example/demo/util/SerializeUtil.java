package com.example.demo.util;

import java.util.concurrent.ConcurrentHashMap;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import java.util.Map;

/**
 * @ClassName SerializeUtil
 * @Description 序列化
 * @Author jackson
 * @Date 2019/8/10 17:59
 * @Version 1.0
 **/
public final class SerializeUtil {
    private static Map<Class<?>, Schema<?>> CACHE_STREAMS = new ConcurrentHashMap<>();

    private SerializeUtil() {
        // prevent instantiation
    }

    public static <T> byte[] serialize(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    public static <T> T deserialize(byte[] data, Class<T> cls) {
        if (data == null || data.length == 0) {
            return null;
        }
        try {
            T message = cls.newInstance();
            Schema<T> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }


    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) CACHE_STREAMS.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            if (schema != null) {
                CACHE_STREAMS.put(cls, schema);
            }
        }
        return schema;
    }
}
