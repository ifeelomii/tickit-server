package com.example.tickit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tickit.domains.Issue;
import com.example.tickit.domains.Status;
import com.example.tickit.domains.User;
import com.example.tickit.errors.IssueNotFoundException;
import com.example.tickit.errors.StatusNotFoundException;
import com.example.tickit.events.AuditEvent;
import com.example.tickit.filters.IssueFilters;
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
	@Autowired
	private UserService userService;

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
	private Issue findOne(Long issueId) {
		Issue issue = issueRepository.findById(issueId)
				.orElseThrow(() -> new IssueNotFoundException("Issue not found",
						String.format("Issue with id %s does not exist", issueId.toString()), ENTITY_NAME,
						HttpStatus.BAD_REQUEST));
		return issue;
	}

	@Transactional
	public IssueResponseVM createNewIssue(IssueRequestVM request) {

		Status status = statusRepository.findById(request.getStatusId())
				.orElseThrow(() -> new StatusNotFoundException("Status not found",
						String.format("Status with id %s does not exist", request.getStatusId().toString()),
						ENTITY_NAME, HttpStatus.BAD_REQUEST));

		User reporter = userService.findReporter(request.getReporterId());
		User assignee = userService.findAssignee(request.getAssigneeId());

		Issue issue = new Issue();
		issue.setTitle(request.getTitle());
		issue.setDescription(request.getDescription());
		issue.setStatus(status);
		issue.setReporter(reporter);
		issue.setAssignee(assignee);
		Issue saved = save(issue);
		return new IssueResponseVM().mapToResponse(saved);
	}

	@Transactional
	public IssueResponseVM updateIssue(Long issueId, IssueRequestVM request) {

		Issue issue = findOne(issueId);
		issue.setTitle(request.getTitle());
		issue.setDescription(request.getDescription());

		Issue savedIssue = issueRepository.saveAndFlush(issue);
		return new IssueResponseVM().mapToResponse(savedIssue);
	}

	public List<IssueResponseVM> getAllIssues(IssueFilters issueFilters, Pageable pageable) {
		Page<Issue> issueList = findFromSpecifications(issueFilters, pageable);
		return new IssueResponseVM().mapListToResponseList(issueList.getContent());
	}

	private Page<Issue> findFromSpecifications(IssueFilters issueFilters, Pageable pageable) {
		return issueRepository.findAll(IssueSpecifications.getIssueSpecifications(issueFilters), pageable);
	}

}
