#!/bin/bash

start_time=$(date +%s%N)

echo "mvn clean package ..."
mvn clean package
echo "mvn clean package done"

./build-docker-images.sh

echo "mvn compile..."
mvn compile
echo "mvn compile done"

execTime=$(( ($(date +%s%N) - $start_time) / 1000000))
echo "Execution time: $execTime ms"