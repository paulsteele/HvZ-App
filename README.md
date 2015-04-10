#HvZ Master App
##Purdue University CS 30700 Project

[Project Calendar](https://www.google.com/calendar/embed?src=9nbo43qa79nmsmpnseo66n64oo%40group.calendar.google.com&ctz=America/New_York)

Server will run on 128.211.191.47:8080


####Server Mappings

`/`

 * POST: Creates a game
 	* `{"gamename": value, "creator": value}`
 * GET: Gets a list of all games

`/user`

 * POST: registers a user
 	* `{"username": value, "admin": value, "password": value}`
 	* username, feedcode, and password are strings, while admin is a boolean


`/user/{username}`

 * POST: logs in a user
 	* `{"password": value}`
 	* password is a string
 * PUT: update a user
 	* `{"feedcode": value, "gamecode": value}`
 * GET:  retrieves a user by username

`/{game}`

 * PUT: Start the game
 * GET: Check if game is started

`/{game}/user`

 * GET: gets all users in a game

`/{game}/user/{feedcode}`

 * GET: retrieves a single user by feedcode in a game

`/{game}/feedcode`

 * POST: generates a feedcode in a game
 	* `{"admin": value}`
 	* admin is a boolean

`/{game}/tag`

 * POST: executes a tag
 	* `{"tagger": feedcode, "tagged": feedcode}`

`/{game}/revivecode`
 * GET: generates a new revivecode
 * POST: use a revivecode
  * `{"revivecode": value, "feedcode": value}`

`/{game}/revivecodes`
 * GET: returns all revive codes in a game

`/{game}/mission`
 * POST: creates a mission
 	* `{"title": value, "humanobjective": value, "zombieobjective": value}`
 * GET: gets an array of all missions in a game

`/{game}/mission/{title}`
 * GET: gets the specific mission
 * PUT: update the mission
    * `{"complete": value}`

###Globals
`Globals getInstance()`
 * gets interface for global constants

`void setSelf()`
 * set current user

`void getSelf()`
 * gets current user

`Boolean isAdmin()`
 * check if self is admin

`Boolean isZombie()`
 * check if self is zombie
 
`String getFeedCode()`
 * get self's feedcode
`String getGameCode()`
 * get current game code
