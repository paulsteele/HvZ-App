#Product Backlog
#####Group 18
#####HvZ Master App
######Rob Mantzoros, Kyle Rodd, Paul Steele, Wells Lin, Manasi Goel

#####Problem Statement
HvZ is a nationwide campus organization that runs a week long mock post apocalyptic humans versus zombie survival game and currently it has very little infrastructure. There is not currently an application that helps run and organize a game. We want to create an app that will structure the organization of the game, while including features that can enhance the overall experience.

#####Background Information
HVZ is similar to a large scale version of tag with added elements like Nerf guns. The goal of the humans is to survive the week without getting tagged by the zombie team, using the Nerf guns to hold off the zombie waves, while the goal of the zombies is to tag and infect all humans. At the beginning of the game everyone starts on the human team besides 1-4 original zombies who have to infect the rest of the players. Currently there is little infrastructure for handling the large amount of data that is needed to run a game of HvZ. Typical players of the game (the potential users of the app) are college aged adults, and standard sizes of games have anywhere from 50 - 200+ people playing at once. Each player has a plethora of information to handle such as their unique identifier code, who they've tagged during the day, what the boundaries of the game are, whose been tagged recently, what their current mission objective is, and a plethora of other details. At night they go home and have to enter their log of tags into an online form. This is slow, not in real time, and very prone to errors. There are existing apps that try to help the process, but all in all they have limited use outside of being a bar code scanner throughout the week. Ours will have much more utility. Those apps focus on the single act of transferring tags between players, while ours will attempt to remedy a large number of the problems listed above.
#####Requirements
#####Functional Requirements

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

* As a player, I'd like to ...
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

* As a human, I'd like to ...
 * be able to be tagged and then converted to the zombie team.

* As a zombie, I'd like to ...
 * be able to be tagged and have my own tagging functionality be disabled for a variable cool down timer.
 * be able to enter revive codes that convert me back into the human team.
 * be able to see a timer until I am kicked out of the game for not tagging constantly enough.
 * be able to have my timer reset once a tag is completed.


#####Non-Functional Requirements

* The application should be able to run on android devices.
* The interface should be smooth and fluid without stutters. 
* The phone app should be a UI for the players, sending work to be done on the server.
* Admins should have unlocked features in app.
* The server should handle all requests and do the bulk of the work, while storing information into the database.
* A minimal amount of data should be sent through the app to avoid data overages for the user.
* The server should able to handle 500 players at once.
* The main screen should be intuitive and have a low number of button presses to get to the desired action. 
* The main screen should have a grid layout with sub menus appearing when certain boxes are pressed.
* The app interface should change themes depending on player status (if time allows).
* The application, and server should be able to handle login information securely.
