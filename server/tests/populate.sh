#!/bin/bash
echo "Populating Database with Curl"
echo " "
#Add users
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "paul", "password": "pass", "admin": "false"}' localhost:8080/user`
#Login 
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"password": "fail"}' localhost:8080/user/paul` #fail case
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"password": "pass"}' localhost:8080/user/paul` #good case
#get User
echo `curl -Ss -X GET localhost:8080/00000000/user/paul`
#get All
echo `curl -Ss -X GET localhost:8080/00000000/user`
#create a game
echo `curl -Ss -X GET localhost:8080/newgame`
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "APPLE", "gamecode": "BANANA"}' localhost:8080/user/paul`
#another login to show difference
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"password": "pass"}' localhost:8080/user/paul` #good case