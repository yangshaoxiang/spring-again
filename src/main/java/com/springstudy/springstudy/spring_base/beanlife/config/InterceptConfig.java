package com.springstudy.springstudy.spring_base.beanlife.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class InterceptConfig implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("统一的 bean 初始化前拦截 beanName:"+beanName);
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("统一的 bean 初始化后拦截 beanName:"+beanName);
        return bean;
    }
}
