# Peer Mentoring App

The backend repository of Codecool's IRL coding challenge's Peer Mentoring application.

## Technologies

- Spring Boot
  - Web
  - Security
  - JPA
  - Mail
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

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${EMAIL_ADDRESS}
spring.mail.password=${EMAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
To initialize database with tags and basic users, set PROFILE to "development", HIBERNATE_DDL to "create" or "create-drop".
In "development" PROFILE an init method is executed, located in PeerMentoringBackEndApplication.

In deployed application set PROFILE to "production", HIBERNATE_DDL to "update".

To send email through Gmail, if 2-step verification is not enabled, allow access for "Less secure apps". If 2-step verification is enabled, create an Application Password to use with the application.

### Testing

Tests are using an H2 database. Setup environment variables in application-test.properties:
```
spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=false
```
