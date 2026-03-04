package com.example.demo.domains;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long driverId;

    private Double distanceKm;

    private Integer estimatedTimeMinutes;

    private LocalDateTime routeStartTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public Double getDistanceKm() {
		return distanceKm;
	}

	public void setDistanceKm(Double distanceKm) {
		this.distanceKm = distanceKm;
	}

	public Integer getEstimatedTimeMinutes() {
		return estimatedTimeMinutes;
	}

	public void setEstimatedTimeMinutes(Integer estimatedTimeMinutes) {
		this.estimatedTimeMinutes = estimatedTimeMinutes;
	}

	public LocalDateTime getRouteStartTime() {
		return routeStartTime;
	}

	public void setRouteStartTime(LocalDateTime routeStartTime) {
		this.routeStartTime = routeStartTime;
	}
}