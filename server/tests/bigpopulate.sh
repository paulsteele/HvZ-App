#!/bin/bash
echo "Populating Database with Curl"
echo " "
#Add admin
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "admin", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "true"}' localhost:8080/user`
#create game
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"gamename": "Big game", "creator": "admin"}' localhost:8080/`
game=${response:28:8}
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "true"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/admin`
#Add 100 players
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player1", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player2", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player3", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player4", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player5", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player6", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player7", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player8", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player9", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player10", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player11", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player12", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player13", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player14", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player15", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player16", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player17", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player18", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player19", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player20", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player21", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player22", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player23", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player24", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player25", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player26", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player27", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player28", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player29", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player30", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player31", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player32", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player33", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player34", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player35", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player36", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player37", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player38", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player39", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player40", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player41", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player42", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player43", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player44", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player45", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player46", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player47", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player48", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player49", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player50", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player51", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player52", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player53", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player54", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player55", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player56", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player57", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player58", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player59", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player60", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player61", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player62", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player63", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player64", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player65", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player66", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player67", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player68", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player69", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player70", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player71", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player72", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player73", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player74", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player75", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player76", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player77", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player78", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player79", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player80", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player81", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player82", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player83", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player84", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player85", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player86", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player87", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player88", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player89", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player90", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player91", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player92", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player93", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player94", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player95", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player96", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player97", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player98", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player99", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
echo `curl -Ss -X POST -H "Content-Type: application/json" -d '{"username": "player100", "password": "22ea1c649c82946aa6e479e1ffd321e4a318b1b0", "admin": "false"}' localhost:8080/user`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player1`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player2`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player3`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player4`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player5`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player6`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player7`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player8`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player9`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player10`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player11`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player12`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player13`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player14`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player15`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player16`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player17`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player18`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player19`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player20`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player21`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player22`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player23`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player24`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player25`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player26`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player27`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player28`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player29`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player30`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player31`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player32`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player33`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player34`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player35`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player36`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player37`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player38`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player39`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player40`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player41`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player42`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player43`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player44`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player45`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player46`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player47`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player48`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player49`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player50`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player51`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player52`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player53`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player54`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player55`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player56`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player57`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player58`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player59`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player60`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player61`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player62`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player63`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player64`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player65`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player66`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player67`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player68`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player69`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player70`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player71`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player72`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player73`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player74`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player75`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player76`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player77`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player78`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player79`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player80`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player81`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player82`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player83`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player84`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player85`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player86`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player87`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player88`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player89`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player90`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player91`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player92`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player93`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player94`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player95`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player96`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player97`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player98`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player99`
#get feedcode
response=`curl -Ss -X POST -H "Content-Type: application/json" -d '{"admin": "false"}' localhost:8080/$game/feedcode `
echo $response
fcode=${response:28:8}
#update user
echo `curl -Ss -X PUT -H "Content-Type: application/json" -d '{"feedcode": "'$fcode'", "gamecode":"'$game'"}' localhost:8080/user/player100`