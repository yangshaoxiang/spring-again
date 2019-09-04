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
 * 相同，织入时查找事务相关属性先在实现类方法上找，再在类上，接口方法上，接口上找
 *
 * 调用时根据配置的事务属性实现对应的事务处理，一般是创建新事物，使用ThreadeLocal缓存事务对象，try包裹业务执行体，catch块回滚事务，finally块清除
 * 本次事务缓存，之后commit提交本次事务，只是commit时会判断当前的事务对象是否设置回滚属性，设置的话，不会提交会执行回滚操作，应用场景其实就是
 * 我们开发中常用的手动事务回滚 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
 * 对于事务中新开事务(事务传播机制为新开事务)
 * 在执行新事务时，将旧事务相关的缓存另外缓存一份到备份缓存,后将缓存清空填充新事务属性，新事务执行完毕后，从备份缓存中获取初始事务的信息重新缓存
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
 *  ps: 看源码时发现spring4和spring5有一点点改动，DataSourceUtils.doGetConnection()获取连接方法，在无事务情况下，4获取连接后直接使用，5会做非空判断，抛异常
 *
 * 动态数据源 AbstractRoutingDataSource 实现原理
 * 其实就是在上面说的获取连接时从数据源获取，关键在于数据源获取连接的实现，在配置动态数据源时会放入多个数据源到一个Map中，设置对应的键值，获取
 * 连接时，先获取到数据源，在通过数据源获取连接，通过determineCurrentLookupKey()方法获取map的key，其对应值就是数据源，该方法由我们自定义的多数据源类重写，
 * 由我们自己根据情况返回应该使用的数据源key。然后获取数据源，在之后获取连接
 *
 * spring结合mybatis后Mapper的创建流程
 * spring结合mybatis后，Mapper的创建是由MapperFactoryBean创建，它是一个FactoryBean，通过getObject()方法创建Mapper，在该方法内，通过获取
 * SqlSession对象调用其方法创建(内部实现基于动态代理)，因此重点就在这个sqlSession上，这个sqlSession其实是 sqlSessionTemplate
 * (该类实现SqlSession接口)，该对象是SqlSessionDaoSuprot创建(直接new)，sqlSessionTemplate创建SqlSession时创建时将自己本身传过去，
 * 在sqlSessionTemplate中有一个SqlSession类型的成员变量，该变量的创建是通过动态代理创建，在invoke方法中创建SqlSession
 * (通过Connection来创建，Connection先通过ThreadLocal获取，获取不到，从数据源获取)
 *
 * ssm或Springboot项目中 Service中注入的xxxMapper是否单例？
 * 是单例，如何保证线程安全？xxxMapper代理对象中包含sqlSessionTemplate，在sqlSessionTemplate中有一个
 * SqlSession类型的成员变量，该变量的创建是通过动态代理创建，在invoke方法中创建SqlSession(通过Connection来创建，Connection先通过ThreadLocal获取，获取不到，从数据源获取)。每次实际执行sql语句由该
 * 变量实际执行，因此保证线程安全
 *
 * 循环依赖问题
 * spring采用3级缓存来解决循环依赖问题，本质就是3个key为beanName的HashMap，第一级缓存本质是用于解决bean单例问题，对于单例bean直接从缓存获取
 * ，真正解决循环依赖问题是第二和第三级缓存，假设A,B互相依赖，A bean在创建时先创建一个原型Abean(即不包含属性值的bean)，将这个原型Abean放入到缓存中，
 * ，然后给A bean属性赋值，此时给属性B赋值时，需要先创建一个B，B在创建时需要A的引用，B此时会使用原型Abean，将B创建出来，B创建出来后，A即可正常赋值，
 * ，A赋值完成后B对于A的引用也无需更新，这样整个流程结束A,B都创建完成，因此属性循环依赖可以解决。
 *
 * 限制:
 * 如果构造方法参数相互依赖无法解决，因为无法创建原型bean
 * 如果是多例bean是无法相互依赖的，因为多例不会有3级缓存
 *
 * 问题:
 * 通过上面分析可以看出其实只是需要一个缓存去存放原型bean即可，2级缓存应该可以解决问题，为何需要3级缓存？
 * 2级缓存确实可以解决一般的循环依赖，但是在spring的体系中还有一个重要的特性 AOP 即动态代理，两个循环依赖的对象可能需要的并不是对应的对象本身，
 * 而是其代理对象，3级缓存第3级其实并没有直接缓存bean的原型对象，而是缓存的一个用于加工bean的工厂(该工厂带有原型bean的属性)，这个工厂根据
 * bean后置处理器情况，可能会对bean进行代理，返回代理对象，之后删除3级缓存将对象保存到二级缓存
 *
 */
public class MainClass {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        PayService payService = context.getBean("payService",PayService.class);
        payService.pay("111",10);
        System.out.println("结束。。。。。");
    }
}
