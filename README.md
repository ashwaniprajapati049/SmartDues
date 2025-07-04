# SmartDues Portal

SmartDues is a web-based No-Dues Management System designed for colleges to streamline the dues clearance process for students and departments. The portal facilitates secure and user-friendly access for both Admin and Students, ensuring transparency and ease in managing dues.

## 🔧 Tech Stack

- **Backend**: Java, Spring Boot, Spring Data JPA, REST APIs  
- **Frontend**: HTML, CSS, JavaScript (can be integrated with Angular/React)
- **Database**: MySQL  
- **IDE**: IntelliJ IDEA  
- **Build Tool**: Maven  
- **Version Control**: Git, GitHub  

---

## ✨ Features

### 👨‍🎓 Student Module
- Student Login via Roll Number
- View pending dues
- Upload receipt/screenshot for payment
- View approval status
- Select Year and Semester while uploading receipt

### 👨‍💼 Admin Module
- Admin login (ID: `Radharaman`, Password: `Radharaman@12`)
- Add/View/Edit/Delete student records
- Add/View/Edit/Delete departments and dues
- Approve or Reject student receipts
- Track dues clearance status per department

---

## 📁 Folder Structure (Spring Boot)
SmartDuesPortal/
├── src/
│ ├── main/
│ │ ├── java/com/smartdues/
│ │ │ ├── controller/
│ │ │ ├── entity/
│ │ │ ├── repository/
│ │ │ ├── service/
│ │ │ └── SmartDuesPortalApplication.java
│ │ └── resources/
│ │ ├── application.properties
│ │ └── static/templates/
├── pom.xml
└── README.md


---

## ⚙️ Configuration

### `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smartdues_db
spring.datasource.username=root
spring.datasource.password=Ashwani@12
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

