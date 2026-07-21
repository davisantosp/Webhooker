# Webhooker

A service for setting rules for reception and sending requests using asynchronous webhooks made towards studying microservices systems using **Java**, **Spring**, **Kafka** and **PostgreSQL**.

## Overview

The webhooker allows **asynchronous** processing of trigger events from you application using **Apache Kafka**, it can send requests to other **services/APIs** at the time of an event occur.


## Tech Stack

* **Java 21** e **Spring Boot 4** (Main framework)
* **Apache Kafka** (Message broker for asynchronous processing)
* **PostgreSQL** (Relational Database for persistence)
* **Flyway** (DB version control using migrations)
* **Docker & Docker Compose** (Infrastructure to run anywhere)
* **JUnit 5 & Mockito** (Tests for developed features)
* **GitHub Actions** (CI/CD for test and build validation)


## Project Structure

```
src/main/java/com/davisantosp/Webhooker/
├── configs/                    # Kafka, RestClient, and other Spring configuration
├── controllers/                # REST endpoints (Event, Rule)
├── domain/
│   ├── DTOs/                   # Request/response DTOs
│   ├── entities/               # JPA entities (Rule)
│   └── enums/                  # Domain enums
├── infra/
│   ├── exceptions/             # Custom exceptions + global handler
│   └── security/               # Security-related infra
├── repositories/               # Spring Data repositories
├── services/                   # Business logic (RuleService, EventConsumer, etc.)
├── utils/                      # Utility/helper classes
└── WebhookerApplication.java
```


## Features

- **_Rules Management_**: **Rules**, used to trigger requests for events as **webhooks**, can be created, updated, seen and deleted via **RuleController API**.
- **_Event Trigger_**: **EventController API** can be used to trigger requests from your API using **rules**.

## APIs' Endpoints 

### _Rules Management_ (`/rules`) — public

| Method | Endpoint      | Description                  |
|--------|---------------|------------------------------|
| GET    | `/rules`      | Index for rules              |
| GET    | `/rules/{id}` | Get and specified rule by id |
| POST   | `/rules`      | Create a new rule            |
| PUT    | `/rules/{id}` | Update a rule using the id   |
| DELETE | `/rules/{id}` | Delete a rule using the id   |

### _Event Trigger_ (`/events/v1/api`) — public

| Method | Endpoint         | Description                               |
|--------|------------------|-------------------------------------------|
| POST   | `/events/v1/api` | Register a new event occur for processing |


## Getting Started

### Prerequisites

It is needed to run first on your machine:
* [Docker](https://www.docker.com/) and Docker Compose
* [Java JDK 21](https://adoptium.net/temurin/releases/?version=21)
* [Maven](https://maven.apache.org/) (optional, you can use the wrapper `./mvnw`)
