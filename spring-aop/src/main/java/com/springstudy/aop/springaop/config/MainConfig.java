package com.springstudy.aop.springaop.config;


import com.springstudy.aop.springaop.aop.Calculate;
import com.springstudy.aop.springaop.aop.CalculateImpl;
import com.springstudy.aop.springaop.aop.SelfLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
//注解表示允许使用AspectJ系列产生的的代理 exposeProxy = true 表示允许使用ThreadLocal缓存代理对象 这样在编码中可以手动获取当前代理对象
//proxyTargetClass=true 表示强制使用cglib代理
@EnableAspectJAutoProxy(exposeProxy = true,proxyTargetClass=true )
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