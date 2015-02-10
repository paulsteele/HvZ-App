#Design Document
#####Group 18
#####HvZ Master App
######Rob Mantzoros, Kyle Rodd, Paul Steele, Wells Lin, Manasi Goel

####Purpose

HVZ is similar to a large scale version of tag with added elements like Nerf guns. The goal of the humans is to survive the week without getting tagged by the zombie team, using the Nerf guns to hold off the zombie waves, while the goal of the zombies is to tag and infect all humans. Zombies are required to “feed”, or tag a human at least once every 48 hours.  If they fail to do this, they “starve” and are removed from the game.  At the beginning of the game everyone starts on the human team besides 1-4 original zombies who have to infect the rest of the players. Currently there is little infrastructure for handling the large amount of data that is needed to run a game of HvZ. Typical players of the game (the potential users of the app) are college aged adults, and standard sizes of games have anywhere from 50 - 200+ people playing at once. Each player has a plethora of information to handle such as their unique identifier code, who they've tagged during the day, what the boundaries of the game are, who's been tagged recently, what their current mission objective is, and a plethora of other details. At night they go home and have to enter their log of tags into an online form. This is slow, not in real time, and very prone to errors. There are existing apps that try to help the process, but all in all they have limited use outside of being a barcode scanner throughout the week. Ours will have much more utility. Those apps focus on the single act of transferring tags between players, while ours will attempt to remedy a large number of the problems listed above.


* As an admin, I'd like to... 
	* be able to create and start a game session.
	* be able to place users into 3 different types of user (human, zombie, and non player character), and define which players are admins.
	* directly change database information through the server console.
	* change game parameters, such as cool down timers, through the app.
	* be able to create boundaries for the game session through an in-app map interface.
	* at the start of the game be able to randomly select the original zombies.
	* push text notifications to the players.
	* be able to receive complaints from the players.
	* create mission objectives that all players can see.
	* be able to set up polls for player bounties.
	* be able to view poll results and set up bounties for certain players.
	* be able to generate revive codes for zombies that let them convert back to humans.
	* be able to make users untaggable for a set amount of time.


* As a player, I'd like to...
	* be able to register for a game session.
	* be able to register a tag through the app.
	* be able to see notifications from the admins.
	* see mission objectives through the app.
	* be able to send complaints to the admins.
	* view in-game statistics
	* be able to view the set up map boundary.
	* see a leaderboard of current players
	* see a list of who is on the human, or zombie team.
	* be able to drop out of the game through the app, disabling app features for that user through the duration of the game.
	* be able to see a score screen when the game session is over.
	* be able to vote in a poll for bounties.
	* be able to receive bonus points for tagging a bountied player.
	* look on the map and see gps location of other players. (if time allows)
	* be able to group chat between a limited number of players. (if time allows)

* As a human, I'd like to...
	be able to be tagged and then converted to the zombie team.

* As a zombie, I'd like to...
	be able to be tagged and have my own tagging functionality be disabled for a variable cool down timer.
	be able to enter revive codes that convert me back into the human team.
	be able to watch a countdown clock of time that I have to tag a human and prevent my removal from the game.

####Design Outline

Our project will consist of a mobile client application that communicates to a singular server over a REST interface. The server will store information in a local database and also process most of the data. The project will primarily follow a client-server architecture. Players of the game will use the mobile app as their “guide book” to the game while they play, this means that the client needs to be fast and responsive. Instead of the admins keeping track of all the game details and handling infrastructure themselves the local server will do that work for them. The server will need to be able to run autonomously, but also be adaptable to parameter changes made by the admins. Important information will be stored in a lightweight database for speedy access. Certain functionality of the mobile app will require talking to the game server and such communications will be done through REST and JSON to take advantage of a simple organizational hierarchy for data access, and make communication standardized, yet lightweight.

&nbsp;

1.	Database
	1.	The database will store information needed to run the application.
	2.	Typical data will be a list of users (and their information) and game information.
