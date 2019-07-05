package com.springstudy.springstudy.spring_base.annotationtest.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SelfApplicationListener implements ApplicationListener {
    //接受到消息，回调该方法
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(getCurrentDate()+"  SelfApplicationListener 接受到了一个事件 开始处理"+event);

        // 这里睡眠测试 自定义可异步的多播器
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getCurrentDate() + "  SelfApplicationListener 接受到了一个事件 结束处理"+event);
    }

    public String getCurrentDate(){
        Date date = new Date();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
