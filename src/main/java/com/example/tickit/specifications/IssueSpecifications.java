package com.example.tickit.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.tickit.domains.Issue;
import com.example.tickit.dto.IssueFilters;

import jakarta.persistence.criteria.Predicate;

public class IssueSpecifications {
	public static Specification<Issue> getIssueSpecifications(IssueFilters filter) {
		return (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (filter.getTitle() != null) {
				predicates.add(cb.like(root.get("title"), "%" + filter.getTitle() + "%"));
			}

			if (filter.getStatusId() != null) {
				predicates.add(cb.equal(root.get("status").get("id"), filter.getStatusId()));
			}

			if (filter.getCategory() != null) {
				predicates.add(cb.equal(root.get("category"), filter.getCategory()));
			}

			if (filter.getPriority() != null) {
				predicates.add(cb.equal(root.get("priority"), filter.getPriority()));
			}

			if (filter.getAssigneeId() != null) {
				predicates.add(cb.equal(root.get("assignee").get("id"), filter.getAssigneeId()));
			}

			if (filter.getSprintId() != null) {
				predicates.add(cb.equal(root.get("sprint").get("id"), filter.getSprintId()));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
