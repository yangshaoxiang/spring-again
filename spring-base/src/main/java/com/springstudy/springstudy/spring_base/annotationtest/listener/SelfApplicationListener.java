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
