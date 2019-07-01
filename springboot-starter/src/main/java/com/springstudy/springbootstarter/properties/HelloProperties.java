package com.springstudy.springbootstarter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("selfstarter.hello")
public class HelloProperties {
    //默认值
    private String msg = "starter-default";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
