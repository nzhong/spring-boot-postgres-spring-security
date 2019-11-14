# Spring-Boot, PostgreSQL, Spring-Security

1: setup database

please refer to database/README.md 


2: build java code

```
mvn clean package
java -jar target/spring-boot-postgres-1.0-SNAPSHOT.jar
```


3: see it in action

- point your browser to http://127.0.0.1:8080/api/v1/hello
- per our policy in src/main/java/com/learn/springboot/server/SecurityConfig.java, anything under /api/v1/* needs authentication. so we will see a login prompt.
- username: seedUser   password: seedPassword  
(seeded in src/main/java/com/learn/springboot/server/postConstruct/PostConstructWork.java)
- now http://127.0.0.1:8080/api/v1/hello should load just fine.