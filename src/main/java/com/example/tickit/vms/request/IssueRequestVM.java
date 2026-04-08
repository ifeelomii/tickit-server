package com.example.tickit.vms.request;

import jakarta.validation.constraints.NotNull;

public class IssueRequestVM {

	private Long issueId;

	@NotNull
	private String title;

	private String description;

	@NotNull
	private Long statusId;

	@NotNull
	private Long assigneeId;

	@NotNull
	private Long sprintId;

	public IssueRequestVM() {
		super();
	}

	public IssueRequestVM(@NotNull String title, String description, @NotNull Long statusId, @NotNull Long assigneeId,
			@NotNull Long sprintId) {
		super();
		this.title = title;
		this.description = description;
		this.statusId = statusId;
		this.assigneeId = assigneeId;
		this.sprintId = sprintId;
	}

	public Long getIssueId() {
		return issueId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Long getStatusId() {
		return statusId;
	}

	public Long getAssigneeId() {
		return assigneeId;
	}

	public Long getSprintId() {
		return sprintId;
	}

}