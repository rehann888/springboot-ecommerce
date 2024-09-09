package com.project.miniecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.project.miniecommerce.entity.CustomerOrder;
import com.project.miniecommerce.entity.OrderItem;

import lombok.Data;

@Data
public class OrderResponse implements Serializable {
    
    private String id;
    private String orderNumber;
    private Date date;
    private String customerName;
    private String shippingAddress;
    private Date orderTime;
    private BigDecimal quantity;
    private BigDecimal shippingCost;
    private BigDecimal total;  
    private List<OrderResponse.Item> items;

    public OrderResponse(CustomerOrder customerOrder, List<OrderItem> orderItems){
        this.id = customerOrder.getId();
        this.orderNumber = customerOrder.getNumber();
        this.date = customerOrder.getDate();
        this.customerName = customerOrder.getUser().getName();
        this.shippingAddress = customerOrder.getShippingAddress();
        this.orderTime = customerOrder.getOrderDate();
        this.quantity = customerOrder.getAmount();
        this.shippingCost = customerOrder.getShippingCost();
        this.total = customerOrder.getTotal();
        items = new ArrayList<>();
        for(OrderItem orderItem : orderItems){
            Item item = new Item();
            item.setProductId(orderItem.getProduct().getId());
            item.setProductName(orderItem.getDescription());
            item.setQuantity(orderItem.getQuantity());
            item.setPrice(orderItem.getPrice());
            item.setTotal(orderItem.getTotal());
            items.add(item);
        }
    }

    @Data
    public static class Item implements Serializable{
        private String productId;
        private String productName;
        private Double quantity;
        private BigDecimal price;
        private BigDecimal total;
    }
}
