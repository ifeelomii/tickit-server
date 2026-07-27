package com.example.tickit.specifications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.example.tickit.domains.User;
import com.example.tickit.filters.UserFilters;

import jakarta.persistence.criteria.Predicate;

public class UserSpecifications {
	public static Specification<User> getUserSpecifications(UserFilters filter) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (filter.getId() != null) {
				predicates.add(cb.equal(root.get("id"), filter.getId()));
			}

			if (filter.getPublicId() != null) {
				predicates.add(cb.equal(root.get("publicId"), filter.getPublicId()));
			}

			if (filter.getEmail() != null) {
				predicates.add(cb.equal(root.get("email"), filter.getEmail()));
			}

			if (filter.getFirstName() != null && filter.getLastName() != null) {
				predicates.add(cb.or(
						cb.like(cb.lower(root.get("firstName")), "%" + filter.getFirstName().toLowerCase() + "%"),
						cb.like(cb.lower(root.get("lastName")), "%" + filter.getLastName().toLowerCase() + "%")));
			} else if (filter.getFirstName() != null) {
				predicates
						.add(cb.like(cb.lower(root.get("firstName")), "%" + filter.getFirstName().toLowerCase() + "%"));
			} else if (filter.getLastName() != null) {
				predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + filter.getLastName().toLowerCase() + "%"));
			}

			if (filter.getUserName() != null) {
				predicates.add(cb.like(cb.lower(root.get("userName")), "%" + filter.getUserName().toLowerCase() + "%"));
			}

			if (filter.getStatus() != null && !filter.getStatus().isBlank()) {

				List<String> statuses = Arrays.stream(filter.getStatus().split(",")).map(String::trim)
						.filter(s -> !s.isEmpty()).collect(Collectors.toList());

				predicates.add(root.get("status").in(statuses));
			}

			if (filter.getUserRoles() != null && !filter.getUserRoles().isBlank()) {

				List<String> roles = Arrays.stream(filter.getUserRoles().split(",")).map(String::trim)
						.filter(s -> !s.isEmpty()).collect(Collectors.toList());

				predicates.add(root.get("userRoles").in(roles));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