2.	Server
	1.	The server is the interface between the database and user.
	2.	The user will give the server information to store in the database.
	3.	The server will request information from the database as needed.
3.	User
	1.	The user deals with the client side of the application.
	2.	The user contains information seen by players and admin.
	3.	Every user has a username, unique ID and is classified as either a player or admin.

Figure 2.1 - High level overview
![High level overview](https://github.com/paulsteele/HvZ-App/blob/master/documentation/resources/high_level.png "High level overview")

There will be one database and one server. The two will interact with each other directly. A server may have multiple users, which can be admin or players. As seen in the diagram, the server will be the layer between the database and user.

####Design Issues

1.	Back-end 

	Options:

    	1. Server and Database
    	2. Server and file directory
    	3. Database only

	Decision: Server and Database

	Reason: Making the client talk directly to the database would require a lot of effort for minimal improvement in usability. Originally a file directory seemed like a good idea as, not much set up work would need to be done to get the required information set up. However with database options like SQLite that require virtually no set up, a file directory seems obsolete.


2. Database Architecture

	Options:

		1. MicrosoftSQl
		2. PostgreSQL
		3. SQLite

	Decision: SQLite

	Reason: Cost barriers prevent use of MicrosoftSQL, and PostgreSQL requires a lot of overhead to set up. SQLite provides the necessary functionality of a database without the extreme setup requirements of the others.

3. Photo uploading from Administrative class

    Options:

		1.	Photo uploader from Android phone library
		2.	In app map that allows the user to take a picture of the exact area they want to use as the game map

	Decision: Photo uploaded from phone library.

	Reason: An in app map would require lots of effort while providing little additional advantage. Players only need to see if there location is part of a safe zone or not and this can be done by referencing an image of a map, which can be uploaded.

4. Client UI

    Options:

		1.	Vertical list of options on a sidebar
		2.	Grid layout
		3.	Tabs	

    Decision: Grid layout

	Reason: A grid layout was chosen to utilize all available screen space and simplify item selection. The grid will provide maximum clarity and follows a similar UI as the Purdue app which many of potential users will be familiar with.

5. Tagging functionality 

	Options:

		1.	Only the tagger sends in request to server
		2.	Both the tagger and player tagged send in request to server

    Decision: Both the tagger and the player tagged send in request to server

	Reason: While only having one player send in the request would require less work, making both players send in a request ensures that information is secure and minimizes the chance that errors are made handling tags.

6. Username requirements

    Options:

		1.	Usernames must be unique
		2.	Usernames do not need to be unique

    Decision: Usernames do not need to be unique

	Reason: While most of the time, usernames are something that are seen as needing to be unique, in our implementation the username is more of a personal identifier that is desired by players but has no real technical need. Users already have unique identifiers (feed codes) that will allow the application to differentiate users from one another.

####Design Details

1.	Classes and functionality
	1.	Server 
		1.	Handles requests from player to admin to join/quit/handle problems of the game
		2.	Capability to update database information from user requests
		3.	Capability to send users information from the database based upon request
		4.	Contains all required attributes of which make up the game including the time left, mission lists for both zombies and humans, the number of remaining human players, the number of remaining zombie players, and the game area map photo.
		5.	Periodically checks for zombies that have not “fed” in a desired amount of time.  If any given zombie has failed to accomplish the task of tagging a human within the last 48 hours, the server will tell the database to remove the zombie from the game.
	2.	User
		1.	Players (super class that applies to all the players in the game)
			1.	Contains a boolean flag to show the current status of the player
			2.	Represented to user as a GUI android interface
			3.	Provides tagging functionality 
			4.	Provides area to see game boundaries by request from user.
			5.	A unique username
			6.	A unique game ID
			7.	Lists representing the ID’s of the people of have converted or killed, depending on the current state of the user’s status flag, stored and updated from the database upon opening the app.
			8.	Receives mission updates
			9.	Provides ability to access server information for viewing only in the form of a leaderboard which shows the point value each user has achieved
		2.	Specialty Players
			1.	This includes a randomly decided alpha zombie
			2.	This player will receive a message reminding them that they do not have to wear a bandana in the first day of gameplay.
		3.	Administrative Player
			1.	All player features
			2.	Provides functionality to start a new game
			3.	Ability to upload photo for game boundaries to server
			4.	Ability to modify potential leaderboard issues as requested by players
			5.	Can send out mass messages to be used as missions, or inform all players of any and all game information that may be relevant
	3.	Mission
		1.	Missions can be sent as messages to either entire team
		2.	They will each possess a distinct point value that the user(s) will be awarded after said mission
		3.	The point values will be awarded upon approval from the admin to prevent any form of cheating or bias
	4.	Database
		1.	Possesses every players’...
			1.	User ID
			2.	Username
			3.	Point Values
			4.	List of kills
			5.	List of converted
		2.	Player information is sent to each user through the server upon opening the application, keeping the bulk of the memory used in the database on not on the user’s personal device
2.	UI Design
	1.	Overall Layout
		1.	Login
			1.	Username
				1.	This is nickname player wishes to use
			2.	Feed code
				1.	This is an unique number to identify players
				2.	Generated by the server by pressing button
		2.	Main screen
			1.	Grid layout of buttons
			2.	Time remaining
			3.	Player status
	2.	Button functionalities
		1.	All
			1.	General
				1.	Time remaining
				2.	Map
				3.	Player list
				4.	Leader board
				5.	Number of humans/zombies
			2.	Missions
				1.	View Current
				2.	View Completed
				3.	View Failed
			3.	Polls
				1.	Participate in open polls
				2.	View past polls’ results
			4.	Bounty
				1.	View current bounties and their rewards
				2.	View list of players that completed bounties
			5.	Rewards
				1.	View list of rewards player has received
		2.	Human/Zombie 
			1.	Tagging
				1.	Manual input of feed code or,
				2.	NFC tagging
				3.	For zombies, cool down timer shown when tagged
			2.	Complaints
				1.	Sends admins complaints
			3.	Revive (Zombie only)
				1.	Enter revive code to convert to human
		3.	Admin
			1.	Missions
				1.	Create missions
				2.	Remove missions
			2.	Polls
				1.	Create polls
				2.	Close polls
				3.	Remove polls
			3.	Complaints
				1.	View complaints
				2.	Resolve complaints (removes from list)
			4.	Admin specific powers
				1.	Create/edit map
				2.	Modify database
				3.	Generate revive code
				4.	Make user untaggable
			5.	Options
				1.	Allow admin to change certain parameters of game
3.	Activities
	1.	Tagging
		1.	Human and zombie players tag each other using app
		2.	Sends information to server and server verifies
		3.	Server updates database
		4.	If human tags zombie, zombie is shown cooldown timer
		5.	If zombie tags human, human changed to zombie type
	2.	Updating information
		1.	User requests info from server
		2.	Server requests info from database
		3.	Database sends info to server
		4.	Server sends info to user
	3.	Creating missions, polls, bounties
		1.	Admin sends information to server (mission conditions, poll question choices, bounties info, etc.)
		2.	Server sends info to database to update
		3.	Server notifies players

Figure 4.1 -  Sequence Diagrams
![Sequence Diagrams](https://github.com/paulsteele/HvZ-App/blob/master/documentation/resources/sequence_diagram.png "Sequence Diagrams")

Figure 4.2 - Mock UI
![Mock UI](https://github.com/paulsteele/HvZ-App/blob/master/documentation/resources/mock_ui.png "Mock UI")

Figure 4.3 - Class Design
![Class Design](https://github.com/paulsteele/HvZ-App/blob/master/documentation/resources/class_design.png "Class Design")

