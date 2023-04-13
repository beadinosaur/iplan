package com.example.iplan_data.core;

import com.example.iplan_data.util.CheckUtil;
import com.example.iplan_data.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Log calls to the controller
 *
 * @author xieguangwei
 * @since 2023-04-13
 */
@Aspect
@Component
@Slf4j(topic = "controllerLogger")
public class LogAspect {
    private static final ThreadLocal<Long> timeTreadLocal = new ThreadLocal<>();

    @Pointcut("execution(* com.example.iplan_data.controller.*.*(..))")
    private void logger() {
    }

    @Before("logger()")
    public void before(JoinPoint joinPoint) {
        timeTreadLocal.set(System.currentTimeMillis());
        //The request is received, and the content of the request is logged
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //Get the requested
        HttpServletRequest request = attributes.getRequest();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //Retrieve the blocked method
        Method method = methodSignature.getMethod();
        //Get the name of the intercepted method
        String methodName = method.getName();
        log.info("Aop begin,the start method：{}", method.getDeclaringClass() + "." + methodName + "()");
        //Get the key and value of all request parameters
        String keyValue = Utils.getReqParameter(request);
        log.info("request url = {}", request.getRequestURL().toString());
        log.info("request ip = {}", request.getRemoteAddr());
        log.info("request method = {}", request.getMethod());
        log.info("request uri = {}", request.getRequestURI());
        log.info("request key：value = {}", keyValue);
        log.info("agent = {}", request.getHeader("user-agent"));



    }

    @After("logger()")
    public void after() {
    }

    /**
     * controller called when the request returns
     */
    @AfterReturning(returning = "result", pointcut = "logger()")
    public Object afterReturn(Object result) {
        if (!CheckUtil.isEmpty(result)) {
            long startTime = timeTreadLocal.get();
            double callTime = (System.currentTimeMillis() - startTime) / 1000.0;
            log.info("Time spent calling the controller time = {}s", callTime);
            return result;
        } else {
            return null;
        }
    }
}
