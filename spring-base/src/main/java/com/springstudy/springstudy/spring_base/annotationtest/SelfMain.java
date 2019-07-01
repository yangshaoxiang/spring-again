package com.springstudy.springstudy.spring_base.annotationtest;


import com.springstudy.springstudy.entry.Home;
import com.springstudy.springstudy.entry.ImportTest;
import com.springstudy.springstudy.entry.User;
import com.springstudy.springstudy.spring_base.annotationtest.config.SelfScanFilterConfig;
import com.springstudy.springstudy.spring_base.annotationtest.dao.UserDao;
import com.springstudy.springstudy.spring_base.annotationtest.service.UserService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
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
@Import(ImportTest.class)
public class SelfMain {
    public static void main(String[] args) {
        // 指定配置类
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        // 设置环境
        annotationConfigApplicationContext.getEnvironment().setActiveProfiles("test","dev");
        annotationConfigApplicationContext.register(SelfMain.class);
        //做个刷新
        annotationConfigApplicationContext.refresh();
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

        /*--------------------------------验证@ComponentScan包扫描规则---------------------------------*/
        //配置了包扫描过滤 UserService 未添加到容器中
        try{
            UserService userService = annotationConfigApplicationContext.getBean(UserService.class);
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


    }
}
