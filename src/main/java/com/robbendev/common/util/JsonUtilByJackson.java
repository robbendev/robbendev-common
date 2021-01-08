package com.robbendev.common.util;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParseException;

import java.io.IOException;

/**
 * Jackson util
 * <p>
 * 1、obj need private and set/get；
 * 2、do not support inner class；
 *
 * @author robbendev
 */
public class JsonUtilByJackson {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(JsonUtilByJackson.class);

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    /**
     * bean、array、List、Map --> json
     *
     * @param obj obj
     * @return json string
     */
    public static String writeValueAsString(Object obj) {
        try {
            return getInstance().writeValueAsString(obj);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * string --> bean、Map、List(array)
     *
     * @param jsonStr      jsonStr
     * @param valueTypeRef valueTypeRef
     * @return obj
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) {
        try {
            return getInstance().readValue(jsonStr, valueTypeRef);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonParseException | IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * string --> bean、Map、List(array)
     *
     * @param jsonStr jsonStr
     * @param clazz   clazz
     * @return obj
     */
    public static <T> T readValue(String jsonStr, Class<T> clazz) {
        try {
            return getInstance().readValue(jsonStr, clazz);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonParseException | IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * string --> List<Bean>...
     *
     * @param jsonStr          jsonStr
     * @param parametrized     parametrized
     * @param parameterClasses parameterClasses
     * @param <T>              T
     * @return list
     */
    public static <T> T readValue(String jsonStr, Class<?> parametrized, Class<?>... parameterClasses) {
        try {
            JavaType javaType = getInstance().getTypeFactory().constructParametricType(parametrized, parameterClasses);
            return getInstance().readValue(jsonStr, javaType);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonParseException | IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
