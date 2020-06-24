# Reddit clone application
##Spring Boot (Kotlin) + Angular + Docker 
### Как запустить:
Приложение запускается одной командой: 
```
$ docker-compose up
```

#### Backend
- Kotlin (Spring Boot)
- PostgreSQL
- Spring Data
- Docker Compose
- JWT
- JKS
- Swagger
- JUnit/Mockito
#### Frontend
- Angular 9
- Bootstrap 4
- nginx
- Fort Awesome

Вдохновлялся замечательным туториалом **https://youtu.be/DKlTBBuc32c**

Переписал на Kotlin, пофиксил баги, положил в docker-compose

После запуска приложение доступно по ссылке: **http://localhost**

Документация REST api доступна по ссылке: **http://localhost:8080/swagger-ui.html**

Логины и пароли можно посмотреть в *docker-compose.yml*.

```yml
version: '3'

services:

  #PostgreSQL
  breddit-postgres:
    image: "postgres"
    container_name: breddit-postgres
    ports:
      - 5432:5432
    environment:
      - POSTGRES_DB=breddit
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
    networks:
      - backendNetwork

  #Backend
  breddit-backend:
    build: ./backend
    container_name: breddit-backend
    environment:
      - DB_SERVER=breddit-postgres
      - POSTGRES_DB=breddit
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
    ports:
      - 8080:8080
    depends_on:
      - breddit-postgres
    networks:
      - backendNetwork
      - frontendNetwork

  #Frontend
  breddit-frontend:
    build: ./frontend
    container_name: breddit-frontend
    ports:
      - 80:80
    depends_on:
      - breddit-backend
    networks:
      - frontendNetwork

networks:
  backendNetwork:
  frontendNetwork:
```
### После запуска
1. Необходимо пройти процедуру регистрации:

![Registration](https://github.com/kkozlovsky/breddit/blob/master/assets/breddit_registration.jpg)

