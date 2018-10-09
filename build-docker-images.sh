#!/bin/bash

echo "# Building bus image..."
cd jf-webservices/jf-bus
docker build . -t jf-bus

echo "# Building engine webservices image..."
cd ../jf-engine-ws
docker build . -t jf-engine-ws