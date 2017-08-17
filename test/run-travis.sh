#!/bin/sh

cd $(dirname $0)

cd ..

./mvnw clean package install site site:deploy -Ptravis