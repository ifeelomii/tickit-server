package com.example.demo.requests.vms;

import jakarta.validation.constraints.NotBlank;

public class AssignDriverRequestVM {
	@NotBlank
	private Long driverId;

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }
}
