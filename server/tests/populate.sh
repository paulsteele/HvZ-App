#!/bin/bash
echo "Populating Database with Curl"
echo " "
#Add users
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "paul", "password": "pass", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "zombiepaul", "password": "passzombie", "admin": "false"}' localhost:8080/user`
#Login 
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"password": "fail"}' localhost:8080/user/paul` #fail case
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"password": "pass"}' localhost:8080/user/paul` #good case
#get User
echo `curl -Ss -X GET localhost:8080/00000000/user/paul`
#get All
echo `curl -Ss -X GET localhost:8080/00000000/user`
#create a game
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"gamename": "test", "creator": "noone"}' localhost:8080/`
echo $response
code=${response:13:8}
#get all games
echo `curl -Ss -X GET localhost:8080/`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$code/feedcode `
echo $response
fcode=${response:13:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$code'"}' localhost:8080/user/paul`
#get feedcode for zombie
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$code/feedcode `
echo $response
zcode=${response:13:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$zcode'", "gamecode":"'$code'"}' localhost:8080/user/zombiepaul`
#another login to show difference
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"password": "pass"}' localhost:8080/user/paul` #good case
#start game
echo `curl -Ss -X PUT localhost:8080/$code`
#set zombie
echo `curl -Ss -X GET localhost:8080/$code/forcezombie/$zcode`
#tag zombie first
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"tagger": "'$fcode'", "tagged": "'$zcode'"}' localhost:8080/$code/tag` #tag 
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"tagger": "'$zcode'", "tagged": "'$fcode'"}' localhost:8080/$code/tag` #tag 
#mission
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"title": "testmission", "humanobjective": "beat zombies", "zombieobjective": "beathumans"}' localhost:8080/$code/mission`