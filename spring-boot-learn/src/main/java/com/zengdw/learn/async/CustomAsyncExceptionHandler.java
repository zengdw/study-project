package com.zengdw.learn.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * @author : zengd
 * @date : Created in 2021/1/19 16:14
 * @description: 异步方法未捕获异常自定义处理器
 * @version: 1.0
 */
@Slf4j
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        log.error("Exception message - {}", throwable.getMessage());
        log.error("Method name - {}", method.getName());
        for (Object param : obj) {
            log.error("Parameter value - {}", param);
        }
    }
}
