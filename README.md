## Introduction


We developed a desktop game application called **Alien Shooter Game**. The success criteria in the game is based on collecting points by shooting aliens. The game is comprised of 3  levels with increased difficulty level to level. The game can be played with single player for now. It will be able to be played in multiplayer mode soon. 


## Project Phases

### Phase 1 - Setup 

Repository is created.

### Phase 2 - Back End Development

It is built using Spring Boot, JPA and Rest. It provides secure and efficient way to store and fetch data for the game to and from the remote MySQL database. 

#### HTTP Verbs

HTTP verbs, or methods, should be used in compliance with their definitions under the [HTTP/1.1](http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html) standard.
These are of how HTTP verbs map to create, read, update, delete operations in a general context:


| HTTP METHOD | POST            | GET       | PUT         | DELETE |
| ----------- | --------------- | --------- | ----------- | ------ |
| CRUD OP     | CREATE          | READ      | UPDATE      | DELETE |
| /players    | Add player | List players | Error | Error |
| /players/{playerId}  | Error   | If exists, get player with playerId; If not, error| If exists, update player with playerId; If not, error | If exists, delete player with playerId; If not, error |
| /register    | Create player | Error | Error | Error |
| /login    |  Log in game | Error | Error | Error |
| /nextId    | Error | Give id for new player | Error | Error |
| /games    | Add game | List games | Error | Error |
| /games/{gameId}    |  Error | Give game with gameId | If exists, update game | If exists, delete game; if not, error |
| /scoreboardallthetime    | Error | Give scoreboard for all time | Error | Error |
| /scoreboardweekly    | Error | Give scoreboard for week | Error | Error |
| /getNextGameId    | Error | Give id for next game | Error | Error |


#### Example Requests

#### POST http://localhost:8080/register
```json
{
    "nickname": "user1",
    "password": "key1"
}
```

with response

```json
Player Saved.
```


#### GET localhost:8080/players

with response

```json
[
    {
        "playerId": 0,
        "nickname": "user1",
        "password": "3288498"
    },
    {
        "playerId": 1,
        "nickname": "user2",
        "password": "3288499"
    },
    {
        "playerId": 2,
        "nickname": "user3",
        "password": "3288500"
    }
]
```


In order to run back-end side of the game, the WAR file is created to be deployed to the server.

WAR file of back end server can be found in executables folder:

```
cd executables
java -jar alien-shooter-serverside-0.0.1-SNAPSHOT.war
```


### Phase 3 - Front End Development 


In this phase, graphical user interface is developed. After executing server developed in phase 2 once, Alien Shooter Game can be played via GUI. It is provided how to play game in *HowtoPlayAlienShooter.pdf* file.


JAR file of client side can be found in executables folder:


```
cd executables
java -jar alienshooter-clientside-0.0.1-SNAPSHOT.jar
```

### Phase 4 - Concurrency 

In this phase, we created another server for multiplayer game. After players completed first 3 level of the game, they connect to this server and play the game in multiplayer mode. This server just provides communication between players and acts a referee, which determines who has won the game.

JAR file of this server can be found in executables folder:
```
cd executables
java -jar matchmaking-server.jar
```


### Technologies and Tools that are used

- JavaFX : In order to develop front end of the game.
- MariaDB : In order to set up database server in back end side of the game.
- Spring Boot : In order to develop back end side (servers, services, controllers) of the game. 



## Useful Links

- POSTMAN - http://www.getpostman.com



## Contributors

* *Ömer ÇETİN*
* *Oğuzcan BUDUMLU*


## License


This project is licensed under the [MIT License](http://opensource.org/licenses/MIT)
