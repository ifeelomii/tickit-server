package com.example.tickit.auditing;

public interface SnapshotAware {
	void captureSnapshot();

	void prepareAuditLogs();
}
