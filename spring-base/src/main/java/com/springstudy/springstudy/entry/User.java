package com.springstudy.springstudy.entry;

import com.springstudy.springstudy.spring_base.annotationtest.othor.StaticValue;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


@Data
public class User {
    @StaticValue("${user.nname}")
    private  static String name;
    @Value("${user.age}")
    private Integer age;

    private String ss;


    public User(String s, int i,String ss) {
        System.out.println("User构造方法被调用:name:"+s+" age："+i);
        //this.name = s;
        this.age = i;
        this.ss = ss;
    }

    public User() {
    }

    public String getName() { return name; }


}
