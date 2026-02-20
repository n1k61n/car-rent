# 🚗 Car Rent

A modern, full-featured car rental platform built with Spring Boot 3, featuring real-time booking management, OAuth2 authentication, AI-powered chat support, and an intuitive admin dashboard.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-green.svg)
![Java](https://img.shields.io/badge/Java-17-orange.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Deployment](#-deployment)
- [API Endpoints](#-api-endpoints)
- [Default Credentials](#-default-credentials)
- [Screenshots](#-screenshots)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Features

### User Features
- 🔐 **Authentication** – Email/password login, OAuth2 (Google), password reset via OTP
- 🚗 **Car Browsing** – Search and filter cars by brand, model, category, price
- 📅 **Booking System** – Real-time availability, price calculation, booking history
- 📧 **Email Notifications** – Booking confirmations, reminders, OTP verification
- 👤 **User Profile** – Manage personal info, view bookings, upload documents
- 💬 **AI Chat Support** – Gemini-powered customer assistance
- 🌐 **Multi-language** – Azerbaijani and English support

### Admin Dashboard
- 📊 **Dashboard Analytics** – Statistics, charts, real-time metrics
- 🚗 **Car Management** – CRUD operations, image upload, feature configuration
- 📋 **Booking Management** – View, approve, cancel, complete bookings
- 👥 **User Management** – View users, manage roles and permissions
- 📝 **Blog Management** – Create and manage blog posts with comments
- ⭐ **Testimonials** – Manage customer reviews
- 👨‍💼 **Team Management** – Add/edit team members
- 💬 **Real-time Chat** – WebSocket-based customer support
- 🔔 **Notifications** – Real-time notification system

---

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| **Backend Framework** | Spring Boot 3.2.5 |
| **Language** | Java 17 |
| **Database** | PostgreSQL 16 |
| **ORM** | Spring Data JPA + Hibernate |
| **Security** | Spring Security + OAuth2 (Google) |
| **Template Engine** | Thymeleaf + Layout Dialect |
| **Validation** | Hibernate Validator |
| **Email** | Spring Mail + SendGrid |
| **Real-time** | WebSocket (STOMP) |
| **AI Integration** | Google Gemini API |
| **Build Tool** | Maven |
| **Containerization** | Docker + Docker Compose |
| **Monitoring** | Spring Boot Actuator |
| **Utilities** | Lombok, ModelMapper, JSOncify |

---

## 📁 Project Structure

```
car-rent/
├── src/main/java/com/example/carrent/
│   ├── config/              # Security, WebSocket, Locale configurations
│   ├── controllers/
│   │   ├── dashboard/       # Admin dashboard controllers
│   │   └── front/           # Public-facing controllers
│   ├── dtos/                # Data Transfer Objects
│   ├── enums/               # Role, BookingStatus enums
│   ├── exceptions/          # Custom exceptions
│   ├── handler/             # Auth handlers, UserDetailsService
│   ├── models/              # JPA Entities
│   ├── repositories/        # Spring Data repositories
│   ├── security/            # Global exception handler
│   └── services/            # Business logic layer
├── src/main/resources/
│   ├── static/              # CSS, JS, images
│   ├── templates/           # Thymeleaf templates
│   ├── application.properties
│   ├── messages.properties
│   └── messages_az.properties
├── docker-compose.yml
├── Dockerfile
└── pom.xml
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL 16
- Docker (optional, for containerized deployment)

### Option 1: Local Setup (Without Docker)

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/car-rent.git
   cd car-rent
   ```

2. **Set up PostgreSQL**
   ```sql
   CREATE DATABASE car_rent;
   ```

3. **Configure database credentials**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/car_rent
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   
   Open your browser and navigate to: `http://localhost:8080`

### Option 2: Docker Setup (Recommended)

1. **Start with Docker Compose**
   ```bash
   docker-compose up -d
   ```

2. **View logs**
   ```bash
   docker-compose logs -f app
   ```

3. **Stop the application**
   ```bash
   docker-compose down
   ```

---

## ⚙️ Configuration

### Environment Variables

| Variable | Description | Required | Default |
|----------|-------------|----------|---------|
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | No | `jdbc:postgresql://db:5432/cardb` |
| `SPRING_DATASOURCE_USERNAME` | Database username | No | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | No | `12345` |
| `SPRING_MAIL_USERNAME` | SMTP email address | No | - |
| `SPRING_MAIL_PASSWORD` | SMTP password | No | - |
| `SENDGRID_API_KEY` | SendGrid API key for emails | No | - |
| `GOOGLE_CLIENT_ID` | Google OAuth2 client ID | No | - |
| `GOOGLE_CLIENT_SECRET` | Google OAuth2 client secret | No | - |
| `GOOGLE_API_KEY` | Gemini AI API key | No | - |
| `PORT` | Server port | No | `8080` |
| `SENDGRID_ENABLED` | Enable SendGrid emails | No | `false` |

### Email Configuration (Gmail)

For email notifications, configure in `application.properties` or use environment variables:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

> **Note**: Use an [App Password](https://support.google.com/accounts/answer/185833) for Gmail, not your regular password.

---

## 🌐 Deployment

### Render Deployment

1. **Create a new Web Service** on [Render](https://render.com)

2. **Connect your Git repository**

3. **Configure build settings**:
   - **Build Command**: `mvn clean install`
   - **Start Command**: `java -jar target/carrent-0.0.1-SNAPSHOT.jar`

4. **Add PostgreSQL database**:
   - Go to Dashboard → New → PostgreSQL
   - Copy the internal database URL

5. **Set environment variables**:
   ```
   SPRING_DATASOURCE_URL=<your-render-postgres-url>
   SPRING_DATASOURCE_USERNAME=<username>
   SPRING_DATASOURCE_PASSWORD=<password>
   GOOGLE_CLIENT_ID=<your-client-id>
   GOOGLE_CLIENT_SECRET=<your-client-secret>
   SENDGRID_API_KEY=<your-sendgrid-key>
   ```

6. **Deploy** – Render will automatically build and deploy

### Docker Deployment

```bash
# Build the image
docker build -t car-rent .

# Run the container
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/car_rent \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=secret \
  car-rent
```

---

## 📡 API Endpoints

### Public Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/` | Home page |
| `GET` | `/listing` | Car listing with filters |
| `GET` | `/blog` | Blog posts |
| `GET` | `/about` | About page |
| `GET` | `/contact` | Contact page |
| `GET` | `/testimonials` | Customer reviews |
| `POST` | `/login` | User login |
| `GET` | `/register` | Registration page |
| `POST` | `/register` | User registration |
| `GET` | `/oauth2/authorization/google` | Google OAuth2 login |

### Protected Endpoints
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/dashboard` | Admin dashboard | ADMIN |
| `GET/POST` | `/dashboard/cars` | Car management | ADMIN |
| `GET/POST` | `/dashboard/bookings` | Booking management | ADMIN |
| `GET/POST` | `/dashboard/users` | User management | ADMIN |
| `GET/POST` | `/dashboard/blog` | Blog management | ADMIN |
| `GET/POST` | `/dashboard/team` | Team management | ADMIN |
| `GET/POST` | `/dashboard/testimonials` | Testimonial management | ADMIN |
| `GET` | `/dashboard/chat` | Real-time chat | ADMIN |
| `GET` | `/user/profile` | User profile | USER |
| `GET` | `/user/bookings` | User bookings | USER |

### WebSocket Endpoints
| Endpoint | Description |
|----------|-------------|
| `/ws-chat` | Real-time chat support |
| `/ws-notification` | Real-time notifications |

### Actuator Endpoints
| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Application health status |
| `/actuator/info` | Application information |
| `/actuator/metrics` | Application metrics |

---

## 🔑 Default Credentials

| Role | Email | Password |
|------|-------|----------|
| **Admin** | `admin@example.com` | `admin` |

> ⚠️ **Important**: Change default credentials immediately after deployment!

---

## 📸 Screenshots

> *Add screenshots of your application here*

- Home Page
- Car Listing
- Booking Form
- Admin Dashboard
- Car Management
- Real-time Chat

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the repository**
   ```bash
   git fork https://github.com/yourusername/car-rent.git
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```

3. **Commit your changes**
   ```bash
   git commit -m "Add amazing feature"
   ```

4. **Push to the branch**
   ```bash
   git push origin feature/amazing-feature
   ```

5. **Open a Pull Request**

### Development Guidelines
- Follow existing code style
- Write meaningful commit messages
- Add tests for new features
- Update documentation as needed

---

## 📄 License

This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.

---

## 📞 Support

For support, email **eminelxanoglu@gmail.com** or open an issue in the repository.

---

<p align="center">Made with ❤️ by <a href="https://github.com/eminelxanoglu">Emin Elxanoglu</a></p>
