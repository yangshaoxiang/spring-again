package com.springstudy.aop.springaop.aop;

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
}