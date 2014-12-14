# Final Project Documentation

## Intro
For the final project I built a Trivia server for coders. This server allows users to connect via good old fashioned command line and play trivia with questions from computer science as well as submit answers for coding excercises (similar to ProjectEuler.net). I built it using akka and Actor model/pattern.

## Server
   The server can be started running ./target/start csp.akka.TriviaServer via command line. Once it starts, it displays the welcome screen to the admin, and waits for users to connect on port 7000. 

Once a user connects, it displays a log message on the server side indicating that a user joined. It also mentions the user's ID (which is the port number the user connects from). For instance: Player 57800 has joined.

At the same time, it sends a welcome message to the client along with some indication on how to start the activity once the user has connected.

Sample welcome screen:

*************************************************************
Welcome to TriviaServer
You are player 57800
For a list of available commands type: !help
For coding problems execute your code locally in your preferred languange and only provide the result via command line.

*************************************************************

## Clients

As soon as the client connects to the server, he/she can type !help. This will display an info screen with relevant commands.

Sample info screen (sent only to the user who requests it):

***************  List of available commands  *******************
<!start>           - start a new Trivia Game
<!stop>            - stops the current game
<!players>         - displays the number of players connected
<!bye>             - exit the trivia game
<!whoami>          - lists information about current player

All of these commands are returning the response only to the user who requests it.
This server can handle a large number of players, and it can have features easily added to it.

## Usage

When user types !start, a new game will begin in 3 seconds. The delay allows other users to get ready for the next question.

Sample Screen:
**********************
!start
New Game Starting
Remember to type your answer in lowercase letters.
Go !!!
-Question goes here-
**********************

When a user types !stop, all other users will receive a message announcing who stopped the game. When any of the other users will type !start, game will be resumed and will not start over.

****************
!stop
Game stopped by player 57800.
****************


When a user types !players, he/she will get a reply back with the total number of users currently logged in and playing:

**************
!players
Current players: 10

**************


The !bye command will disconnect the user who requested it


Whenever a user joins in, all other playes will get a status update announcing the ID of the user who joined.

*****************
Player 57917 has joined.
Player 57922 has joined.
Player 57911 has joined.
Player 57928 has joined.
*****************

Any other input that does not start with "!" at the beginning will be considered a response to a question.
If there is no game currently running, user will be informed accordingly:

********************
a
Be patient.. the game hasn't started yet
Hello
Be patient.. the game hasn't started yet
********************

When the game is running, the question will stay active until somebody answers it correctly. When a player answers correctly to the question, all other users will be announced:

New Game Starting
Remember to type your answer in lowercase letters.
Go !!!
If you need to sort a very large list of integers (billions), what efficient sorting algorithm would be your best bet?
                           
                          (one of the players answers correctly)


Player 57961 answerred correctly: quicksort

**************
Next Question:
**************

Which company is the largest manufacturer of network equipment?


When a player answers incorrectly, he/she will get another try, unless somebody else answers in the meantime:
****************
Which company is the largest manufacturer of network equipment?
test
Try again !
****************




























