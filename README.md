# TaskManagement

[![Java CI with Maven](https://github.com/mizarion/TaskManagement/actions/workflows/maven.yml/badge.svg)](https://github.com/mizarion/TaskManagement/actions/workflows/maven.yml)

## How to run:

### Run db:

```shell
docker-compose up
```

### Run app:

```shell
mvn spring-boot:run
```

or from IDE [TaskManagementApplication.java](src%2Fmain%2Fjava%2Fcom%2Fmizarion%2Ftaskmanagement%2FTaskManagementApplication.java)


## Swagger

http://localhost:8080/swagger-ui/index.html

http://localhost:8080/v3/api-docs

## Description

The system should provide the ability to create, edit, delete and view tasks.
Each task should contain a title, description, status (e.g. pending, in progress, completed),
priority (e.g. high, medium, low), and the task author and assignee.
Only the API needs to be implemented.

1. The service must support user authentication and authorization by email and password.
2. API access must be authenticated using a JWT token.
3. Users can manage their tasks: create new ones, edit existing ones, view and delete, change the status and assign task
   executors.
4. Users can view other users' tasks, and task executors can change the status of their tasks.
5. You can leave comments on tasks.
6. The API must allow you to get tasks of a specific author or assignee, as well as all comments on them. It is
   necessary to provide filtering and pagination of the output.
7. The service must correctly handle errors and return understandable messages, as well as validate incoming data.
8. The service must be well documented. The API must be described using Open API and Swagger. Swagger UI must be
   configured in the service. It is necessary to write a README with instructions for running the project locally. The
   dev environment must be raised using docker compose.
9. Write several basic tests to check the main functions of your system.
10. Use Java 17+, Spring, Spring Boot to implement the system. PostgreSQL or MySQL can be used as a database

## Example:

### Register User:

http://localhost:8080/api/auth/register

```json
{
  "email": "user@example.com",
  "password": "password"
}
```

### Login (get JWT)

http://localhost:8080/api/auth/login

```json
{
  "email": "user@example.com",
  "password": "password"
}
```

### Create Task

http://localhost:8080/api/tasks

```json
{
  "header": "A",
  "description": "B",
  "status": "PENDING",
  "priority": "HIGH",
  "assigned": "assigned@example.com"
}
```

### Get Tasks

You can specify what you want using parameters:

* size
* page
* creator
* assigned

```http request
http://localhost:8080/api/tasks?size=10&page=0&assigned=assigned@example.com&creator=user@example.com
```

### Update task

http://localhost:8080/api/tasks/{taskid}

```json
{
  "header": "Hot fix",
  "description": "Investigate and fix the issue.",
  "status": "IN_PROGRESS",
  "priority": "HIGH",
  "creator": "creator@exaple.com",
  "assigned": "assigned@exaple.com"
}
```

### Delete task

http://localhost:8080/api/tasks/{taskid}

### Create Comment for task id

http://localhost:8080/api/{taskid}/comments

```json
...
```

### Get Comments for task id

http://localhost:8080/api/{taskid}/comments?size=10&page=0



