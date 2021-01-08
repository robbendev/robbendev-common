package com.robbendev.common.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 使用 ConcurrentMap 实现本地缓存
 *
 * @author robbendev
 */
public class LocalCacheUtil {

    private static ConcurrentMap<String, LocalCacheData> cacheRepository = new ConcurrentHashMap<>();

    /**
     * set cache
     *
     * @param key       key
     * @param val       val
     * @param cacheTime 过期时间
     * @return bool
     */
    public static boolean set(String key, Object val, long cacheTime) {

        // clean timeout cache, before set new cache (avoid cache too much)
        cleanTimeoutCache();

        // set new cache
        if (StringUtils.isBlank(key)) {
            return false;
        }
        if (val == null) {
            remove(key);
        }
        if (cacheTime <= 0) {
            remove(key);
        }
        long timeoutTime = System.currentTimeMillis() + cacheTime;
        LocalCacheData localCacheData = new LocalCacheData(key, val, timeoutTime);
        cacheRepository.put(localCacheData.getKey(), localCacheData);
        return true;
    }

    /**
     * remove cache
     *
     * @param key key
     * @return bool
     */
    public static boolean remove(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        cacheRepository.remove(key);
        return true;
    }

    /**
     * get cache
     *
     * @param key key
     * @return val
     */
    public static Object get(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        LocalCacheData localCacheData = cacheRepository.get(key);
        if (localCacheData != null && System.currentTimeMillis() < localCacheData.getTimeoutTime()) {
            return localCacheData.getVal();
        } else {
            remove(key);
            return null;
        }
    }

    /**
     * clean timeout cache
     */
    public static void cleanTimeoutCache() {
        if (!cacheRepository.keySet().isEmpty()) {
            for (String key : cacheRepository.keySet()) {
                LocalCacheData localCacheData = cacheRepository.get(key);
                if (localCacheData != null && System.currentTimeMillis() >= localCacheData.getTimeoutTime()) {
                    cacheRepository.remove(key);
                }
            }
        }
    }

    private static class LocalCacheData {
        private String key;
        private Object val;
        private long timeoutTime;

        public LocalCacheData() {

        }

        public LocalCacheData(String key, Object val, long timeoutTime) {
            this.key = key;
            this.val = val;
            this.timeoutTime = timeoutTime;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getVal() {
            return val;
        }

        public void setVal(Object val) {
            this.val = val;
        }

        public long getTimeoutTime() {
            return timeoutTime;
        }

        public void setTimeoutTime(long timeoutTime) {
            this.timeoutTime = timeoutTime;
        }
    }

}
