package com.springstudy.springstudy.spring_base.beanlife.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * spring的后置处理器 AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization 1770行
 */
@Component
public class SelfBeanPostProcessorConfig implements BeanPostProcessor {


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
