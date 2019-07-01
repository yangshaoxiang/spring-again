package com.springstudy.springstudy.spring_base.annotationtest.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 自定义 @Condition 条件注入的条件 返回true表示符合条件
 */
public class SelfConditionConfig implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        if(conditionContext.getBeanFactory().containsBean("user")){
            return true;
        }
        return false;
    }
}
