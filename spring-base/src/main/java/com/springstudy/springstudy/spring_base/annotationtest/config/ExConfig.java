package com.springstudy.springstudy.spring_base.annotationtest.config;

import com.springstudy.springstudy.entry.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExConfig {



    //若容器中有同名同类的bean 后加载的bean会被舍弃，修改为不同名后会放到容器
   //@Bean("user11")
    @Bean
    public User user(){
        System.out.println("调用了ExConfig.user");
        // return  new User("spring-ani-config",13);
        return  new User("ex",1,"ex");
    }
}
