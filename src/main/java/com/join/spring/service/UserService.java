package com.join.spring.service;

import com.join.spring.dao.UserDAO;
import com.join.spring.join_spring.Autowired;
import com.join.spring.join_spring.ClassPathXmlApplicationContext;
import com.join.spring.model.User;

public class UserService {

    @Autowired
    private UserDAO userDAO;

    public UserService() throws Exception{
//        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext();
//        this.userDAO = (UserDAO)classPathXmlApplicationContext.getBean("userDao");
    }

    public void add(User u){
        userDAO.save(u);
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
