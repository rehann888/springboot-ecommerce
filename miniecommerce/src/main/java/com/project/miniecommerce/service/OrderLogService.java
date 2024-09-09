package com.project.miniecommerce.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.miniecommerce.entity.CustomerOrder;
import com.project.miniecommerce.entity.OrderLog;
import com.project.miniecommerce.entity.User;
import com.project.miniecommerce.repository.OrderlogRepo;

@Service
public class OrderLogService {
    
    @Autowired
    private OrderlogRepo orderlogRepo;

    public final static int DRAFT = 0;
    public final static int PAYMENT = 10;
    public final static int PACKING = 20;
    public final static int SHIPPING = 30;
    public final static int COMPLETED = 40;
    public final static int CANCELLED = 90;

    public void createLog(String username, CustomerOrder customerOrder, int type, String message){
        OrderLog orderLog = new OrderLog();
        orderLog.setId(UUID.randomUUID().toString());
        orderLog.setLogMessege(message);
        orderLog.setLogType(type);
        orderLog.setOrder(customerOrder);
        orderLog.setUser(new User(username));
        orderLog.setTime(new Date());
        orderlogRepo.save(orderLog);
    }

    // write log
    
}
