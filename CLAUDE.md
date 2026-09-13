# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

# Project Overview

BattleForge - Turn-based RPG full stack game platform.
Evolution plan: full stack → Docker → Kubernetes → observability → analytics -> simulation

# Tech Stack

- Frontend: Angular 21.2.13, TypeScript, RxJS, Vitest
- Backend: Java 21, Spring Boot 4.0.6, Spring Data JPA, Lombok
- DB: PostgreSQL (localhost:5432/battleforge, user: postgres, pass: 123123123)
- Infra: Docker, Kubernetes, GCP (not yet implemented)

# Commands

### Backend (`backend/battleforge-backend/`)
```bash
./mvnw spring-boot:run        # Start dev server on :8080
./mvnw test                   # Run all tests
./mvnw test -Dtest=ClassName  # Run a single test class
./mvnw clean install          # Full build
```

### Frontend (`frontend/battleforge-frontend/`)
```bash
npm start                     # Dev server on :4200
npm test                      # Run tests with Vitest
npm run build                 # Production build
```

# Architecture

### Backend Layer Order

```
controller → service → repository → entity ← dto
```

- **entity**: JPA-mapped DB table (`@Entity`)
- **dto**: Data transfer object for request/response — never expose entities directly over HTTP
- **repository**: `JpaRepository` interface, no business logic
- **service**: All business logic lives here; annotated `@Service`, calls repository
- **controller**: `@RestController`, thin — delegates immediately to service, maps DTOs


### Frontend

Angular standalone components (no `NgModules`). State is managed via RxJS observables in injectable services. Components communicate with the backend via typed HTTP services using `HttpClient`. Routes are defined in `app.routes.ts`.

# Database

`spring.jpa.hibernate.ddl-auto=create-drop` — schema is **dropped and recreated** on every backend restart. This is intentional for early development. Change to `validate` or `update` before adding Docker/prod config.

# Claude Workflow

Before implementing any feature:
1. Explain the plan and which files will be affected
2. Wait for confirmation
3. Then implement

For architectural changes: always ask first, never assume.
