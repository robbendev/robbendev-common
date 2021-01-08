package com.robbendev.common.util;

import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合工具类 扩展org.apache.commons.collections
 *
 * @author robbendev
 */
public class ListUtil extends CollectionUtils {

    /**
     * 校验集合是否为空
     *
     * @param coll 入参
     * @return boolean
     */
    public static boolean isEmpty(Collection<?> coll) {
        return (coll == null || coll.isEmpty());
    }

    /**
     * 校验集合是否不为空
     *
     * @param coll 入参
     * @return boolean
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }


    /**
     * 根据对象属性判断是否存在
     *
     * @param coll   集合
     * @param func   属性方法
     * @param column 判断的值
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> boolean containsCol(Collection<T> coll, Function<T, R> func, R column) {
        return coll.stream()
                .map(func)
                .collect(Collectors.toSet())
                .contains(column);
    }

    public static <T, R> boolean containsObj(Collection<T> coll, Function<T, R> func, T obj) {
        return coll.stream()
                .map(func)
                .collect(Collectors.toSet())
                .contains(func.apply(obj));
    }

    /**
     * 根据字段属性值获取集合中的元素
     *
     * @param coll   源列表
     * @param func   字段方法引用
     * @param column 字段匹配值
     * @param <T>    列表元素对象类型
     * @param <R>    匹配的字段
     * @return 匹配的元素
     */
    public static <T, R> T getOne(Collection<T> coll, Function<T, R> func, @NonNull R column) {

        return coll.stream()
                .filter(item -> column.equals(func.apply(item)))
                .findFirst()
                .orElse(null);
    }
}
