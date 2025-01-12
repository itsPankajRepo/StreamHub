package com.stream.hub.userMgmt.annotation;

import com.stream.hub.userMgmt.enums.ApiAccessValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiAccessType {
    ApiAccessValue value() default ApiAccessValue.PUBLIC;
}
