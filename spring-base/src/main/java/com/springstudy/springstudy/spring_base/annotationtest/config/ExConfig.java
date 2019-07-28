package com.springstudy.springstudy.spring_base.annotationtest.config;

import com.springstudy.springstudy.entry.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class ExConfig {

    @Bean
    //默认单例 通过prototype指定多例
    //@Scope("prototype")
    //单例默认立即加载，通过@Lazy指定懒加载
    //@Lazy
    //这个不是循环依赖，只是标志实例化该类，需要先实例化依赖类 表明一种实例化顺序 用@Conditional()也可实现
    @DependsOn({""})
    public User user(){
        // return  new User("spring-ani-config",13);
        return  new User();
    }
}
