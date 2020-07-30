package com.springstudy.springstudy.spring_base.annotationtest.othor;

import java.lang.annotation.*;

/**
 * @Description: 注入静态属性值
 * @Author: ysx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface StaticValue {
    String value();
}
