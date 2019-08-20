package com.springstudy.transaction.springtransaction;

import com.springstudy.transaction.springtransaction.config.MainConfig;
import com.springstudy.transaction.springtransaction.service.PayService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 对于spring 事务，也是采用AOP去实现
 *
 * 使用注解方式启动spring事务会添加@EnableTransactionManagement注解 该注解的作用其实是在事务中会注入
 * InfrastructureAdvisorAutoProxyCreator 组件对象
 * 和spring AOP注入的那个组件对象类似，同样实现一个 InstantiationAwareBeanPostProcessor接口 该接口实现 BeanPostProcessor(bean的后置处理器)接口，
 *
 * 同时@EnableTransactionManagement注解还会注入 事务拦截器(和AOP切面一个概念)，事务增强器(和AOP增强器是一个概念)，事务注解解析器
 *
 * 对于AOP实现事务而言，不需要像一般的AOP那样缓存切面及增强器，因为事务是固定的增强器直接获取无需再做一层缓存，织入时机和spring AOP织入时机
 * 相同，织入时查找事务相关属性先在实现类方法上找，在再类上，接口方法上，接口上找
 *
 * 调用时根据配置的事务属性实现对应的事务处理，一般是创建新事物，使用ThreadeLocal缓存事务对象，try包裹业务执行体，catch块回滚事务，finally块清除
 * 本次事务缓存，之后commit提交本次事务，只是commit时会判断当前的事务对象是否设置回滚属性，设置的话，不会提交会执行回滚操作，应用场景其实就是
 * 我们开发中常用的手动事务回滚 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
 * 对于事务中新开事务(事务传播机制为新开事务)
 * 在执行新事务时，将旧事务相关的缓存另外缓存一份到备份缓存,后将缓存清空填充新事务属性，新事务执行完毕后，从备份缓存中获取初始事务的信息重新缓存
 *
 *
 * 何时获取和归还数据库链接？
 *
 * 打开一个数据库连接就是在调用业务层方法时，如果该方法被事务修饰(即注解配置或xml声明式事务配置)那么，业务对象被代理，在代理实现中会创建一个
 * 新事务，此时会打开一个数据库连接，并将连接包装为一个连接持有对象作为值保存到键为数据源的HashMap中，将HashMap作为值保存到ThreadLocal中，
 * 在整个业务方法中一般所有数据库操作需要的数据库连接都是从ThreadLocal中获取(数据库操作)，保证获取到的是同一链接，以此保证事务，因此如果业务
 * 方法中是多数据源的操作此时会存在多个链接是无法保证事务的。当执行完业务方法后，事务提交时会归还链接至连接池。
 *
 * 如果业务方法未被事务修饰,也未被其他AOP切面拦截，此时获取连接的时机是具体执行数据库操作时进行，先在ThreadLocal中获取，获取不到从数据源连接池
 * 获取，执行完本次数据操作后即把连接归还给连接池
 */
public class MainClass {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        PayService payService = context.getBean("payService",PayService.class);
        payService.pay("111",10);
    }
}
