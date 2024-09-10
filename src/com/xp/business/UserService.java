package com.xp.business;

import com.xp.spring.Autowired;
import com.xp.spring.Component;

@Component
public class UserService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderService orderService1;

    public void test(){
        System.out.println(orderService);

        System.out.println(orderService1);
    }

}
