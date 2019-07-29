package com.springstudy.springstudy.spring_base.annotationtest.config;

import com.springstudy.springstudy.entry.EnvironmentTest;
import com.springstudy.springstudy.entry.Home;
import com.springstudy.springstudy.entry.User;
import org.springframework.context.annotation.*;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@PropertySource(value = {"classpath:user.properties"})
public class SelfConfig {

    //bean名称默认方法名 可在@Bean注解指定
    @Bean("user")
    //默认单例 通过prototype指定多例
    //@Scope("prototype")
    //单例默认立即加载，通过@Lazy指定懒加载
    //@Lazy
    public User user(){
       // return  new User("spring-ani-config",13);
        return  new User();
    }

    @Bean
    //自定义条件注解 当容器中包含 user时才能创建home
    @Conditional(SelfConditionConfig.class)
    public Home home(){
        return new Home(3,"楼房");
    }


    @Bean("environmentTest")
    @Profile("pro")
    public EnvironmentTest environmentTest(){
        return new EnvironmentTest();
    }

    /**
     *  配置支持异步的事件多播器
     *   配置原因:
     *   1. 事件多播器的第一次使用是在reflush().initApplicationEventMulticaster()中使用
     *      根据代码逻辑，若容器中有 SimpleApplicationEventMulticaster 则使用容器中的，否则创建一个默认的非异步的多播器
     *
     *   2. 因此我们自己配置一个带有线程池的多播器即可支持异步，问题是多播器的另外一个参数 BeanFactory 如何配置
     *      答案是不用配置 SimpleApplicationEventMulticaster 实现了 BeanFactoryAware 接口 在容器创建bean时 会自动注入该参数
     *      在 AbstractAutowireCapableBeanFactory.invokeAwareMethods()中注入
     */
    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster(){
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
        simpleApplicationEventMulticaster.setTaskExecutor(Executors.newFixedThreadPool(10));
        return simpleApplicationEventMulticaster;
    }

    //多播器不能用
}
