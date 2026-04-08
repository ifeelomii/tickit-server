package com.example.tickit.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tickit.dto.IssueFilters;
import com.example.tickit.services.IssueService;
import com.example.tickit.vms.request.IssueRequestVM;
import com.example.tickit.vms.response.IssueResponseVM;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class IssueResource {
	@Autowired
	private IssueService issueService;

	@PostMapping("/create-issue")
	private ResponseEntity<IssueResponseVM> createIssue(@Valid @RequestBody IssueRequestVM issueRequestVM) {
		IssueResponseVM response = issueService.createNewIssue(issueRequestVM);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/update-issue/{id}")
	private ResponseEntity<IssueResponseVM> updateIssue(@PathVariable Long id,
			@Valid @RequestBody IssueRequestVM issueRequestVM) {
		IssueResponseVM response = issueService.updateIssue(id, issueRequestVM);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/get-issues")
	private ResponseEntity<List<IssueResponseVM>> getAllIssue(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, IssueFilters issueFilters) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
		List<IssueResponseVM> response = issueService.getAllIssues(issueFilters, pageable);
		return ResponseEntity.ok(response);
	}
}
