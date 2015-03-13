#HvZ Master App
##Purdue University CS 30700 Project

[Project Calendar](https://www.google.com/calendar/embed?src=9nbo43qa79nmsmpnseo66n64oo%40group.calendar.google.com&ctz=America/New_York)

Server will run on 128.211.191.47:8080


####Server Mappings

`/user`

 * POST: registers a user
 	* `{"username": value, "feedcode": value, "admin": value, "password": value}`
 	* username, feedcode, and password are strings, while admin is a boolean
 * GET: gets all users

`/user/identifier`

 * POST: logs in a user
 	* `{"password": value}`
 	* password is a string
 * GET: retrieves a single user

`/feedcode`

 * POST: generates a feedcode
 	* `{"admin", value}`
 	* admin is a boolean
