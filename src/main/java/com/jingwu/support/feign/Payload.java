package com.jingwu.support.feign;

/**
 * @author 菁芜
 * @since 2021/7/26 - 10:40
 */
public interface Payload<T> {
    T parseData();
}
