package com.springstudy.aop.springaop.jdkproxy;

import com.springstudy.aop.springaop.aop.Calculate;
import com.springstudy.aop.springaop.aop.CalculateImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        CalculateImpl target = new CalculateImpl();
        Calculate calculate = (Calculate)Proxy.newProxyInstance(Calculate.class.getClassLoader(), CalculateImpl.class.getInterfaces(), (proxy, method, args1) -> {
            System.out.println("jdk 方法代理拦截" + method);
            return  method.invoke(target, args1);
        });
        int add = calculate.add(1, 2);
        System.out.println(add);

    }
}
