# Peer Mentoring App

The backend repository of Codecool's IRL coding challenge's Peer Mentoring application.

## Technologies

- Spring Boot
  - Web
  - Security
  - JPA
- PostgreSQL

## Setup

### Development

Setup environment variables in application.properties:
```
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.url=${DB_URL}
spring.jpa.hibernate.ddl-auto=${HIBERNATE_DDL}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.profiles.active=${PROFILE}
```
To initialize database with tags and basic users, set PROFILE to "development", HIBERNATE_DDL to "create" or "create-drop".
In "development" PROFILE an init method is executed, located in PeerMentoringBackEndApplication.

In deployed application set PROFILE to "production", HIBERNATE_DDL to "update".

### Testing

Tests are using and H2 database. Setup environment variables in application-test.properties:
```
spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=false
```
