package com.funi.distributedcomputer.protal.controller.support;

import java.lang.annotation.*;

/**
 * 匿名访问注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Anonymous {
}
