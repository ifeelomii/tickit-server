# Mini Jira Backend (Modular Monolith with Kafka)

## Overview

This project is a **Mini Jira-like Issue Tracking System** built using **Spring Boot**. It is designed with a forward-looking architecture that evolves from a clean monolith into a **modular monolith**, with planned integrations for **Kafka, GraphQL, and Elasticsearch**.

The system supports core issue tracking capabilities such as issue creation, updates, filtering, lifecycle management, and audit logging.

---

## Key Features

* Issue Management (Create, Update, Fetch, Filter)
* Pagination & Sorting support
* Issue lifecycle handling
* Audit Logging
* Extensible filter-based querying
* Modular architecture (in progress)
* Kafka integration (planned/partial)

---

## Tech Stack

* **Backend:** Spring Boot
* **Language:** Java
* **Database:** (Assumed) MySQL / PostgreSQL
* **Messaging (Planned):** Apache Kafka
* **Future Enhancements:**

  * GraphQL API layer
  * Elasticsearch for advanced search

---

## Project Structure (Target Modular Monolith)

```
com.project
│
├── issue
│   ├── controller
│   ├── service
│   ├── repository
│   ├── domain
│   └── dto
│
├── audit
│   ├── service
│   └── domain
│
├── common
│   ├── config
│   ├── exception
│   └── util
│
├── kafka (planned)
│   ├── producer
│   ├── consumer
│   └── config
│
└── application
```

---

## API Example

### Get Issues with Filters

```http
GET /get-issues?page=0&size=10
```

### Controller Snippet

```java
@GetMapping("/get-issues")
private ResponseEntity<List<IssueResponseVM>> getAllIssue(
        @RequestParam(value = "page", required = true, defaultValue = "0") int page,
        @RequestParam(value = "size", required = true, defaultValue = "10") int size,
        IssueFilters issueFilters) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
    return ResponseEntity.ok(issueService.getAllIssues(issueFilters, pageable));
}
```

---

## Kafka Integration Plan

Kafka is **not used for logging**, but for domain-driven async workflows such as:

* Issue Created Event
* Issue Status Changed Event
* Audit Trail Processing
* Future: Notifications / Automation

### Goals

* Decouple modules
* Enable event-driven communication
* Introduce resilience with retries/fallbacks

---

## Future Enhancements

### 1. Modular Monolith

* Clear module boundaries
* Internal communication via events (Kafka)
* Independent module evolution

### 2. GraphQL

* Flexible querying for UI
* Reduce over-fetching

### 3. Elasticsearch

* Advanced filtering
* Full-text search
* Faster querying at scale

---

## Design Considerations

* Avoid premature microservices
* Maintain strong domain boundaries
* Keep modules loosely coupled
* Prefer event-driven patterns for scalability

---

## How to Run

```bash
# Clone repository
git clone <repo-url>

# Navigate to project
cd mini-jira

# Run application
./mvnw spring-boot:run
```

---

## Development Guidelines

* Follow layered architecture (Controller → Service → Repository)
* Use DTOs for API contracts
* Keep domain models clean
* Avoid circular dependencies between modules
* Write meaningful logs and audit trails

---

## Contribution

This project is evolving toward a production-grade architecture. Contributions and architectural improvements are welcome.

---

## Notes

* Kafka setup can initially be local (Docker)
* Start simple, then modularize
* Focus on correctness before optimization

---

## Author

Omkar Ware

---

## License

This project is for learning and development purposes.
