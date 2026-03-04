package com.example.demo.domains;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.enums.OrderStatus;
import com.example.demo.errors.InvalidOrderStateException;
import com.example.demo.response.vms.OrderResponseVM;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private String pickupAddress;

    private String deliveryAddress;

    private Double packageWeight;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long driverId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getPickupAddress() {
		return pickupAddress;
	}

	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Double getPackageWeight() {
		return packageWeight;
	}

	public void setPackageWeight(Double packageWeight) {
		this.packageWeight = packageWeight;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public OrderResponseVM toOrderResponseVM() {
		OrderResponseVM response = new OrderResponseVM();
		response.setId(this.getId());
		response.setCreatedAt(this.getCreatedAt());
		response.setCustomerId(this.getCustomerId());
		response.setDeliveryAddress(this.getDeliveryAddress());
		response.setDriverId(this.getDriverId());
		response.setPackageWeight(this.getPackageWeight());
		response.setPickupAddress(this.getPickupAddress());
		response.setStatus(this.getStatus());
		response.setUpdatedAt(this.getUpdatedAt());
		return response;
	}

	public List<OrderResponseVM> toOrderResponseVMList(List<Order> list) {
		List<OrderResponseVM> responseList = new ArrayList<>();
		
		for(Order order : list) {
			OrderResponseVM response = new OrderResponseVM();
			response.setId(order.getId());
			response.setCreatedAt(order.getCreatedAt());
			response.setCustomerId(order.getCustomerId());
			response.setDeliveryAddress(order.getDeliveryAddress());
			response.setDriverId(order.getDriverId());
			response.setPackageWeight(order.getPackageWeight());
			response.setPickupAddress(order.getPickupAddress());
			response.setStatus(order.getStatus());
			response.setUpdatedAt(order.getUpdatedAt());
			responseList.add(response);
		}
		return responseList;
	}
	
	public void transitionTo(OrderStatus newStatus) {

	    if (!this.status.canTransitionTo(newStatus)) {
	        throw new InvalidOrderStateException("Invalid transition");
	    }

	    this.status = newStatus;
	}
    
}
