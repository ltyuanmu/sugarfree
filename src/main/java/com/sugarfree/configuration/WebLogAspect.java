package com.sugarfree.configuration;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class WebLogAspect {
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.sugarfree.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        //log.info("request url is :{}",request.getRequestURI());
       // log.info("request param is :{}", new Gson().toJson(request.getParameterMap()));

        String   url  = request.getScheme()+"://"; //请求协议 http 或 https
        url+=request.getHeader("host");  // 请求服务器
        url+=request.getRequestURI();     // 工程名
        if(request.getQueryString()!=null) //判断请求参数是否为空
            url+="?"+request.getQueryString();   // 参数

        log.info("request url is :{}",url);
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("response is :{} ",new Gson().toJson(ret));
        log.info("spend time : {} " ,System.currentTimeMillis() - startTime.get());
    }

}
