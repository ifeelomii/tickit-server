package com.example.demo.requests.vms;

import jakarta.validation.constraints.NotBlank;

public class CreateOrderRequestVM {
	@NotBlank(message = "Customer Id cannot be empty")
	private Long customerId;
	
	@NotBlank(message = "Pickup address cannot be empty")
    private String pickupAddress;
	
	@NotBlank(message = "Delivery address cannot be empty")
    private String deliveryAddress;
	
    private Double packageWeight;
    
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

}
