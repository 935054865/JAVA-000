package com.mysql.demo.handler;

import com.mysql.demo.common.DbType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DbInterceptor {

    //全部service层请求都走这里，ThreadLocal才能有DbType值
    private final String pointcut = "execution(* com.mysql.demo.core.service..*.*(..))";

    @Pointcut(value = pointcut)
    public void dbType() {
    }

    @Before("dbType()")
    void before(JoinPoint joinPoint) {
        System.out.println("before...");

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        DbType dbType = method.getAnnotation(DbType.class);
        //设置Db
        DbContextHandler.setDb(dbType == null ? false : dbType.isMaster());
    }

    @After("dbType()")
    void after() {
        System.out.println("after...");

        DbContextHandler.remove();
    }
}
