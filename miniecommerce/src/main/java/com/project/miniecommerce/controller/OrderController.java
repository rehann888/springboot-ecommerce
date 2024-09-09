package com.project.miniecommerce.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.project.miniecommerce.entity.CustomerOrder;
import com.project.miniecommerce.model.OrderRequest;
import com.project.miniecommerce.model.OrderResponse;
import com.project.miniecommerce.security.service.UserDetailImplementation;
import com.project.miniecommerce.service.CustomerOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class OrderController {
    
    @Autowired
    private CustomerOrderService customerOrderService;

    @PostMapping("/order")
    @PreAuthorize("hasAuthority('user')")
    public OrderResponse create(
            @AuthenticationPrincipal UserDetailImplementation user,
            @RequestBody OrderRequest request) throws BadRequestException{
            return customerOrderService.create(user.getUsername(), request);   
    }

    @PatchMapping("/order/{orderId}/cancel")
    @PreAuthorize("hasAuthority('user')")
    public CustomerOrder cancelOrder(
            @AuthenticationPrincipal UserDetailImplementation user,
            @PathVariable("orderId") String orderId) throws BadRequestException{
            return customerOrderService.cancelOrder(orderId, user.getUsername());
    }

    @PatchMapping("/order/{orderId}/accept")
    @PreAuthorize("hasAuthority('user')")
    public CustomerOrder accept(
            @AuthenticationPrincipal UserDetailImplementation user,
            @PathVariable("orderId") String orderId) throws BadRequestException{
            return customerOrderService.acceptOrder(orderId, user.getUsername());
    }

    @PatchMapping("/order/{orderId}/confirmation")
    @PreAuthorize("hasAuthority('admin')")
    public CustomerOrder confirmation(
            @AuthenticationPrincipal UserDetailImplementation user,
            @PathVariable("orderId") String orderId) throws BadRequestException{
            return customerOrderService.acceptPayment(orderId, user.getUsername());
    }

    @PatchMapping("/order/{orderId}/packing")
    @PreAuthorize("hasAuthority('admin')")
    public CustomerOrder packing(
            @AuthenticationPrincipal UserDetailImplementation user,
            @PathVariable("orderId") String orderId) throws BadRequestException{
            return customerOrderService.packing(orderId, user.getUsername());
    }

    @PatchMapping("/order/{orderId}/send")
    @PreAuthorize("hasAuthority('admin')")
    public CustomerOrder sendOrder(
            @AuthenticationPrincipal UserDetailImplementation user,
            @PathVariable("orderId") String orderId) throws BadRequestException{
            return customerOrderService.OnTheWay(orderId, user.getUsername());
    }

    @GetMapping("/order")
    @PreAuthorize("hasAuthority('user')")
    public List<CustomerOrder> findAllUserOrder(
        @AuthenticationPrincipal UserDetailImplementation user,
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "limit", defaultValue = "25", required = false) int limit)throws BadRequestException{
        return customerOrderService.findAllUserOrder(user.getUsername(), page, limit);
    }

    @GetMapping("/order/admin")
    @PreAuthorize("hasAuthority('admin')")
    public List<CustomerOrder> findAllUserOrder(
        @AuthenticationPrincipal UserDetailImplementation user,
        @RequestParam(name = "filterText", defaultValue = "0", required = false) String filterText,
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "limit", defaultValue = "25", required = false) int limit){
        return customerOrderService.search(filterText, page, limit);
    }


    

}
