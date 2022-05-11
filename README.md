# Roles service

## My solution
To solve this problem I used the following steps:
1. Mapped the entities (role and membership)
2. Created the db scripts
3. Created an empty spring project
4. Added Spring Data JPA and some other dependencies for fast development
5. Created the endpoints while writing the tests
6. Did some manual testing (using Postman)

### Why I created a role entity/table?
We have default roles in the application. But more can be created

## Running the code
1. Build with maven
2. Deploy a container with postgres on port 5432
3. Run the application using dev profile
4. Or just run make run-all and it will run the application on a container for you

Check the [docker-compose.yml](docker-compose.yml) file for more details

## Improvements on existing services
For now, I would just add pagination to `/users` and `/teams` endpoints.