package com.xp.business;

import com.xp.spring.Autowired;
import com.xp.spring.Component;
import com.xp.spring.InitializingBean;

@Component
public class UserService implements InitializingBean {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderService orderService1;

    public void test(){
        System.out.println(orderService);

        System.out.println(orderService1);
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet-------");
    }
}
