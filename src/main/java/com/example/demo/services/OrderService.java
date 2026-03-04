package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domains.Order;
import com.example.demo.domains.OrderEvent;
import com.example.demo.enums.OrderStatus;
import com.example.demo.errors.EntityNotFoundException;
import com.example.demo.errors.InvalidOrderStateException;
import com.example.demo.repositories.OrderEventRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.requests.vms.CreateOrderRequestVM;
import com.example.demo.response.vms.OrderResponseVM;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventRepository orderEventRepository;
    
    private OrderService self;

    public OrderService(OrderRepository orderRepository, OrderEventRepository orderEventRepository) {
        this.orderRepository = orderRepository;
        this.orderEventRepository = orderEventRepository;
    }
    
    public List<OrderResponseVM> getAllOrder(Pageable pageable) {
    	Page<Order> page =  orderRepository.findAll(pageable);
    	
    	List<Order> list = page.getContent();
    	return new Order().toOrderResponseVMList(list);
	}

    @Transactional
    public OrderResponseVM createOrder(CreateOrderRequestVM request) {

        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setPickupAddress(request.getPickupAddress());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setPackageWeight(request.getPackageWeight());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order).toOrderResponseVM();
    }
    
    public OrderResponseVM getOrder(Long id) {
        return self.findOne(id).toOrderResponseVM();
    }
    
    @Transactional
    public OrderResponseVM assignDriver(Long orderId, Long driverId) {

        Order order = self.findOne(orderId);

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new InvalidOrderStateException("Driver can only be assigned to CREATED orders");
        }

        order.setDriverId(driverId);
        order.setStatus(OrderStatus.DRIVER_ASSIGNED);

        return orderRepository.save(order).toOrderResponseVM();
    }

	private Order findOne(Long orderId) {
		return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
	}
	
	@Transactional
	public OrderResponseVM updateStatus(Long orderId, String newStatus) {
		OrderStatus status;
		try {
			status = OrderStatus.valueOf(newStatus);
		}catch (IllegalArgumentException ex) {
			throw new EntityNotFoundException("Invalid Status");
		}
		
	    Order order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new EntityNotFoundException("Order not found"));

	    order.transitionTo(status);
	    
	    OrderEvent event = new OrderEvent();
	    event.setOrderId(order.getId());
	    event.setStatus(status);
	    event.setEventTime(LocalDateTime.now());

	    orderEventRepository.save(event);

	    return orderRepository.save(order).toOrderResponseVM();
	}
}