package com.springstudy.aop.springaop.proxy;

import com.springstudy.aop.springaop.aop.Calculate;
import com.springstudy.aop.springaop.aop.CalculateImpl;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyTest {
    public static void main(String[] args) {

        // 创建代理对象
        Calculate cgLibProxy = getCgLibProxy(new CalculateImpl());
        // 通过代理对象调用目标方法
        long currentTimeMillis = System.currentTimeMillis();
        for(int i=0;i<10000000;i++){
            cgLibProxy.getSum(100);
        }
        System.out.println(System.currentTimeMillis()-currentTimeMillis);
    }

    private static Calculate getCgLibProxy(Calculate target){
        // 代理类class文件存入本地磁盘方便我们反编译查看源码
        // System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code\\cglib");
        // 通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        // 设置enhancer对象的父类
        enhancer.setSuperclass(CalculateImpl.class);
        // 设置enhancer的回调对象
        enhancer.setCallback(new MethodInterceptor() {
            /**
             * o：cglib生成的代理对象
             * method：被代理对象方法
             * objects：方法入参
             * methodProxy: 代理方法
             */
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
               // System.out.println("======插入前置通知======");
                Object object = methodProxy.invokeSuper(o, objects);
               // System.out.println("======插入后者通知======");
               // System.out.println(method.getName());
                return object;
            }
        });
        // 创建代理对象
        return (CalculateImpl)enhancer.create();
    }

}


