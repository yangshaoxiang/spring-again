package com.springstudy.springstudy.spring_base.beanlife;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

//注意ComponentScan中的配置仅仅是表示类是否被容器扫描，即使扫描到了，无类似@Controller @Service @Repository @Compent注解也不会被加载到容器中
@ComponentScan(
        //基础扫包
        basePackages={"com.springstudy.springstudy.spring_base.beanlife"}
)
public class SelfMain {
    public static void main(String[] args) {
        // 指定配置类
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SelfMain.class);

        annotationConfigApplicationContext.close();
        // 验证基于注解配置Bean 默认单例 立即加载
      /*  User user = annotationConfigApplicationContext.getBean("user", User.class);
        System.out.println(user);*/


    }
}
