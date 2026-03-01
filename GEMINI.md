/# 🚗 Car Rent - Project Context

## Project Overview

**Car Rent** is a comprehensive, modern car rental platform built using **Java 17** and **Spring Boot 3.2.5**. It provides both a public-facing website for customers to browse and book cars, and a secure admin dashboard to manage inventory, bookings, users, and content.

**Key Technologies:**
*   **Backend:** Spring Boot (Web, Data JPA, Security, Mail, Actuator, WebSocket)
*   **Database:** PostgreSQL 16 (with H2 for testing/runtime scope)
*   **Frontend/Views:** Thymeleaf (with Layout Dialect), Bootstrap, jQuery, custom CSS/JS.
*   **Security:** Spring Security with traditional email/password and Google OAuth2 integration.
*   **AI Integration:** Google Gemini AI API (used for an AI Assistant chat, OCR document extraction, and dynamic pricing suggestions).
*   **Other Tools:** Lombok, ModelMapper, Docker/Docker Compose.

## Architecture & Structure

The project follows a standard layered Spring Boot architecture:
*   `src/main/java/com/example/carrent/`:
    *   `controllers/`: Separated into `dashboard` (admin) and `front` (public) REST/MVC controllers. Recently added `api/AiController.java` for frontend AJAX calls.
    *   `services/` & `services/impl/`: Business logic. Includes services for standard CRUD operations and external integrations (e.g., `GeminiService`, `SendGridEmailService`).
    *   `models/`: JPA Entities (e.g., `Car`, `Booking`, `User`).
    *   `repositories/`: Spring Data JPA interfaces.
    *   `dtos/`: Data Transfer Objects for passing data between layers.
    *   `config/`: Application configurations (Security, WebSocket).
*   `src/main/resources/`:
    *   `application.properties`: Main configuration file (DB credentials, Mail, OAuth2, Gemini API Key).
    *   `templates/`: Thymeleaf HTML templates, split into `dashboard` and `front`.
    *   `static/`: Static assets (CSS, JS, images, fonts).
*   `uploads/licenses/`: Directory for storing uploaded driving licenses.

## Building and Running

### Prerequisites
*   Java 17+
*   Maven
*   PostgreSQL running (default port 5432, db name `cardb` or `car_rent`) or Docker.

### Key Commands

*   **Compile the project:**
    ```bash
    ./mvnw compile
    ```
*   **Run locally:**
    ```bash
    ./mvnw spring-boot:run
    ```
*   **Run with Docker Compose (Database + App):**
    ```bash
    docker-compose up -d
    ```

**Environment Variables Required for Full Functionality:**
*   `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`
*   `SPRING_MAIL_USERNAME`, `SPRING_MAIL_PASSWORD`
*   `GOOGLE_CLIENT_ID`, `GOOGLE_CLIENT_SECRET`
*   `GOOGLE_API_KEY` (Crucial for AI Chat, OCR, and Pricing features)
*   `SENDGRID_API_KEY`

## Development Conventions

*   **Dependency Management:** Maven (`pom.xml`).
*   **Boilerplate:** Heavy reliance on **Lombok** (`@Getter`, `@Setter`, `@RequiredArgsConstructor`) to reduce boilerplate code in models and services.
*   **Templating:** The UI relies on **Server-Side Rendering (SSR)** using Thymeleaf. JavaScript is sprinkled in the templates (`<script>` tags) or included from the `static/` folder for interactive features (like AJAX calls to the `/api/ai/*` endpoints).
*   **Validation:** Use `jakarta.validation.constraints` annotations on Entities/DTOs.
*   **AI Integrations:** New AI functionalities are wrapped inside `GeminiService` and exposed either directly in server-rendered flows (like `ChatServiceImpl`) or as REST APIs (`AiController`) consumed by frontend JS `fetch()` calls.