# Postii
A lightweight and very scalable to create Posts (like Twitter). Twitter has tweets, **Postii** has **pweets**
(a word created by me for this specific project :). This product was designed to be extremely scalable, therefore was
split into 4 projects (it is **not** a multimodule project, each should have its own pipeline):
* **postii-user:** manage users and relationships and contains the DB migration. Port 9999. Swagger: http://localhost:9999/swagger-ui/#/
* **postii-pweet-reader:** a GraphQL API for reading Pweets only (the idea is to be independently scalable). Port 9998.
* **postii-pweet-subscriber:** subscriber of "new.pweet" queue to persist new Pweets
  (the idea is to be independently scalable). Port 9997.
* **postii-pweet-publisher-api:** enqueue the new Pweets in "new.pweet" queue. Port 9996.

ps. for clear examples, Postman collection "attached"

### Requirements
* Java 17
* Node 18.3 & NPM (or Yarn)
* Docker and Docker Compose (for Neo4j, RabbitMQ and Redis - not the best option for scale proposes, 
I did in way to be easier to run)
* Free ports: 9999, 9998, 9997, 9996, 7474 & 7687 (Neo4j), 5672 & 15672 (RabbitMQ) and 6379 (Redis)

### Running
(extremely recommended to run in Linux/Unix environment)
* First running docker images with docker compose <br>
 ``` $ sudo docker-compose up ```
* Run the **postii-user** from some IDE or:
  ```
  $ cd postii-user/
  $ ./gradlew clean build
  $ ./gradlew bootRun
  ```
* Do the same above for **postii-pweet-reader** and **postii-pweet-subscriber** 
* Run the **postii-pweet-publisher-api**:
  ```
  $ cd postii-pweet-publisher-api/
  $ npm install
  $ npm run server  
  ```

### Rules
* Follow / Unfollow
* Post a Pweet
* Post a Pweet quoting another Pweet
* 5 posts/pweets in 24 hours time span. ie. If the same user posts 4 messages at 11 p.m. and 1 message at 9 a.m. on the next day, this user needs to wait until 11 p.m. to post more. 
  
### Dependencies Map
* postii-user: Neo4j
* postii-pweet-subscriber: RabbitMQ, Neo4j
* postii-pweet-reader: Neo4j
* postii-pweet-publisher-api: postii-user, RabbitMQ 

### Database
* Neo4j without auth! Running in default port: http://localhost:7474/browser/  (empty for username/password) 
* 5 users already created: Ricardo, Neymar, Lula, Ciro and Bolsonaro
  * Ricardo: following nobody, followed by everyone except for Bolsonaro
  * Neymar: following everyone except for Bolsonaro, followed by nobody
  * Lula: following Ricardo and Ciro, followed by Neymar and Ciro
  * Ciro: following Ricardo and Lula, followed by Neymar and Lula 
  * Bolsonaro: following nobody, followed by nobody

### Improvements
* The Cypher queries in postii-pweet-reader
* Use of messages.properties or similar
* Create the Repweet (new word too)
