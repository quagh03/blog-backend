# BLOG API - SPRING BOOT
## Overview
This project is a RESTful API for a simple blog application built using Spring Boot, MySQL, and JWT token authentication. The API provides endpoints for managing blog posts, user authentication, and authorization.

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

## Setup
### MySQL Database
1. Create a MySQL database for the blog application. Use the given sql file at  `_src/BlogDatabase.sql_`
2. Configure the database connection in the application.properties file.
```properties 
    spring.datasource.url=jdbc:mysql://localhost:3306/blog
    spring.datasource.username=your_username
    spring.datasource.password=your_password
```

## API Endpoints
API Documents available at `localhost:8080/swagger-ui/index.html`, `localhost:8080/v3/api-docs`

## Usage
1. Build the project
```command
    mvn clean install
```
2. Run the application
```command
    java -jar target/blog-api.jar
```

## Contributing
Feel free to contribute to the project. Fork the repository, make your changes, and submit a pull request.