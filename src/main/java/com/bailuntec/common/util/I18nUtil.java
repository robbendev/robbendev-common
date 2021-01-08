package com.bailuntec.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 国际化资源文件工具类
 *
 * @author robbendev
 */
public class I18nUtil {

    private static Logger logger = LoggerFactory.getLogger(I18nUtil.class);

    private static Properties prop = null;

    /**
     * 加载国际化信息资源配置文件
     *
     * @param lang
     * @return
     */
    public static Properties loadI18nProp(String lang) {
        return loadI18nProp(lang, null);
    }

    /**
     * 加载国际化信息资源配置文件
     *
     * @param lang
     * @return
     */
    public static Properties loadI18nProp(String lang, String fileName) {
        if (prop != null) {
            return prop;
        }
        try {
            // build i18n prop
            fileName = StringUtils.isNotEmpty(fileName) ? fileName.trim() : "message";
            lang = (StringUtils.isNotEmpty(lang) ? "_" + lang : "_zh");
            String i18nFile = MessageFormat.format("i18n/{0}{1}.properties", fileName, lang);

            // load prop
            Resource resource = new ClassPathResource(i18nFile);
            EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
            prop = PropertiesLoaderUtils.loadProperties(encodedResource);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return prop;
    }

    /**
     * 获取国际化配置信息
     *
     * @param lang
     * @param key
     * @return
     */
    public static String getString(String lang, String key) {
        return loadI18nProp(lang).getProperty(key);
    }

    /**
     * 取英文国际化信息
     *
     * @param key
     * @return
     */
    public static String getStringEn(String key) {
        return getString("en", key);
    }

    /**
     * 获取多个国际化配置信息
     *
     * @param lang
     * @param keys
     * @return
     */
    public static String getMultString(String lang, String... keys) {
        Map<String, String> map = new HashMap<String, String>();

        Properties prop = loadI18nProp(lang);
        if (keys != null && keys.length > 0) {
            for (String key : keys) {
                map.put(key, prop.getProperty(key));
            }
        } else {
            for (String key : prop.stringPropertyNames()) {
                map.put(key, prop.getProperty(key));
            }
        }

        return JsonUtilByFsJson.beanToJson(map);
    }

    /**
     * 获取多个国际化配置信息
     *
     * @param prop
     * @param keys
     * @return
     */
    public static String getMultString(Properties prop, String... keys) {
        Map<String, String> map = new HashMap<String, String>();

        if (keys != null && keys.length > 0) {
            for (String key : keys) {
                map.put(key, prop.getProperty(key));
            }
        } else {
            for (String key : prop.stringPropertyNames()) {
                map.put(key, prop.getProperty(key));
            }
        }

        return JsonUtilByFsJson.beanToJson(map);
    }

    /**
     * 获取多个国际化配置信息
     *
     * @param keys
     * @return
     */
    public static String getMultStringEn(String... keys) {
        return getMultString("en", keys);
    }
}
