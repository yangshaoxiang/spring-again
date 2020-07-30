package com.springstudy.springstudy.spring_base.annotationtest;


import com.springstudy.springstudy.entry.EventModel;
import com.springstudy.springstudy.entry.Home;
import com.springstudy.springstudy.entry.ImportTest;
import com.springstudy.springstudy.entry.User;
import com.springstudy.springstudy.spring_base.annotationtest.dao.UserDao;
import com.springstudy.springstudy.spring_base.annotationtest.listener.SelfApplicationEvent;
import com.springstudy.springstudy.spring_base.annotationtest.service.UserService;
import com.springstudy.springstudy.spring_base.annotationtest.sometest.CountryMapper;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Modifier;

//注意ComponentScan中的配置仅仅是表示类是否被容器扫描，即使扫描到了，无类似@Controller @Service @Repository @Compent注解也不会被加载到容器中
@ComponentScan(
        //基础扫包
        basePackages={"com.springstudy.springstudy.spring_base.annotationtest"} ,
        //排除配置
        excludeFilters ={
                //根据注解排除
                @ComponentScan.Filter(type = FilterType.ANNOTATION,value = {RestController.class}),
                //根据类型排除
               // @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {UserService.class}),
                //自定义类型排除
                //@ComponentScan.Filter(type = FilterType.CUSTOM,value = {SelfScanFilterConfig.class})
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
@Import(ImportTest.class)
public class SelfMain {
    public static void main(String[] args) {
        // 指定配置类
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        // 设置环境
        annotationConfigApplicationContext.getEnvironment().setActiveProfiles("test","dev");
        //自定义环境验证 -- 一般这个环境指的是系统的环境变量，不存在会在启动时报错 验证在refresh().prepareRefresh();
        //annotationConfigApplicationContext.getEnvironment().setRequiredProperties("computer-id11");
        annotationConfigApplicationContext.register(SelfMain.class);
        // 做个刷新
        annotationConfigApplicationContext.refresh();
        // 验证 同名bean 丢弃后加载
      /*  User bean = (User)annotationConfigApplicationContext.getBean("user22222");
        System.out.println("类型获取:"+bean.getSs());*/
        // 验证基于注解配置Bean 默认单例 立即加载
        User user = annotationConfigApplicationContext.getBean("user", User.class);
        System.out.println(user);
        User user2 = annotationConfigApplicationContext.getBean("user",User.class);
        if(user == user2){
            System.out.println("user单例");
        }else{
            System.out.println("user多例");
        }

        //验证事件发布 自定义的事件监听器 com.springstudy.springstudy.spring_base.annotationtest.listener
        annotationConfigApplicationContext.publishEvent(new ApplicationEvent("手动发布事件") {});
        EventModel eventModel = new EventModel();
        eventModel.setEventName("自定义事件实体");
        annotationConfigApplicationContext.publishEvent(new SelfApplicationEvent(eventModel));

        /*--------------------------------验证@ComponentScan包扫描规则---------------------------------*/
        //配置了包扫描过滤 UserService 未添加到容器中
        try{
            UserService userService = annotationConfigApplicationContext.getBean(UserService.class);
            UserService.test();
            System.out.println(userService);

        }catch (NoSuchBeanDefinitionException exception){
            System.out.println("容器中未发现 UserService");
        }

        try{
            // 注意 UserDao 采用包扫描自定义过滤规则过滤
            UserDao userDao = annotationConfigApplicationContext.getBean(UserDao.class);
            System.out.println(userDao);
        }catch (NoSuchBeanDefinitionException exception){
            System.out.println("容器中未发现 userDao");
        }

        // 验证自定义条件注解
        try{
            Home home = annotationConfigApplicationContext.getBean(Home.class);
            System.out.println(home);
        }catch (NoSuchBeanDefinitionException exception){
            System.out.println("容器中未发现 home");
        }

        //验证环境配置注入
        try{
            Object environment = annotationConfigApplicationContext.getBean("environmentTest");
            System.out.println("自定义环境对象:"+environment);
        }catch (NoSuchBeanDefinitionException exception){
            System.out.println("容器中未发现 environmentTest");
        }

        //验证 @Import = @ComponentScan + @Component 即被扫描到，且可以加入到容器
        try{
            Object importTest = annotationConfigApplicationContext.getBean(ImportTest.class);
            System.out.println("导入测试对象:"+importTest);
        }catch (NoSuchBeanDefinitionException exception){
            System.out.println("容器中未发现 importTest");
        }

        try{
            CountryMapper countryMapper = annotationConfigApplicationContext.getBean(CountryMapper.class);
            System.out.println("导入动态注册对象:"+countryMapper);
        }catch (NoSuchBeanDefinitionException exception){
            System.out.println("容器中未发现 CountryMapper");
        }

        // @Lookup 使用场景 单例 bean 内注入 非单例 bean   https://www.jianshu.com/p/fc574881e3a2

    }


    // 计算对象的空闲时间，也就是没有被访问的时间，返回结果是毫秒
     public static long estimateObjectIdleTime() {
         // 获取 redis 时钟，也就是 server.lruclock 的值
        int lruclock = 16777216;
      /*  if (lruclock >= o->lru) {
            // 正常递增
            return (lruclock - o->lru) * LRU_CLOCK_RESOLUTION; // LRU_CLOCK_RESOLUTION 默认是 1000
        } else {
            // 折返了
            return (lruclock + (LRU_CLOCK_MAX - o->lru)) * // LRU_CLOCK_MAX 是 2^24-1
                    LRU_CLOCK_RESOLUTION;
        }*/
      return 0;
    }



}
