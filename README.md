# Week 4 — Multi-Service Stack with Docker Compose

## What this is
A multi-service cloud application orchestrated with Docker Compose,
demonstrating how real production systems wire multiple services together
and bring them up with a single command.

## Tech stack
- **Spring Boot** (Java) — REST API web application
- **PostgreSQL 15** — relational database storing student records
- **Redis** — in-memory cache storing page visit counter
- **Docker Compose** — orchestrates all 3 services together

## Architecture
┌─────────────────────────────────────────┐
│           Docker Network                │
│                                         │
│  ┌──────────────────┐                   │
│  │   Spring Boot    │──▶ PostgreSQL DB  │
│  │   Port 8080      │      (students)   │
│  │                  │──▶ Redis Cache    │
│  └──────────────────┘    (visit count)  │
└─────────────────────────────────────────┘

## Services
| Service | Image | Port | Role |
|---------|-------|------|------|
| web | studentapp-web (custom build) | 8080 | Spring Boot REST API |
| postgres | postgres:15-alpine | 5432 | Student data storage |
| redis | redis:alpine | 6379 | Visit counter cache |

## API Endpoints
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/` | GET | Home page with Redis visit counter |
| `/api/students` | GET | List all students from PostgreSQL |
| `/api/students` | POST | Add a new student |
| `/api/health` | GET | Health check for all 3 services |

## How to run

### Prerequisites
- Docker Desktop installed and running
- Java 17 + Maven (to rebuild if needed)

### 1. Clone the repo
```bash
git clone https://github.com/mnavaneeth44/week4-multiservice.git
cd week4-multiservice
```

### 2. Start all services with one command
```bash
docker compose up
```

### 3. Open in browser
http://localhost:8080/api/

### 4. Test health check
http://localhost:8080/api/health

### 5. Stop all services
```bash
docker compose down
```

## Key concepts demonstrated
- **Service orchestration** — 3 containers managed as one system
- **Shared Docker network** — services communicate using service names
  (e.g. Spring Boot connects to `postgres:5432` not an IP address)
- **depends_on** — Redis and PostgreSQL start before Spring Boot
- **Persistent volumes** — PostgreSQL data survives container restarts
- **Environment variables** — database credentials passed via
  docker-compose.yml, not hardcoded in the app
- **Health separation** — each service runs in its own isolated container

## What I learned
- How to wire multiple Docker containers together using Compose
- How Spring Boot connects to PostgreSQL and Redis inside a Docker network
- Why services need to be on the same network to talk to each other
- How Redis speeds up repeated data access (visit counter vs DB query)
- How `depends_on` controls service startup order

## Screenshots
| File | Description |
|------|-------------|
| `01-services-startup.png` | Terminal showing all 3 services starting |
| `02-docker-desktop.png` | Docker Desktop showing all containers running |
| `03-app-homepage.png` | Student Registry app with Redis counter |
| `04-health-check.png` | Health check confirming all services connected |
