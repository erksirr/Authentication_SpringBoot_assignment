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
### 4.1 หลังจากแอปพลิเคชันทำงานแล้ว ทดสอบ API ผ่าน postman<br>
เลือก Body -> raw<br>
✅ /auth/login<br>
POST http://localhost:8080/auth/login<br>
Request Body:<br>
{<br>
  "username": "your_username",<br>
  "password": "your_password"<br>
}<br>
▶️ reponse: token : {token}<br>
✅ /auth/register<br>
POST http://localhost:8080/auth/register<br>
{<br>
  "username": "new_user",<br>
  "password": "new_password",<br>
  "confirmPassword": "new_password",<br>
  "role": "USER"<br>
}<br>
▶️ response: User registered successfully token : {token}<br>
### 4.2 การทดสอบ Endpoint ที่มีการจำกัดสิทธิ์ (/user และ /admin)<br>
หลังจากที่ผู้ใช้ ลงทะเบียน หรือ ล็อกอิน สำเร็จแล้ว ระบบจะส่ง JWT Token กลับมาใน response<br>
นำ Token นั้นไปใช้ในการเข้าถึง endpoint ที่ต้องการ โดยแนบใน header Authorization -> Bearer <(ใส่ token ที่ได้มาตรงนี้)><br>
✅ /user Endpoint<br>
GET http://localhost:8080/user<br>
▶️ responce: Welcome USER: {username}<br>
หาก เข้าถึง path GET http://localhost:8080/admin<br>
▶️ responce: You do not have permission to access this resource.<br>
✅ /admin Endpoint<br>
GET http://localhost:8080/user<br>
▶️ responce: Welcome ADMIN: {username}<br>
หาก เข้าถึง path GET http://localhost:8080/admin<br>
▶️ responce: Welcome ADMIN: {username}<br>
