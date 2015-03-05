#HvZ Master App
##Purdue University CS 30700 Project

[Project Calendar](https://www.google.com/calendar/embed?src=9nbo43qa79nmsmpnseo66n64oo%40group.calendar.google.com&ctz=America/New_York)

Server will run on 128.211.191.47:8080


####Server Mappings
Registers a player to the game being run by the server.
Needs a username and password.
Can be supplied with a feedcode and an admin flag.

`/user/register?username="username"&password="password[&feedcode="feedcode"&admin="false"]`

Generates a feedcode, it is checked to make sure that it is already not taken but won't be held for the player until `/user/register` is called with that feedcode.
Can be supplied with admin flag.

`/feedcode/generate?admin="false"`

Returns player information
Needs the feedcode of that player

`/user/get?feedcode="feedcode"`

Returns all players

`/user/getall`

Preforms tag action.
Needs the feedcodes of the tagger and the tagged.

`/tag?tagger="tagger"&tagged="tagged"`

Starts the game session.

`/game/begin`

Checks to see if game session is running.

`/game/isstarted`

Logs an existing player into the game.

`/user/login?feedcode="feedcode"&password="password"`