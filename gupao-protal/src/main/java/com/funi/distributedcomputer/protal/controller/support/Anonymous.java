package com.funi.distributedcomputer.protal.controller.support;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Anonymous {
}
