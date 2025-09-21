# 🚗 DriveNow Car Rental System - Backend

## 📖 Project Description
The **DriveNow Backend** is a Spring Boot application that provides secure APIs for managing a **Car Rental System**.  
It handles authentication, user management, bookings, vehicles, and driver data, ensuring smooth communication between the frontend and the database.  

This service uses **JWT authentication** for security and is connected to a **MySQL database**.

---

## ✨ Features
- 🔐 **JWT-based Authentication** (Register, Login, Secure APIs)  
- 👤 Manage Users (Admin/User roles)  
- 🚘 Manage Vehicles & Drivers  
- 📅 Bookings with notifications  
- 📊 Generate Reports (JasperReports integration)  

---

## ⚙️ Technologies
- **Java 21**
- **Spring Boot** (Spring Web, Spring Data JPA, Spring Security)  
- **MySQL**  
- **JWT (io.jsonwebtoken)**  
- **Jakarta Mail** (for notifications)  
- **JasperReports** (for reports)  

---

## 🚀 Setup Instructions

### 1️⃣ Clone the Repository
git clone https://github.com/username/drivenow-backend.git

📌 **Frontend 
git clone https://github.com/username/drivenow-frontend.git


2️⃣ Configure Database

Update application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/drivenow_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

📌 Project Structure
src/main/java/org/example/drivenow_carrental_aad
│── controller    # REST Controllers
│── service       # Service interfaces
│── service/impl  # Service implementations
│── entity        # JPA Entities
│── repo          # Repositories (Spring Data JPA)
│── util          # Utility (JWT, etc.)

4️⃣ API Endpoints

POST /api/auth/register → Register user
POST /api/auth/login → Login & receive JWT
GET /api/users/me → Get logged-in user info
POST /api/bookings → Create booking
GET /api/admin/users → Admin: Manage users

🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss.
git clone https://github.com/your-username/drivenow-backend.git
cd drivenow-backend
