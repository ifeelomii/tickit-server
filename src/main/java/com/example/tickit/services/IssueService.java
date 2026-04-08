package com.example.tickit.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tickit.domains.Issue;
import com.example.tickit.domains.Status;
import com.example.tickit.dto.IssueFilters;
import com.example.tickit.errors.IssueNotFoundException;
import com.example.tickit.errors.StatusNotFoundException;
import com.example.tickit.events.AuditEvent;
import com.example.tickit.repositories.IssueRepository;
import com.example.tickit.repositories.StatusRepository;
import com.example.tickit.specifications.IssueSpecifications;
import com.example.tickit.vms.request.IssueRequestVM;
import com.example.tickit.vms.response.IssueResponseVM;

@Service
public class IssueService {

	public static final String ENTITY_NAME = "Issue";

	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	@Autowired
	private StatusRepository statusRepository;

	public Issue save(Issue issue) {
		return issueRepository.save(issue);
	}

	private void publishEvent(Issue savedIssue) {
		System.out.println("Publishing Event 1");
		if (savedIssue.getAuditLogs().isEmpty())
			return;
		System.out.println("Publishing Event 2");
		eventPublisher.publishEvent(new AuditEvent("Issue", savedIssue.getId().toString(), savedIssue.getAuditLogs()));
	}

	@Transactional
	public IssueResponseVM createNewIssue(IssueRequestVM request) {

		Status status = statusRepository.findById(request.getStatusId())
				.orElseThrow(() -> new StatusNotFoundException("Status not found",
						String.format("Status with id %s does not exist", request.getStatusId().toString()),
						ENTITY_NAME, HttpStatus.BAD_REQUEST));

		Issue issue = new Issue();
		issue.setTitle(request.getTitle());
		issue.setDescription(request.getDescription());
		issue.setStatus(status);

		Issue saved = save(issue);
		return mapToResponse(saved);
	}

	@Transactional
	public IssueResponseVM updateIssue(Long issueId, IssueRequestVM request) {

		Issue issue = issueRepository.findById(issueId)
				.orElseThrow(() -> new IssueNotFoundException("Issue not found",
						String.format("Issue with id %s does not exist", issueId.toString()), ENTITY_NAME,
						HttpStatus.BAD_REQUEST));
		issue.setTitle(request.getTitle());
		issue.setDescription(request.getDescription());

		Issue savedIssue = issueRepository.saveAndFlush(issue);
		return mapToResponse(savedIssue);
	}

	public List<IssueResponseVM> getAllIssues(IssueFilters issueFilters, Pageable pageable) {
		Page<Issue> issueList = issueRepository.findAll(IssueSpecifications.getIssueSpecifications(issueFilters),
				pageable);
		return mapListToResponseList(issueList.getContent());
	}

	private IssueResponseVM mapToResponse(Issue issue) {

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

	private List<IssueResponseVM> mapListToResponseList(List<Issue> issues) {
		return issues.stream().map(this::mapToResponse).collect(Collectors.toList());
	}

}
