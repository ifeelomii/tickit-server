package com.example.tickit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tickit.domains.Issue;
import com.example.tickit.domains.Status;
import com.example.tickit.events.AuditEvent;
import com.example.tickit.repositories.IssueRepository;
import com.example.tickit.repositories.StatusRepository;
import com.example.tickit.vms.request.IssueRequestVM;
import com.example.tickit.vms.response.IssueResponseVM;

@Service
public class IssueService {

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
				.orElseThrow(() -> new RuntimeException("Status not found"));

		Issue issue = new Issue();
		issue.setTitle(request.getTitle());
		issue.setDescription(request.getDescription());
		issue.setStatus(status);

		Issue saved = save(issue);
		return mapToResponse(saved);
	}

	@Transactional
	public IssueResponseVM updateIssue(Long issueId, IssueRequestVM request) {

		Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));
		issue.setTitle(request.getTitle());
		issue.setDescription(request.getDescription());

		Issue savedIssue = issueRepository.saveAndFlush(issue);
//		publishEvent(savedIssue);
		return mapToResponse(savedIssue);
	}

	private IssueResponseVM mapToResponse(Issue issue) {
		return new IssueResponseVM(issue.getId(), issue.getTitle(), issue.getDescription(), issue.getStatus().getName(),
				issue.getAssignee() != null ? issue.getAssignee().getId() : null,
				issue.getSprint() != null ? issue.getSprint().getId() : null);
	}

}
