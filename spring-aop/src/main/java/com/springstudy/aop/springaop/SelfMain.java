package com.springstudy.aop.springaop;


import com.springstudy.aop.springaop.aop.Calculate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


/**
 *  spring AOP 过程
 * 1.找到所有的增强器
 *    spring在集成AspectJ后使用注解@EnableAspectJAutoProxy开启AspectJ代理相关功能，实质上是往容器中注入一个AnnotationAwareAspectJAutoProxyCreator
 * 组件对象，该类有层层的继承和实现，其中有实现一个 InstantiationAwareBeanPostProcessor接口 该接口实现 BeanPostProcessor(bean的后置处理器)接口，
 * 在bean被创建的过程中会执行接口的 postProcessBeforeInstantiation()方法 找到项目中所有的切面(即@Aspect修饰的类)，由此找到所有的增强器
 * (即@Before，@After等注解修饰的方法)，并缓存对应切面的增强器
 *
 * 2.将增强器织入要代理的对象中，创建代理对象
 *   在bean创建过程中，填充完bean相关属性，回调aware相关接口，init相关方法后会回调 BeanPostProcessor(后置处理器)的 postProcessAfterInitialization()
 *   方法，在所有的增强器中找到所有满足目标对象方法的增强器，以此创建目标对象的代理对象
 *
 * 3.代理对象执行代理的方法，即被织入的增强器增强方法实现
 *   以JDK代理方式为例，执行代理对象的被代理方法时，进入invoke方法，通过责任链加递归的方式进行增强器的调用，递归终止条件为执行目标方法或中间异常
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
