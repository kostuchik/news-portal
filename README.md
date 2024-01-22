# News Portal 
The News Portal project is a Java-based web application that provides a feature-rich platform for managing and consuming news articles. The application is designed to offer users the ability to read, comment on, and contribute news content, along with various functionalities such as searching, media uploads, and profile customization.
## Stack
- Spring Boot v.3.1.4
- Spring Data JPA
- Spring Security + JWT
- PostgreSQL
- FlyWay
- Swagger
- Docker
  
## Includes 7 Java directories
- config - security configuration files;
- controllers - 5 controller classes;
- dto - a set of objects for data transfer;
- models - models of User, Comment, News, Role;
- repository - repositories for models;
- services - business logic;
- exception - classes for exception handling, validation, order cost calculation, and more;

## Deployment
There are several ways to run a Spring Boot application on your local machine. One way is to execute the docker-compose.yml file from your IDE.

```bash
  docker-compose up
```
Another way:
1. Build the project using `mvn clean install`
2. Run using `mvn spring-boot:run`
3. The web application is accessible via localhost:8080
4. Use username and password as 'admin' to login to demo (see below).
