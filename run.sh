#!/bin/sh

export JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/postgres?user=postgres
mvn spring-boot:run