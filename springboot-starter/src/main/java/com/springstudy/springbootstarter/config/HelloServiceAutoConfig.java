package com.springstudy.springbootstarter.config;

import com.springstudy.springbootstarter.properties.HelloProperties;
import com.springstudy.springbootstarter.service.HelloService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;

@Configuration
@EnableConfigurationProperties(HelloProperties.class)
//@ConditionalOnClass(HelloService.class)
/**
 * @ConditionalOnProperty 注解
 * 通过其两个属性name以及havingValue来实现的，其中name用来从application.properties中读取某个属性值。
 * 如果该值为空，则返回false;
 * 如果值不为空，则将该值与havingValue指定的值进行比较，如果一样则返回true;否则返回false。
 * 如果返回值为false，则该configuration不生效；为true则生效。
 *
 *
 */
//@ConditionalOnProperty(prefix="selfstarter.hello")
public class HelloServiceAutoConfig {
    @Resource
    private HelloProperties helloProperties;

    @Bean
   // @ConditionalOnMissingBean(HelloService.class)
    public HelloService helloService(){
        // System.out.println("自动装配helloService:"+HelloService.class.getName());
        HelloService helloService = new HelloService();
        helloService.setMsg(helloProperties.getMsg());




        return helloService;
    }



}
