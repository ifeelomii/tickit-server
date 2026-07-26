package com.example.tickit.vms.response;

import java.util.List;
import java.util.stream.Collectors;

import com.example.tickit.domains.Issue;
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

	public IssueResponseVM() {
		super();
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

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getStatus() {
		return status;
	}

	public Long getAssigneeId() {
		return assigneeId;
	}

	public Long getSprintId() {
		return sprintId;
	}

	public IssueResponseVM mapToResponse(Issue issue) {

		IssueResponseVM response = new IssueResponseVM();

		response.setId(issue.getId());
		response.setTitle(issue.getTitle());
		response.setDescription(issue.getDescription());

		if (issue.getStatus() != null) {
			response.setStatus(issue.getStatus().getName());
		}

		if (issue.getAssignee() != null) {
			response.setAssigneeId(issue.getAssignee().getId());
		}

		if (issue.getSprint() != null) {
			response.setSprintId(issue.getSprint().getId());
		}

		return response;
	}

	public List<IssueResponseVM> mapListToResponseList(List<Issue> issues) {
		return issues.stream().map(this::mapToResponse).collect(Collectors.toList());
	}

}
