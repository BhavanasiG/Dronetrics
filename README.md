# Dronetrics
Dronetrics is a webapp aimed at making data and metrics in Drone CI more accessible

## Running the application
The application can be run either fully containerised or with only the Redis and PostgreSql containerised.

### Running fully containerised
To run the application fully containerised run all the services in the `docker-compose.yaml` file.
Note: when running fully containerised you will need to delete and recreate the backend and/or frontend containers depending on if you make backend and/or frontend code changes.

### Running partially containerised
If you are doing local development, this is preferred and recommend method of running.
To run partially containerised, run the `PDE Start` compound run configuration.
- This creates and runs the required PostgreSql and Redis services as Docker containers
- But runs the Vite Vue.js frontend and Java 26 Spring Boot backend directly locally (uncontainerised)

### Running uncontainerised
This is no longer possible as we make use of PostgreSql and Redis in-memory cache, so these services need to be running, which we achive by running containerised versions of these services.
