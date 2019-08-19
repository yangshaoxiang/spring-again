package com.springstudy.transaction.springtransaction.threadlocal;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *  测试 InheritableThreadLocal
 * InheritableThreadLocal 是可将 ThreadLocal 中数据传递到子线程中的对象，其实现原理是在主线程创建线程时，复制当前线程(主线程)ThreadLocal数据到即将创建的线程中去
 * 注意点:
 *   子线程真的只是复制一份主线程的 InheritableThreadLocal 对应的值，而不是共享值，因此无论父子线程如何修改所保存的值，都对对方无影响
 */
public class InheritableThreadLocalTest {
    private static InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws Exception {
        inheritableThreadLocal.set("main-inheritableThreadLocal");
        threadLocal.set("main-threadLocal");
        Thread thread = new Thread(() -> {
            System.out.println("子线程 inheritableThreadLocal 值: " + inheritableThreadLocal.get());
            System.out.println("子线程 threadLocal 值: " + threadLocal.get());
            inheritableThreadLocal.set("child-inheritableThreadLocal");
            System.out.println("子线程 变更 inheritableThreadLocal 值: "+inheritableThreadLocal.get());
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程 inheritableThreadLocal 值: " + inheritableThreadLocal.get());
        });
        thread.start();
        Thread.sleep(1000L);
        System.out.println("主线程 inheritableThreadLocal 值: "+inheritableThreadLocal.get());
        inheritableThreadLocal.set("main11-inheritableThreadLocal");
        System.out.println("主线程 变更 inheritableThreadLocal 值: "+inheritableThreadLocal.get());
    }

    /**
     * 直接依据 线程对象和 ThreadLocal 对象 获取线程中ThreadLocalMap中的值
     * @param threadLocal 希望获取到值的 threadLocal 对象 - 即线程中 ThreadLocalMap 的key
     * @param hopeThread 希望取值的线程
     * @return 对应线程环境下调用 threadLocal.get()应当获取的值
     */
    private static Object threadLocalGet(ThreadLocal threadLocal,Thread hopeThread)  {
        try{
            // 获取 线程下的 ThreadLocalMap
            Class<? extends ThreadLocal> threadLocalClass = threadLocal.getClass();
            Method getMapMethod = threadLocalClass.getDeclaredMethod("getMap", Thread.class);
            getMapMethod.setAccessible(true);
            Object threadLocalMap = getMapMethod.invoke(threadLocal, hopeThread);

            // 获取 ThreadLocalMap 下 key 为 传入的 ThreadLocal 的 entry
            Class<?> threadLocalMapClass = threadLocalMap.getClass();
            Method getEntryMethod = threadLocalMapClass.getDeclaredMethod("getEntry",ThreadLocal.class);
            getEntryMethod.setAccessible(true);
            Object ThreadLocalMapEntry = getEntryMethod.invoke(threadLocalMap, threadLocal);

            //  获取 ThreadLocal 的 entry 下的值
            Class<?> threadLocalMapEntryClass = ThreadLocalMapEntry.getClass();
            Field valueField = threadLocalMapEntryClass.getDeclaredField("value");
            valueField.setAccessible(true);
            Object entryValue = valueField.get(ThreadLocalMapEntry);
            System.out.println("工具方法中获取值: "+entryValue);
            return entryValue;
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }
}

