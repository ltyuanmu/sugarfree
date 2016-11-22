package com.sugarfree.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * http连接请求工具类
 * @author lt
 *
 */
public class HttpRequestUtil {


    private HttpRequestUtil() {
        super();
    }

    /**
     * 获得http请求
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }


}