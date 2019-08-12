package com.springstudy.springstudy.spring_base.annotationtest;


import com.springstudy.springstudy.entry.EventModel;
import com.springstudy.springstudy.entry.Home;
import com.springstudy.springstudy.entry.ImportTest;
import com.springstudy.springstudy.entry.User;
import com.springstudy.springstudy.spring_base.annotationtest.config.SelfScanFilterConfig;
import com.springstudy.springstudy.spring_base.annotationtest.dao.UserDao;
import com.springstudy.springstudy.spring_base.annotationtest.listener.SelfApplicationEvent;
import com.springstudy.springstudy.spring_base.annotationtest.service.UserService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.*;
import org.springframework.web.bind.annotation.RestController;

//注意ComponentScan中的配置仅仅是表示类是否被容器扫描，即使扫描到了，无类似@Controller @Service @Repository @Compent注解也不会被加载到容器中
@ComponentScan(
        //基础扫包
        basePackages={"com.springstudy.springstudy.spring_base.annotationtest"} ,
        //排除配置
        excludeFilters ={
                //根据注解排除
                @ComponentScan.Filter(type = FilterType.ANNOTATION,value = {RestController.class}),
                //根据类型排除
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {UserService.class}),
                //自定义类型排除
                @ComponentScan.Filter(type = FilterType.CUSTOM,value = {SelfScanFilterConfig.class})
        }

        /* ,//包含配置 表示基础扫包下 只包含意思
          includeFilters ={
          //根据注解包含
          @ComponentScan.Filter(type = FilterType.ANNOTATION,value = {RestController.class}),
          //根据类型包含
          @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {UserService.class})
          },
          // 结合includeFilters使用 false表示基础扫包中，只有includeFilters中才会加入到spring容器
          useDefaultFilters = false*/
)
@Import(ImportTest.class)
public class SelfMain {
    public static void main(String[] args) {
        // 指定配置类
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        // 设置环境
        annotationConfigApplicationContext.getEnvironment().setActiveProfiles("test","dev");
        annotationConfigApplicationContext.register(SelfMain.class);
        // 做个刷新
        annotationConfigApplicationContext.refresh();
        // 验证 同名bean 丢弃后加载
      /*  User bean = (User)annotationConfigApplicationContext.getBean("user22222");
        System.out.println("类型获取:"+bean.getSs());*/
        // 验证基于注解配置Bean 默认单例 立即加载
        User user = annotationConfigApplicationContext.getBean("user", User.class);
        System.out.println(user);
        User user2 = annotationConfigApplicationContext.getBean("user",User.class);
        if(user == user2){
            System.out.println("user单例");
        }else{
            System.out.println("user多例");
        }

        //验证事件发布 自定义的事件监听器 com.springstudy.springstudy.spring_base.annotationtest.listener
        annotationConfigApplicationContext.publishEvent(new ApplicationEvent("手动发布事件") {});
        EventModel eventModel = new EventModel();
        eventModel.setEventName("自定义事件实体");
        annotationConfigApplicationContext.publishEvent(new SelfApplicationEvent(eventModel));

        /*--------------------------------验证@ComponentScan包扫描规则---------------------------------*/
        //配置了包扫描过滤 UserService 未添加到容器中
        try{
            UserService userService = annotationConfigApplicationContext.getBean(UserService.class);
            System.out.println(userService);
        }catch (NoSuchBeanDefinitionException exception){
            System.out.println("容器中未发现 UserService");
        }

        try{
            // 注意 UserDao 采用包扫描自定义过滤规则过滤
            UserDao userDao = annotationConfigApplicationContext.getBean(UserDao.class);
            System.out.println(userDao);
        }catch (NoSuchBeanDefinitionException exception){
            System.out.println("容器中未发现 userDao");
        }

        // 验证自定义条件注解
        try{
            Home home = annotationConfigApplicationContext.getBean(Home.class);
            System.out.println(home);
        }catch (NoSuchBeanDefinitionException exception){
            System.out.println("容器中未发现 home");
        }

        //验证环境配置注入
        try{
            Object environment = annotationConfigApplicationContext.getBean("environmentTest");
            System.out.println("自定义环境对象:"+environment);
        }catch (NoSuchBeanDefinitionException exception){
            System.out.println("容器中未发现 environmentTest");
        }

        //验证 @Import = @ComponentScan + @Component 即被扫描到，且可以加入到容器
        try{
            Object importTest = annotationConfigApplicationContext.getBean(ImportTest.class);
            System.out.println("导入测试对象:"+importTest);
        }catch (NoSuchBeanDefinitionException exception){
            System.out.println("容器中未发现 importTest");
        }


    }



  /*  public void refresh() throws BeansException, IllegalStateException {
        synchronized (this.startupShutdownMonitor) {
            // Prepare this context for refreshing.
            prepareRefresh();

            // Tell the subclass to refresh the internal bean factory.
            ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

            // Prepare the bean factory for use in this context.
            prepareBeanFactory(beanFactory);

            try {
                // Allows post-processing of the bean factory in context subclasses.
                postProcessBeanFactory(beanFactory);

                // Invoke factory processors registered as beans in the context.
                *//**
                 * 将需要纳入容器的bean，生成BeanDef 并注册到bean的注册器中
                 *//*
                invokeBeanFactoryPostProcessors(beanFactory);

                // Register bean processors that intercept bean creation.
                registerBeanPostProcessors(beanFactory);

                // Initialize message source for this context.
                initMessageSource();

                // Initialize event multicaster for this context.
               // 初始化一个事件多播器，注册到容器中
                initApplicationEventMulticaster();

                // Initialize other special beans in specific context subclasses.
                // 默认是一个空实现 --》结合springboot后 里面非空
                onRefresh();

                // Check for listener beans and register them.
                *//**
                 *   1.获取容器中所有的事件监听器，注册到事件多播器中
                 *         获取框架的事件监听器，注册
                 *         获取我们自定义的事件监听器 注册
                 *   2.使用多播器广播容器中的早期事件(所谓早期事件指的是在多播器初始化之前产生的事件)---》 正常是没有，但是结合springboot之后会有，就在上一步的 onRefresh()方法
                 *         广播就是多播器获取他里面的所有监听器然后使用监听器去执行事件
                 *//*
                registerListeners();

                // Instantiate all remaining (non-lazy-init) singletons.
                finishBeanFactoryInitialization(beanFactory);

                // Last step: publish corresponding event.
                finishRefresh();
            }

            catch (BeansException ex) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Exception encountered during context initialization - " +
                            "cancelling refresh attempt: " + ex);
                }

                // Destroy already created singletons to avoid dangling resources.
                destroyBeans();

                // Reset 'active' flag.
                cancelRefresh(ex);

                // Propagate exception to caller.
                throw ex;
            }

            finally {
                // Reset common introspection caches in Spring's core, since we
                // might not ever need metadata for singleton beans anymore...
                resetCommonCaches();
            }
        }
    }*/
}
