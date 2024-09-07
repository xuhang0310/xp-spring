package com.xp.business;

import com.xp.spring.XpApplicationContext;

public class TestMySpring {

    public static void main(String[] args) {
        XpApplicationContext xpApplicationContext = new XpApplicationContext(AppConfig.class);

        UserService userService = (UserService) xpApplicationContext.getBean("userService");

        UserService userService2 = (UserService) xpApplicationContext.getBean("userService");

        System.out.println(userService);
        System.out.println(userService2);
    }
}
