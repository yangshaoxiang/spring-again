package com.springstudy.aop.springaop.config;


import com.springstudy.aop.springaop.aop.Calculate;
import com.springstudy.aop.springaop.aop.CalculateImpl;
import com.springstudy.aop.springaop.aop.SelfLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class MainConfig {

    @Bean
    public Calculate calculate() {
        return new CalculateImpl();
    }

    @Bean
    public SelfLogAspect tulingLogAspect() {
        return new SelfLogAspect();
    }
}