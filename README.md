![Deploy to Amazon ECS](https://github.com/andersonbco/customer-service/workflows/Deploy%20to%20Amazon%20ECS/badge.svg)

# customer-service
A simple CRUD made with Spring Boot

#### How to run the project
###### Requirements
- Java 14
- Gradle 6.5

If you have the correct versions of both Java and Gradle installed on your system you can run the `bootRun` task from Gradle.

Alternatively, you can use the `Dockerfile` provided to run the project in a container.

Once the application is up and running, the swagger will be available at `localhost:8080/swagger-ui.html`.

Here's an example of a `POST` request that will create a sample customer:

```shell script
curl --location --request POST 'localhost:8080/customer' \
 --header 'Content-Type: application/json' \
 --data-raw '{
     "name": "John Doe",
     "cpf": "12345678912",
     "addresses": [
         {
             "number": "71",
             "streetAddress": "Scott Street",
             "city": "No Man'\''s Land",
             "state": "RJ",
             "zipCode": "123456"
         }
     ]
 }'
```
 
 
 #### *_TO DO_*
 
 - add more unit tests
 - implement the update endpoint
 - fix the ECS pipeline
 - configure logback/sl4j
 - improve swagger/documentation
 