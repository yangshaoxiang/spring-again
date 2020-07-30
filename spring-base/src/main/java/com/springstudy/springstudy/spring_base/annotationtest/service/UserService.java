package com.springstudy.springstudy.spring_base.annotationtest.service;

import com.springstudy.springstudy.entry.User;
import com.springstudy.springstudy.spring_base.annotationtest.dao.UserDao;
import com.springstudy.springstudy.spring_base.annotationtest.othor.StaticAutowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

@Service
@Import(UserDao.class)
public class UserService {
    @Autowired
    public static User user;

    @Autowired
    public void setUser(User user) {
        UserService.user = user;
    }

    public static void test(){
        System.out.println("UserService 里面的静态方法 name:"+user.getName()+"      age:"+user.getAge());
    }

}
