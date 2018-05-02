#!/usr/bin/env bash

#./mvnw clean install -DskipTests=true

./mvnw clean install site -DskipTests=true

#./mvnw install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
#./mvnw clean site site:deploy -Ptravis -Dtest=AlphaTopLevelSuiteIT  -B -V

exit 0
