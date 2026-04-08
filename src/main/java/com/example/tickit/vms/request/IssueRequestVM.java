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
	private Long reporterId;

	@NotNull
	private Long sprintId;

	public IssueRequestVM() {
		super();
	}

	public IssueRequestVM(Long issueId, @NotNull String title, String description, @NotNull Long statusId,
			@NotNull Long assigneeId, @NotNull Long reporterId, @NotNull Long sprintId) {
		super();
		this.issueId = issueId;
		this.title = title;
		this.description = description;
		this.statusId = statusId;
		this.assigneeId = assigneeId;
		this.reporterId = reporterId;
		this.sprintId = sprintId;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public Long getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Long assigneeId) {
		this.assigneeId = assigneeId;
	}

	public Long getReporterId() {
		return reporterId;
	}

	public void setReporterId(Long reporterId) {
		this.reporterId = reporterId;
	}

	public Long getSprintId() {
		return sprintId;
	}

	public void setSprintId(Long sprintId) {
		this.sprintId = sprintId;
	}

}