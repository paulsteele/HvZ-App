#!/bin/bash
echo "Populating Database with Curl"
#Add users
echo `curl -X POST -H "Content-Type: application/json" -d '{"username": "paul", "password": "pass", "admin": "false"}' localhost:8080/user`