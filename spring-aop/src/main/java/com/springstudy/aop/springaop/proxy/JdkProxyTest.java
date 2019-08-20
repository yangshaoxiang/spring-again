package com.springstudy.aop.springaop.proxy;

import com.springstudy.aop.springaop.aop.Calculate;
import com.springstudy.aop.springaop.aop.CalculateImpl;

import java.lang.reflect.Proxy;

/**
 *  为何 jdk代理 无法代理 没有接口的对象？
 *  因为 动态代理的对象必须持有 和原对象高度相同的 方法 一般只能通过 实现同一接口或继承达成目的，jdk动态代理采用的是实现接口的方式，为何不采用继承方式？因为代理对象
 *  已经继承jdk的Proxy类，java中是无法多继承的
 */
public class JdkProxyTest {
    public static void main(String[] args) {
        // 1、生成$Proxy0的class文件  注意生成在 classpath 下
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        CalculateImpl target = new CalculateImpl();
        Calculate calculate = (Calculate)Proxy.newProxyInstance(Calculate.class.getClassLoader(), CalculateImpl.class.getInterfaces(), (proxy, method, args1) -> {
            System.out.println("jdk 方法代理拦截" + method);
            return  method.invoke(target, args1);
        });
        int add = calculate.add(1, 2);
        System.out.println(add);

    }
}
