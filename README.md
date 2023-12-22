
# BLOG API - SPRING BOOT
## Overview
This project is a RESTful API for a simple blog application built using Spring Boot, MySQL,  JWT token authentication and Docker. The API provides endpoints for managing blog posts, user authentication, and authorization.

## Table of Contents
1. [Requirements](#requirements)
2. [Setup](#setup)
    - [MySQL Database](#mysql-database)
3. [API Endpoints](#api-endpoints)
4. [Usage](#usage)
5. [Contributing](#contributing)

## Requirements

- Java 11 or higher
- MySQL
- Maven
- Docker 3.8

## Setup
### DOCKER
1. **Build and run the services:**
Open a terminal and navigate to the directory containing the `docker-compose.yml` file. Run the following command:
```bash
docker-compose up
```
2. **Check the status of the containers:**
You can check whether the containers are running or not using the following command:
```bash
docker-compose ps
```
3. **Check logs (if needed):**
To view the logs of the containers, you can use the command:
```bash
docker-compose logs
```
To view logs for a specific service (e.g., `server`), you can run:
```bash
docker-compose logs server
```
You can check whether the containers are running or not using the following command:
### MySQL Database
1. Create a MySQL database for the blog application. Use the given sql file at  `_src/BlogDatabase.sql_`
2. Configure the database connection in the application.properties file.
```properties 
    spring.datasource.url=jdbc:mysql://mysqldb:3306/blog
    spring.datasource.username=root
    spring.datasource.password=your_password
```

## API Endpoints
API Documents available at `localhost:8080/swagger-ui/index.html`, `localhost:8080/v3/api-docs`

## Usage
### 
1. Build the project
```bash
    ./mvnw clean install
```
2. Run the application
```bash
    java -jar target/blog-api.jar
```

## Contributing
Feel free to contribute to the project. Fork the repository, make your changes, and submit a pull request.
