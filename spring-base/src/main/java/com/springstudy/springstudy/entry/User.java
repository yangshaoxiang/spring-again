package com.springstudy.springstudy.entry;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class User {
    @Value("${user.nname}")
    private String name;
    @Value("${user.age}")
    private Integer age;

    public User(String s, int i) {
        System.out.println("User构造方法被调用");
        this.name = s;
        this.age = i;
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
