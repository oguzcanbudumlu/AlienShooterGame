# Alien Shooter Game



## Introduction



## Project Phases

### Phase 1 - Setup 

Repository is created.

### Phase 2 - Back End Development

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








### Phase 3 - Front End Development 



### Phase 4 - Concurrency 


## Useful Links

- POSTMAN - http://www.getpostman.com



## Contributors

* *Ömer ÇETİN*
* *Oğuzcan BUDUMLU*


## License


This project is licensed under the [MIT License](http://opensource.org/licenses/MIT)