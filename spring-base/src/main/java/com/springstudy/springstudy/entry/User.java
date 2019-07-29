package com.springstudy.springstudy.entry;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class User {
    @Value("${user.nname}")
    private String name;
    @Value("${user.age}")
    private Integer age;

    private String ss;

    public User(String s, int i,String ss) {
        System.out.println("User构造方法被调用:name:"+s+" age："+i);
        this.name = s;
        this.age = i;
        this.ss = ss;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
