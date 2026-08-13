# 💰 Finance API

A backend microservice responsible for core financial operations, transaction management, and reporting within the microservices ecosystem.

---

## 🛠️ Tech Stack & Dependencies

* **Language/Framework:** Java 25+ / Spring Boot
* **Build Tool:** Maven
* **Database:** PostgreSQL
* **Containerization:** Docker

---

## 🏛️ Ecosystem Integration

This service is designed to run independently or as a submodule within the central [Infrastructure Repository](https://github.com/your-username/repo-infra).

When orchestrated via Docker Compose, it communicates with other microservices and shared resources through the internal network.

---

## ⚙️ Environment Variables

The application can be configured using the following environment variables (or via an `.env` file when running through Docker):

| Variable | Description | Default Value |
| :--- | :--- | :--- |
| `POSTGRES_HOST` | Database host address | `localhost` |
| `POSTGRES_PORT` | Database port | `5432` |
| `POSTGRES_DB` | Database name | `finance_db` |
| `POSTGRES_USER` | Database connection user | `postgres` |
| `POSTGRES_PASSWORD` | Database connection password | `postgres` |

---

## 🚀 Getting Started

... Work in progress ...