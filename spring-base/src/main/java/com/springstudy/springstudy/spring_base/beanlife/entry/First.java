package com.springstudy.springstudy.spring_base.beanlife.entry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 调用顺序总结:
 * -----------------------------------------------这里可以被插入 BeanPostProcessor 统一处理拦截器在执行初始化方法前拦截
 * 1.构造方法
 * 2.@PostConstruct修饰的方法
 * 3.InitializingBean接口的afterPropertiesSet()方法
 * 4.bean指定的init-method
 *------------------------------------------------这里可以被插入 BeanPostProcessor 统一处理拦截器在执行初始化方法后拦截
 * 5.PreDestroy修饰的方法
 * 6.DisposableBean接口的destroy() 方法
 * 7.bean指定的destory-method
 *
 * 总结:对于bean的初始化和销毁方法中 注解方式优先执行，其次接口方式，最后是bean指定的初始和销毁方法
 */
public class First  implements InitializingBean,DisposableBean {
    public First(){
        System.out.println("First 的 构造");
    }

    public void init(){
        System.out.println("First 的 init()");
    }
    public void destory(){
        System.out.println("First 的 destory()");
    }

    @PostConstruct
    public void initByPostConstruct(){
        System.out.println("First 的 initByPostConstruct()");
    }
    @PreDestroy
    public void destoryByPreDestroy(){
        System.out.println("First 的 destoryByPreDestroy()");
    }

    @Override
    public void destroy()  {
        System.out.println("First DisposableBean 的 destroy");
    }

    @Override
    public void afterPropertiesSet()  {
        System.out.println("First InitializingBean 的 afterPropertiesSet");
    }



}
