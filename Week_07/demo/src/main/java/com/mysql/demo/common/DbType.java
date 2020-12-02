package com.mysql.demo.common;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DbType {
    boolean isMaster() default true;
}