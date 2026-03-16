package com.example.tickit.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tickit.services.IssueService;
import com.example.tickit.vms.request.IssueRequestVM;
import com.example.tickit.vms.response.IssueResponseVM;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class IssueController {
	@Autowired
	private IssueService issueService;

	@PostMapping("/create-issue")
	private ResponseEntity<IssueResponseVM> createIssue(@Valid @RequestBody IssueRequestVM issueRequestVM) {
		IssueResponseVM response = issueService.createNewIssue(issueRequestVM);
		return ResponseEntity.ok().body(response);
	}

	@PatchMapping("/update-issue/{id}")
	private ResponseEntity<IssueResponseVM> updateIssue(@PathVariable Long id,
			@Valid @RequestBody IssueRequestVM issueRequestVM) {
		IssueResponseVM response = issueService.updateIssue(id, issueRequestVM);
		return ResponseEntity.ok().body(response);
	}
}
