# Auth Assignment

โปรเจกต์นี้เป็นแอปพลิเคชัน Spring Boot สำหรับระบบยืนยันตัวตนของผู้ใช้ (Authentication) ซึ่งรองรับการล็อกอิน การลงทะเบียน และการใช้งาน JWT เพื่อความปลอดภัย โดยเชื่อมต่อกับฐานข้อมูล MySQL เพื่อเก็บข้อมูลผู้ใช้

## ติดตั้ง
- Java version 21 หรือมากกว่า
- Maven หรือ Gradle (แนะนำ Maven)
- MySQLWorkbench
- Docker (สำหรับ run MySQLWorkbench)

## คำแนะนำในการติดตั้ง

## 1. Clone โปรเจกต์
git clone https://github.com/your-username/auth-assignment.git
cd auth-assignment

## 2. ตั้งค่าฐานข้อมูล MySQL
สร้างฐานข้อมูลชื่อ auth_assignment ใน MySQL

แก้ไขไฟล์ application.properties (อยู่ที่ src/main/resources/application.properties) ให้ตรงกับข้อมูลฐานข้อมูล

spring.datasource.url=jdbc:mysql://localhost:3306/auth_assignment

spring.datasource.username=user_auth

spring.datasource.password=password_auth

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

jwt.secret=supersecretkeythatshouldbelongerthan32charactersforbettersecurity (JWT Secret ต้องมีความยาวอย่างน้อย 32 ตัวอักษร)


## 3. Build โปรเจกต์ และ run application
รันคำสั่ง Maven เพื่อติดตั้ง dependencies และ build โปรเจกต์
mvn clean install
ใช้คำสั่งนี้เพื่อรัน Spring Boot
mvn spring-boot:run

## 4. ทดสอบ API<br>

### 4.1 หลังจากแอปพลิเคชันทำงานแล้ว ทดสอบ API ผ่าน postman

เลือก Body -> raw

✅ /auth/login

POST http://localhost:8080/auth/login

Request Body:

{

  "username": "your_username",
  
  "password": "your_password"
  
}

▶️ reponse: 

{

    "message": "User login successfully",

    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJob24iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE3NDU5NjA3MzksImV4cCI6MTc0NjA0NzEzOX0.Jm_BjekoCjnyKkVfifL_d12YCDWYJPKZ0mR6Y0nGj-w"
    
}

✅ /auth/register

POST http://localhost:8080/auth/register

{

  "username": "new_user",
  
  "password": "new_password",
  
  "confirmPassword": "new_password",
  
  "role": "USER"
  
}

▶️ response:

{

    "message": "User registered successfully",
    
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwb3IiLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE3NDU5NjA2MjgsImV4cCI6MTc0NjA0NzAyOH0.CNSEkQx7SH7ccC5ymmS0MtScjG-oBfmpYmK0kEOjvU4"
    
}

### 4.2 การทดสอบ Endpoint ที่มีการจำกัดสิทธิ์ (/user และ /admin)

หลังจากที่ผู้ใช้ ลงทะเบียน หรือ ล็อกอิน สำเร็จแล้ว ระบบจะส่ง JWT Token กลับมาใน response

นำ Token นั้นไปใช้ในการเข้าถึง endpoint ที่ต้องการ โดยแนบใน header Authorization -> Bearer <(ใส่ token ที่ได้มาตรงนี้)>

✅ /user Endpoint

GET http://localhost:8080/user

▶️ responce: Welcome USER: {username}

หาก เข้าถึง path GET http://localhost:8080/admin

▶️ responce: You do not have permission to access this resource.

✅ /admin Endpoint

GET http://localhost:8080/user

▶️ responce: Welcome USER: {username}

หาก เข้าถึง path GET http://localhost:8080/admin

▶️ responce: Welcome ADMIN: {username}
