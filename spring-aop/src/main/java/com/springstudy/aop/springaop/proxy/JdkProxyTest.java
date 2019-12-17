package com.springstudy.aop.springaop.proxy;

import com.springstudy.aop.springaop.aop.Calculate;
import com.springstudy.aop.springaop.aop.CalculateImpl;

import java.lang.reflect.Proxy;

/**
 *  为何 jdk代理 无法代理 没有接口的对象？
 *  因为 动态代理的对象必须持有 和原对象高度相同的 方法 一般只能通过 实现同一接口或继承达成目的，jdk动态代理采用的是实现接口的方式，为何不采用继承方式？因为代理对象
 *  已经继承jdk的Proxy类，java中是无法多继承的
 *
 *  cg 785
 * jdk 700
 * assit 725
 * 1000w 次方法调用时间  代理完成后，方法执行基本没有区别
 */
public class JdkProxyTest {
    public static void main(String[] args) {
        // 目标对象
        CalculateImpl target = new CalculateImpl();
        // jdk 生成代理对象
        Calculate jdkProxy = getJdkProxy(target);
        // 通过代理对象调用目标方法
        long currentTimeMillis = System.currentTimeMillis();
        for(int i=0;i<10000000;i++){
            // 代理对象执行
            jdkProxy.getSum(100);
        }
        System.out.println(System.currentTimeMillis()-currentTimeMillis);

    }

    /**
     *  jdk 生成代理对象
     * @param target 要被代理的对象
     * @return 代理对象
     */
    private static Calculate getJdkProxy(Calculate target){
        // 1、生成$Proxy0的class文件  注意生成在 classpath 下
        // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        return (Calculate)Proxy.newProxyInstance(Calculate.class.getClassLoader(), CalculateImpl.class.getInterfaces(), (proxy, method, args1) -> {
          //  System.out.println("jdk 方法代理拦截" + method);
            return  method.invoke(target, args1);
        });
    }
}
