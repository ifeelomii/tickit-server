package com.example.tickit.domains;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.example.tickit.auditing.Auditable;
import com.example.tickit.auditing.AuditableField;
import com.example.tickit.auditing.SnapshotAware;
import com.example.tickit.enums.IssuePriorities;
import com.example.tickit.enums.IssueTypes;
import com.example.tickit.event.listeners.AuditListener;
import com.example.tickit.utils.AuditDiffEngine;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "issues")
@EntityListeners(AuditListener.class)
public class Issue implements Auditable, SnapshotAware {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "public_id", nullable = false, unique = true, updatable = false)
	private UUID publicId = UUID.randomUUID();

	@Column(length = 500)
	@AuditableField(name = "Issue Title")
	private String title;

	@Column(length = 4000)
	@AuditableField(name = "Issue Description")
	private String description;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "deleted", nullable = false)
	private boolean deleted = false;

//	@AuditableField(name = "Issue Type")
	@Enumerated(EnumType.STRING)
	private IssueTypes issueType;

//	@AuditableField(name = "Issue Priority")
	@Enumerated(EnumType.STRING)
	private IssuePriorities priority;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

//	@AuditableField(name = "Sprint")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sprint_id")
	private Sprint sprint;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reporter_id", updatable = false)
	private User reporter;

//	@AuditableField(name = "Issue Assignee")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assignee_id")
	private User assignee;

	@AuditableField(name = "Issue Status")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;

//	@AuditableField(name = "Collaborators")
	@ManyToMany
	@JoinTable(name = "issue_collaborators", joinColumns = @JoinColumn(name = "issue_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> collaborators = new HashSet<>();

//	@AuditableField(name = "Watchers")
	@ManyToMany
	@JoinTable(name = "issue_watchers", joinColumns = @JoinColumn(name = "issue_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> watchers = new HashSet<>();

	@Transient
	private Issue oldIssue;

	@Transient
	private List<AuditLog> auditLogs = new ArrayList<>();

	public Issue() {
	};

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getPublicId() {
		return publicId;
	}

	public void setPublicId(UUID publicId) {
		this.publicId = publicId;
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

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public IssueTypes getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueTypes issueType) {
		this.issueType = issueType;
	}

	public IssuePriorities getPriority() {
		return priority;
	}

	public void setPriority(IssuePriorities priority) {
		this.priority = priority;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public User getReporter() {
		return reporter;
	}

	public void setReporter(User reporter) {
		this.reporter = reporter;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<User> getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(Set<User> collaborators) {
		this.collaborators = collaborators;
	}

	public Set<User> getWatchers() {
		return watchers;
	}

	public void setWatchers(Set<User> watchers) {
		this.watchers = watchers;
	}

	@Override
	public void captureSnapshot() {
		Issue snapshot = new Issue();
		BeanUtils.copyProperties(this, snapshot);
		this.oldIssue = snapshot;
	}

	@Override
	public void prepareAuditLogs() {

		if (oldIssue == null) {
			this.auditLogs = AuditDiffEngine.generateLogsForCreate(this, "Issue", getEntityId());
		} else {
			this.auditLogs = AuditDiffEngine.generateLogs(oldIssue, this, "Issue", getEntityId());
		}
		System.out.println("Audit logs in prepareAuditLogs(): " + auditLogs.size());
	}

	@Override
	public String getEntityId() {
		return String.valueOf(id);
	}

	@Override
	public List<AuditLog> getAuditLogs() {
		return auditLogs;
	}

}