package com.example.demo.resources;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.requests.vms.AssignDriverRequestVM;
import com.example.demo.requests.vms.CreateOrderRequestVM;
import com.example.demo.response.vms.OrderResponseVM;
import com.example.demo.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping
    public List<OrderResponseVM> getAllOrder(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
    	Pageable pageable = PageRequest.of(page, size);
        return orderService.getAllOrder(pageable);
    }

    @PostMapping
    public OrderResponseVM createOrder(@Valid @RequestBody CreateOrderRequestVM request) {
        return orderService.createOrder(request);
    }
    
    @GetMapping("/{id}")
    public OrderResponseVM getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }
    
    @PostMapping("/{id}/assign-driver")
    public OrderResponseVM assignDriver(@PathVariable Long id,
                             @Valid @RequestBody AssignDriverRequestVM request) {
        return orderService.assignDriver(id, request.getDriverId());
    }
    
    @PutMapping("/{id}/status/{status}")
    public OrderResponseVM updateOrderStatus(@PathVariable Long id, @PathVariable String status) {
    	return orderService.updateStatus(id, status);
    }
}