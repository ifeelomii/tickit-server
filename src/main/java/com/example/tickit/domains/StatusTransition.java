package com.example.tickit.domains;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "status_transitions")
public class StatusTransition {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "public_id", nullable = false, unique = true, updatable = false)
	private UUID publicId = UUID.randomUUID();

	@ManyToOne(fetch = FetchType.LAZY)
	private Workflow workflow;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_status_id")
	private Status fromStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_status_id")
	private Status toStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getPublicId() {
		return publicId;
	}

	public void setPublicId(UUID publicId) {
		this.publicId = publicId;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public Status getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(Status fromStatus) {
		this.fromStatus = fromStatus;
	}

	public Status getToStatus() {
		return toStatus;
	}

	public void setToStatus(Status toStatus) {
		this.toStatus = toStatus;
	}

}