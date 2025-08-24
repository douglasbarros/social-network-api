# API for a Simple Social Network

The idea is to develop a REST API for a social network with the following functionalities:

* Post messages
* Read the timeline
* ​​Follow other users
* Show a wall with posts from users I follow
* Mention other users
* Share links
* Send direct messages to a user
* Create a user

## Technologies

The project was developed with the following technologies:

* Java 24
* Maven
* Springboot Web
* Springboot Data JPA
* H2 database
* Lombok

## Getting Started

### Steps to run

Run the following commands:

> mvn clean package  
> mvn spring-boot:run  
* You can access the API at <http://localhost:8080/api/v1>  
* You can use the collection-postman.json  
* You can access the API Swagger at <http://localhost:8080/api/v1/docs>  

### CLI

The CLI can be accessed from CLI.java.  
The command post will create a new user if not exists.

The available commands are:

```shell
post <user> <message>
timeline <user>
create <user>
follow <follower> <followed>
wall <user>
dm <from> <to> <message>
inbox <user>
chat <userA> <userB>
mentions <user>
```

A test scenario would be this:

```shell
post alice Hello World! This is my first message. Check out @bob and this link https://google.com
post bob Hello @alice! Please check this link https://facebook.com
timeline alice
timeline bob
create charlie
follow charlie alice
follow charlie bob
wall alice
wall bob
wall charlie
dm alice bob Hi there!
inbox bob
chat alice bob
mentions alice
mentions bob
```

### Next Steps

* Implement authentication
* Create a web interface
* Create Docker configurations
* Create a CI/CD pipeline and deploy
