package com.springstudy.springstudy.spring_base.annotationtest.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SelfApplicationListener implements ApplicationListener {
    //接受到消息，回调该方法
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("SelfApplicationListener 接受到了一个事件"+event);
    }
}

 /*   public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        //手动发布一个事件
        ctx.publishEvent(new ApplicationEvent("我手动发布了一个事件") {
            @Override
            public Object getSource() {
                return super.getSource();
            }
        });

        //容器关闭也发布事件
        ctx.close();
    }*/