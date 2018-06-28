### Demonstrates Following 
* Spring-boot - Restful
* Redis - In-memory DB
* Rest-Assured - Integration rest testing
* Jenkinsfile - Pipeline as a code

##### Get enrolled courses by "Student2" 
<http://localhost:8080/students/Student2/courses>

### Running Tests

##### 1. Run only unit tests
```sh
mvn clean test
```

##### 2. Run only integration-test
```sh
mvn clean verify -P integration-test
```

##### rest-assured documentation
<https://github.com/rest-assured/rest-assured>

##### 3. Run both unit and integration tests
```sh
mvn clean install
```     

