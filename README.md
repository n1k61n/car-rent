# Car Rent

This is a car rental application.

## Local Setup

1.  **Database Setup:**
    *   Install PostgreSQL.
    *   Create a database named `car_rent`.
    *   Update the `spring.datasource.username` and `spring.datasource.password` in `src/main/resources/application.properties` with your PostgreSQL credentials.

2.  **Environment Variables:**
    *   No specific environment variables are required for local setup beyond the database credentials in `application.properties`.

3.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```

## Render Deployment

For deployment on Render, you can use the provided `Dockerfile` and `docker-compose.yml`.

1.  Create a new Web Service on Render.
2.  Connect your Git repository.
3.  Set the build command to `mvn clean install`.
4.  Run the application on docker compose up -d
4.  Set the start command to `java -jar target/carrent-0.0.1-SNAPSHOT.jar`.
5.  Add a PostgreSQL database to your Render project.
6.  Set the `DATABASE_URL` environment variable to your Render PostgreSQL connection string.

## Admin Login

*   **Username:** admin@example.com
*   **Password:** admin

---
This is a simplified setup guide. For more detailed information, please refer to the project's source code.
