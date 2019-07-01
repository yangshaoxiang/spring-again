package com.springstudy.springstudy.spring_base.xmltest;

import com.springstudy.springstudy.entry.User;
import com.springstudy.springstudy.spring_base.annotationtest.dao.UserDao;
import com.springstudy.springstudy.spring_base.annotationtest.service.UserService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        User user = xmlApplicationContext.getBean("user",User.class);
        User user2 = xmlApplicationContext.getBean("user",User.class);
        System.out.println(user);
        if(user == user2){
            System.out.println("user单例");
        }else{
            System.out.println("user多例");
        }

        /*--------------------------------验证xml包扫描规则---------------------------------*/
        //配置了包扫描过滤 UserService 未添加到容器中
        try{
            UserService userService = xmlApplicationContext.getBean(UserService.class);
            System.out.println(userService);
        }catch (NoSuchBeanDefinitionException exception){
            System.out.println("容器中未发现 UserService");
        }

        try{
            // 注意 UserDao 采用包扫描自定义过滤规则过滤
            UserDao userDao = xmlApplicationContext.getBean(UserDao.class);
            System.out.println(userDao);
        }catch (NoSuchBeanDefinitionException exception){
            System.out.println("容器中未发现 userDao");
        }
    }
}
