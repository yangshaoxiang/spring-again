package com.springstudy.aop.springaop;


import com.springstudy.aop.springaop.aop.Calculate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


/**
 * AOP 过程
 * 1、
 */
@ComponentScan("com.springstudy.aop.springaop")
public class SelfMain {
    public static void main(String[] args) {
        // 指定配置类
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SelfMain.class);
        Calculate calculate = annotationConfigApplicationContext.getBean("calculate", Calculate.class);
        calculate.useAdd(1,2);

    }


}
