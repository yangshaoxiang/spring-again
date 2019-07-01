package com.springstudy.springstudy.spring_base.beanlife.config;

import com.springstudy.springstudy.spring_base.beanlife.entry.First;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean(initMethod = "init",destroyMethod = "destory")
    public First first(){
        return new First();
    }
}
