package com.robbendev.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * Json工具类
 *
 * @author robbendev
 */
public class JsonUtilByFsJson {

    /**
     * 期望的 JSON 格式
     */
    public static final SerializerFeature[] features = {
            // 输出空置字段
            SerializerFeature.WriteMapNullValue,
            // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullListAsEmpty,
            // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullNumberAsZero,
            // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullBooleanAsFalse,
            // 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteEnumUsingToString
    };

    /**
     * 对返回的 JSON 值做空值过滤
     */
    public static final PropertyFilter allPropertyFilter = (object, name, value) -> {
        if (value == null) {
            return false;
        }
        if (value instanceof String) {
            String str = (String) value;
            return !StringUtils.isEmpty(str);
        }
        return true;
    };

    /**
     * bean 转换为json（过滤空值）
     *
     * @param src bean
     * @param <T> bean的类型
     * @return json
     */
    public static <T> String beanToJsonNotNull(T src) {
        return JSON.toJSONString(src, allPropertyFilter, features);
    }

    /**
     * bean 转换为json（不过滤空值）
     *
     * @param src bean
     * @param <T> bean的类型
     * @return json
     */
    public static <T> String beanToJson(T src) {
        return JSON.toJSONString(src, features);
    }

    /**
     * json 转为 Bean
     *
     * @param json  json
     * @param clazz clazz
     * @param <T>   T
     * @return bean
     */
    public static <T> T jsonToBean(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * json 转为list
     *
     * @param json  json
     * @param clazz clazz
     * @param <T>   T
     * @return list
     */
    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

}
