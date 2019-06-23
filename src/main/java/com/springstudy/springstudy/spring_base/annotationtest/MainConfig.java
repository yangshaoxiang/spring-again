package com.springstudy.springstudy.spring_base.annotationtest;

import com.springstudy.springstudy.spring_base.annotationtest.config.SelfScanFilterConfig;
import com.springstudy.springstudy.spring_base.annotationtest.service.UserService;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.context.annotation.FilterType;
import org.springframework.web.bind.annotation.RestController;


//注意ComponentScan中的配置仅仅是表示类是否被容器扫描，即使扫描到了，无类似@Controller @Service @Repository @Compent注解也不会被加载到容器中
@ComponentScan(
        //基础扫包
        basePackages={"com.springstudy.springstudy.spring_base.annotationtest"} ,
        //排除配置
        excludeFilters ={
        //根据注解排除
        @ComponentScan.Filter(type = FilterType.ANNOTATION,value = {RestController.class}),
        //根据类型排除
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {UserService.class}),
        //自定义类型排除
        @ComponentScan.Filter(type = FilterType.CUSTOM,value = {SelfScanFilterConfig.class})
        }

      /* ,//包含配置 表示基础扫包下 只包含意思
        includeFilters ={
        //根据注解包含
        @ComponentScan.Filter(type = FilterType.ANNOTATION,value = {RestController.class}),
        //根据类型包含
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {UserService.class})
        },
        // 结合includeFilters使用 false表示基础扫包中，只有includeFilters中才会加入到spring容器
        useDefaultFilters = false*/
        )
class MainConfig {

}
