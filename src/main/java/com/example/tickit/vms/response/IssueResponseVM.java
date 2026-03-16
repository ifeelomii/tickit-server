package com.example.tickit.vms.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class IssueResponseVM {
	private Long id;

	private String title;

	private String description;

	private String status;

	private Long assigneeId;

	private Long sprintId;

	public IssueResponseVM(Long id, String title, String description, String status, Long assigneeId, Long sprintId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.assigneeId = assigneeId;
		this.sprintId = sprintId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAssigneeId(Long assigneeId) {
		this.assigneeId = assigneeId;
	}

	public void setSprintId(Long sprintId) {
		this.sprintId = sprintId;
	}

}
