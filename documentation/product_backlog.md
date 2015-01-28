#Project Backlog
#####Group 18
#####HvZ Master App
######Rob Mantzoros, Kyle Rodd, Paul Steele, Wels Lin, Mamasi Goel

#####Problem Statement
HvZ is a nationwide campus organization that runs a week long mock post apocalyptic humans versus zombie survival game and currently it has very little infrastructure. There is not currently an application that helps run and organize a game. We want to create an app that will structure the organization of the game, while including features that can enhance the overall experience.

#####Background Information
HVZ is similar to a large scale version of tag with added elements like Nerf guns. The goal of the humans is to survive the week without getting tagged by the zombie team, using the Nerf guns to hold off the zombie waves, while the goal of the zombies is to tag and infect all humans. Currently there is little infrastructure for handling the large amount of data that is needed to run a game of HvZ. Typical players of the game (the potential users of the app) are college aged adults, and standard sizes of games have anywhere from 50 - 200+ people playing at once. Each player has a plethora of information to handle such as their unique identifier code, who they've tagged during the day, what the boundaries of the game are, whose been tagged recently, what their current mission objective is, and a plethora of other details. At night they go home and have to enter their log of tags into an online form. This is slow, not in real time, and very prone to errors. There are existing apps that try to help the process, but all in all they have limited use outside of being a bar code scanner throughout the week. Ours will have much more utility. Those apps focus on the single act of transferring tags between players, while ours will attempt to remedy a large number of the problems listed above.
#####Requirements
#####Functional Requirements

 * As an admin, I'd like to be able to create and start a game session.
 * As a player, I'd like to be able to register for a game session.
 * As an admin, I'd like to be able to create boundaries for the game session in an in-app map interface.
 * As a player, I'd like to be able to view the set up map boundary.
 * As a user, I'd like to see a list of who is on the human, or zombie team.
 * As an admin, I'd like to push notifications to the players.
 * As a player, I'd like to be able to see notifications from the admins.
 * As a player, I'd like to be able to send complaints to the admins.
 * As an admin, I'd like to be able to receive complaints from the players.
 * As a human, I'd like to be able to register a tag through the app.
 * As a zombie, I'd like to be able to be tagged and have my own tagging functionality be disabled for a cool down timer.
 * As a zombie, I'd like to be able to register a tag through the app.
 * As a human, I'd like to be able to be tagged and the converted to the zombie team.
 * As a player, I'd like to be able to group chat between a limited number of players. (if time allows)
 * As a player, I'd like to view in-game statistics.
 * As an admin, I'd like to directly change database information through the server console.

#####Non-Functional Requirements

#####Performance

 * The interface should be smooth and fluid without stutters. 
 * The client will be a UI for the players while sending work to be done on the server.
 * Admins will have unlocked features in app.
 * The server will handle all requests and do the bulk of the work, while storing information into the database.
 * A minimal amount of data should be sent through the app to avoid data overages for the user.

#####Design

* The main screen should be intuitive and have a low number of button presses to get to the desired action. 
* The main screen should have a grid layout with sub menus appearing when certain boxes are pressed.

#####Security

* The application, and server should be able to handle login information securely.


