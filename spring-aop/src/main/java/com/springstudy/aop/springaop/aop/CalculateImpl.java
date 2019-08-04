package com.springstudy.aop.springaop.aop;

import org.springframework.aop.framework.AopContext;

public class CalculateImpl implements Calculate {

    public int add(int numA, int numB) {
        System.out.println("执行 add 方法");
        //测试异常时的通知
        // int a = 1/0;
        return numA+numB;
    }

    public int reduce(int numA, int numB) {
        System.out.println("执行 reduce 方法");
        return numA-numB;
    }

    public int div(int numA, int numB) {
        System.out.println("执行 div 方法");
        return numA/numB;
    }

    public int multi(int numA, int numB) {
        System.out.println("执行 multi 方法");
        return numA*numB;
    }


    public int useAdd(int numA, int numB) {
        System.out.println("执行 useAdd 方法");
        //当 执行useAdd方法时 add方法不会被代理
        //int result = this.add(numA, numB);
        //获取当前类的代理对象   @EnableAspectJAutoProxy(exposeProxy = true) 允许使用ThreadLocal缓存代理对象
        Calculate calculateProxy = (Calculate) AopContext.currentProxy();
        int result = calculateProxy.add(numA, numB);
        return result;
    }
}