package com.project.miniecommerce.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.miniecommerce.entity.CustomerOrder;
import com.project.miniecommerce.entity.OrderItem;
import com.project.miniecommerce.entity.Product;
import com.project.miniecommerce.entity.User;
import com.project.miniecommerce.exception.ResourceNotFoundException;
import com.project.miniecommerce.model.CartRequest;
import com.project.miniecommerce.model.OrderRequest;
import com.project.miniecommerce.model.OrderResponse;
import com.project.miniecommerce.model.OrderStatus;
import com.project.miniecommerce.repository.OrderRepo;
import com.project.miniecommerce.repository.OrderitemRepo;
import com.project.miniecommerce.repository.ProductRepo;

@Service
public class CustomerOrderService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderitemRepo orderitemRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderLogService orderLogService;

    @Transactional
    public OrderResponse create(String username, OrderRequest request) throws BadRequestException{

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(UUID.randomUUID().toString());
        customerOrder.setDate(new Date());
        customerOrder.setNumber(generateOrderNumber());
        customerOrder.setUser(new User(username));
        customerOrder.setShippingAddress(request.getShippingAddress());
        customerOrder.setOrderStatus(OrderStatus.DRAFT);
        customerOrder.setDate(new Date());

        List<OrderItem> items = new ArrayList<>();
        for(CartRequest cartRequest : request.getItems()){
            Product product = productRepo.findById(cartRequest.getProductId())
            .orElseThrow(()-> new BadRequestException("Product ID " + cartRequest.getProductId() + " Not Found"));
            if(product.getStock()<cartRequest.getQuantity()){
                throw new BadRequestException("insufficient stock");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setId(UUID.randomUUID().toString());
            orderItem.setProduct(product);
            orderItem.setDescription(product.getName());
            orderItem.setQuantity(cartRequest.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setTotal(new BigDecimal(orderItem.getPrice().doubleValue()*orderItem.getQuantity()));
            orderItem.setOrder(customerOrder);
            items.add(orderItem);
        }
        BigDecimal transactionAmount = BigDecimal.ZERO;
        for(OrderItem orderItem : items){
            transactionAmount = transactionAmount.add(orderItem.getTotal());
        }

        customerOrder.setAmount(transactionAmount);;
        customerOrder.setShippingCost(request.getShippingCost());
        customerOrder.setTotal(customerOrder.getAmount().add(customerOrder.getShippingCost()));

        CustomerOrder saved = orderRepo.save(customerOrder);
        for(OrderItem orderItem : items){
            orderitemRepo.save(orderItem);
            Product product = orderItem.getProduct();
            product.setStock(product.getStock()-orderItem.getQuantity());
            productRepo.save(product);
            cartService.delete(username, product.getId());
        }

        orderLogService.createLog(username, customerOrder, OrderLogService.DRAFT, "ORDER SUCCESSFULLY CREATED");
        OrderResponse orderResponse = new OrderResponse(saved, items);
        return orderResponse;
    }

    @Transactional
    public CustomerOrder cancelOrder(String orderId, String userId) throws BadRequestException{
        CustomerOrder customerOrder = orderRepo.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order ID " + orderId + " Not Found"));
        if (!userId.equals(customerOrder.getUser().getId())) {
            throw new BadRequestException("This order can only be cancelled by the person concerned");
        }

        if (!OrderStatus.DRAFT.equals(customerOrder.getOrderStatus())) {
            throw new BadRequestException("This order cannot be cancelled");
        }

        customerOrder.setOrderStatus(OrderStatus.CANCELLED);
        CustomerOrder saved = orderRepo.save(customerOrder);
        orderLogService.createLog(userId, customerOrder, OrderLogService.CANCELLED, "Order cancelled successfully");
        return saved;
    }

    @Transactional
    public CustomerOrder acceptOrder(String orderId, String userId) throws BadRequestException{
        CustomerOrder customerOrder = orderRepo.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order ID " + orderId + " Not Found"));
        if (!userId.equals(customerOrder.getUser().getId())) {
            throw new BadRequestException("This order can only be cancelled by the person concerned");
        }

        if (!OrderStatus.SHIPPING.equals(customerOrder.getOrderStatus())) {
            throw new BadRequestException("Failed Acceptance, order status is" + customerOrder.getOrderStatus().name());
        }

        customerOrder.setOrderStatus(OrderStatus.CANCELLED);
        CustomerOrder saved = orderRepo.save(customerOrder);
        orderLogService.createLog(userId, customerOrder, OrderLogService.CANCELLED, "Order cancelled successfully");
        return saved;
    }

    @Transactional
    public CustomerOrder acceptPayment(String orderId, String userId) throws BadRequestException{
        CustomerOrder customerOrder = orderRepo.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order ID " + orderId + " Not Found"));
        if (!OrderStatus.DRAFT.equals(customerOrder.getOrderStatus())) {
            throw new BadRequestException("Payment Confirmation is Failed, order status is" + customerOrder.getOrderStatus().name());
        }

        customerOrder.setOrderStatus(OrderStatus.PAYMENT);
        CustomerOrder saved = orderRepo.save(customerOrder);
        orderLogService.createLog(userId, customerOrder, OrderLogService.PAYMENT, "Payment confirmation successfully");
        return saved;
    }

    @Transactional
    public CustomerOrder packing(String orderId, String userId) throws BadRequestException{
        CustomerOrder customerOrder = orderRepo.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order ID " + orderId + " Not Found"));
        if (!OrderStatus.PAYMENT.equals(customerOrder.getOrderStatus())) {
            throw new BadRequestException("Order Packing is Failed, order status is" + customerOrder.getOrderStatus().name());
        }

        customerOrder.setOrderStatus(OrderStatus.PACKING);
        CustomerOrder saved = orderRepo.save(customerOrder);
        orderLogService.createLog(userId, customerOrder, OrderLogService.PACKING, "ORDERS ARE BEING PREPARED");
        return saved;
    }

    @Transactional
    public CustomerOrder OnTheWay(String orderId, String userId) throws BadRequestException{
        CustomerOrder customerOrder = orderRepo.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order ID " + orderId + " Not Found"));
        if (!OrderStatus.PACKING.equals(customerOrder.getOrderStatus())) {
            throw new BadRequestException("Shipping Order is Failed, order status is" + customerOrder.getOrderStatus().name());
        }

        customerOrder.setOrderStatus(OrderStatus.SHIPPING);
        CustomerOrder saved = orderRepo.save(customerOrder);
        orderLogService.createLog(userId, customerOrder, OrderLogService.SHIPPING, "Your Order is On THe Way");
        return saved;
    }


    public List<CustomerOrder> findAllUserOrder(String userId, int page, int limit){
        return orderRepo.findByUserId(userId, 
        PageRequest.of(page, limit, Sort.by("orderDate").descending()));
    }

    public List<CustomerOrder> search(String filterText, int page, int limit){
        return orderRepo.search(filterText.toLowerCase(), 
        PageRequest.of(page, page, Sort.by("orderDate").descending()));
    }

    private String generateOrderNumber(){
        return String.format("%016d", System.nanoTime());
    }


    
}
