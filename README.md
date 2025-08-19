# 🎯 UserPoint Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-green?style=for-the-badge&logo=spring-boot)
![Spring AI](https://img.shields.io/badge/Spring_AI-1.0.1-blue?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=for-the-badge&logo=postgresql)
![Ollama](https://img.shields.io/badge/Ollama-AI-purple?style=for-the-badge&logo=ai)

*MCP Chatbot integrated points earning and usage project with AI-powered assistance*

</div>

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [API Documentation](#-api-documentation)
- [AI Chatbot](#-ai-chatbot)
- [Usage Examples](#-usage-examples)
- [Contributing](#-contributing)

## 🎯 Overview

UserPoint Management System is a modern Spring Boot application that combines traditional point management with AI-powered assistance. Users can earn, spend, and manage points while interacting with an intelligent chatbot powered by Spring AI and Ollama.

### Key Highlights

- 🏆 **Point Management**: Comprehensive system for earning and spending points
- 🎁 **Reward System**: Rich reward catalog with point-based redemption
- 🤖 **AI Assistant**: Spring AI-powered chatbot for natural language interactions
- 🔐 **Secure Authentication**: JWT-based security with role management
- 📊 **RESTful APIs**: Well-documented REST endpoints with Swagger UI
- 🐳 **Docker Ready**: Complete containerization support

## ✨ Features

### Core Features
- ✅ User registration and authentication
- ✅ Point earning and spending tracking
- ✅ Reward catalog management
- ✅ User reward redemption history
- ✅ Real-time point balance tracking

### AI Features
- 🤖 **Natural Language Queries**: Ask questions about your points and rewards in plain English
- 🔍 **Smart Point Analysis**: Get insights about your point history
- 🎯 **Reward Recommendations**: AI-powered suggestions based on your points
- 📈 **Usage Analytics**: Understand your point earning and spending patterns

### Security Features
- 🔐 JWT-based authentication
- 🛡️ Role-based access control
- 🔒 Secure password handling
- 🚫 CORS protection

## 🛠 Tech Stack

### Backend
- **Framework**: Spring Boot 3.2.5
- **Language**: Java 17
- **AI Framework**: Spring AI 1.0.1
- **Security**: Spring Security + JWT
- **Database**: PostgreSQL 15
- **ORM**: Spring Data JPA
- **AI Model**: Ollama (Mistral)

### Tools & Libraries
- **Documentation**: Swagger/OpenAPI 3
- **Validation**: Bean Validation
- **Containerization**: Docker & Docker Compose
- **Build Tool**: Maven

## 📋 Prerequisites

Before running this application, make sure you have:

- ☑️ **Java 17** or higher
- ☑️ **Maven 3.6+**
- ☑️ **Docker & Docker Compose**
- ☑️ **Git**

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd userpointmcp
```

### 2. Start Infrastructure Services

```bash
# Start PostgreSQL and Ollama
docker-compose up -d

# Wait for services to be ready (about 30 seconds)
```

### 3. Download AI Model

```bash
# Pull Mistral model for Ollama
docker exec userpoint-ollama ollama pull mistral
```

### 4. Build and Run Application

```bash
# Build the application
mvn clean package

# Run the application
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

## ⚙️ Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | `jdbc:postgresql://localhost:5432/userpoint` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `userpoint` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `userpoint123` |
| `JWT_SECRET` | JWT signing secret | `mySecretKey...` |
| `JWT_EXPIRATION` | JWT expiration time (ms) | `86400000` |
| `SPRING_AI_OLLAMA_BASE_URL` | Ollama service URL | `http://localhost:11434` |

### Database Setup

The application automatically creates the database schema on startup. Initial data includes:
- Sample rewards catalog
- Test user accounts
- Example point transactions

## 📚 API Documentation

### Swagger UI
Access the interactive API documentation at: `http://localhost:8080/swagger-ui.html`

### Main Endpoints

#### Authentication
```
POST /api/auth/register    # Register new user
POST /api/auth/login       # User login
```

#### Points Management
```
GET    /api/points/balance         # Get user point balance
POST   /api/points/earn           # Record point earning
POST   /api/points/spend          # Record point spending
GET    /api/points/history        # Get transaction history
```

#### Rewards
```
GET    /api/rewards               # List available rewards
POST   /api/rewards/{id}/redeem   # Redeem a reward
GET    /api/rewards/user-rewards  # Get user's redeemed rewards
```

#### AI Chatbot
```
POST   /api/chat/message          # Chat with AI assistant
```

## 🤖 AI Chatbot

The AI chatbot is powered by Spring AI and Ollama, providing natural language interaction with your point system.

### Capabilities

#### Point Queries
```
"How many points do I have?"
"Show me my point history"
"When did I last earn points?"
```

#### Reward Information
```
"What rewards can I afford?"
"Tell me about available rewards"
"How much do I need for [reward name]?"
```

#### Smart Analytics
```
"How am I doing with my points this month?"
"What's my spending pattern?"
"Suggest rewards for me"
```

### Tool Integration

The chatbot has access to:
- 🔧 **PointToolService**: Point balance and transaction queries
- 🎁 **RewardToolService**: Reward information and recommendations
- 👤 **UserToolService**: User account and profile information

## 💡 Usage Examples

### Register and Login
```bash
# Register a new user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "password123"
  }'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "password123"
  }'
```

### Earn Points
```bash
curl -X POST http://localhost:8080/api/points/earn \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 100,
    "description": "Completed daily task"
  }'
```

### Chat with AI
```bash
curl -X POST http://localhost:8080/api/chat/message \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "message": "How many points do I have and what can I redeem?"
  }'
```

## 🏗️ Project Structure

```
src/main/java/com/userpoint/
├── 📁 config/          # Configuration classes
├── 📁 controller/      # REST controllers
├── 📁 dto/            # Data Transfer Objects
├── 📁 entity/         # JPA entities
├── 📁 repository/     # Data repositories
├── 📁 security/       # Security configuration
└── 📁 service/        # Business logic
    └── 📁 tools/      # AI tool services
```

## 🔧 Development

### Running Tests
```bash
mvn test
```

### Code Quality
The project follows Spring Boot best practices:
- ✅ Layered architecture
- ✅ Dependency injection
- ✅ Exception handling
- ✅ Input validation
- ✅ Security implementation

## 🐳 Docker Deployment

### Production Deployment
```bash
# Build application image
docker build -t userpoint-app .

# Run complete stack
docker-compose -f docker-compose.prod.yml up -d
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

<div align="center">

**Made with ❤️ using Spring Boot and Spring AI**

*For questions or support, please open an issue*

</div>
