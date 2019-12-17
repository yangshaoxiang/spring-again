package com.springstudy.aop.springaop.proxy;

import com.springstudy.aop.springaop.aop.Calculate;
import com.springstudy.aop.springaop.aop.CalculateImpl;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;


public class JavassistProxy {

    public static void main(String[] args) throws Exception{
        Calculate javassistProxy = (CalculateImpl)getJavassistProxy(CalculateImpl.class);
        // 通过代理对象调用目标方法
        long currentTimeMillis = System.currentTimeMillis();
        for(int i=0;i<10000000;i++){
            // 代理对象执行
            javassistProxy.getSum(100);
        }
        System.out.println(System.currentTimeMillis()-currentTimeMillis);


    }

    /*
     * 要代理的对象的class
     * */
    @SuppressWarnings("deprecation")
    public static Object getJavassistProxy(Class clazz) throws InstantiationException, IllegalAccessException {
        // 代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
        // 设置需要创建子类的父类
        proxyFactory.setSuperclass(clazz);

        //定义一个拦截器。在调用目标方法时，Javassist会回调MethodHandler接口方法拦截,类似JDK中的InvocationHandler接口
        proxyFactory.setHandler(new MethodHandler() {
            /**
             *
             * @param proxyObject Javassist动态生成的代理类实例
             * @param targetMethod  当前要调用的原始方法
             * @param proxyMethod 生成的代理类对方法的代理引用
             * @param methodArgs 方法参数列表
             * @return 从代理实例的方法调用返回的值
             */
            public Object invoke(Object proxyObject, Method targetMethod, Method proxyMethod, Object[] methodArgs) throws Throwable {
               // System.out.println("--------------------------------");
                return proxyMethod.invoke(proxyObject, methodArgs);
            }
        });

        // 通过字节码技术动态创建子类实例
        return  proxyFactory.createClass().newInstance();
    }

}



