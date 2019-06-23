package com.springstudy.springstudy.spring_base.annotationtest;


import com.springstudy.springstudy.entry.User;
import com.springstudy.springstudy.spring_base.annotationtest.dao.UserDao;
import com.springstudy.springstudy.spring_base.annotationtest.service.UserService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SelfMain {
    public static void main(String[] args) {
        // 指定配置类
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);

        // 验证基于注解配置Bean 默认单例 立即加载
        User user = annotationConfigApplicationContext.getBean("user", User.class);
        System.out.println(user);
        User user2 = annotationConfigApplicationContext.getBean("user",User.class);
        if(user == user2){
            System.out.println("user单例");
        }else{
            System.out.println("user多例");
        }

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


    }
}
