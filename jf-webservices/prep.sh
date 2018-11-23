#!/bin/bash

docker-compose down
docker rm $(docker ps -a -q)
docker rmi $(docker images -q)
cd ..
./build-docker-images.sh
cd jf-webservices/
docker-compose up
