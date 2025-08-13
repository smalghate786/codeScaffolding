# codeScaffolding
 This process helps developers quickly set up the foundational elements of an application, reducing the need for manual creation of repetitive code and files.
 Example API Call

# Endpoint 
POST http://localhost:8080/api/scaffold/crud
Content-Type: application/json

# Request Body

{
  "entityName": "Customer",
  "packageName": "com.example.demo",
  "fields": [
    {"name": "id", "type": "Long"},
    {"name": "name", "type": "String"},
    {"name": "email", "type": "String"}
  ]
}

#Example with curl
curl -X POST http://localhost:8080/api/scaffold/crud \
     -H "Content-Type: application/json" \
     -d '{
           "entityName": "Customer",
           "packageName": "com.example.demo",
           "fields": [
             {"name": "id", "type": "Long"},
             {"name": "name", "type": "String"},
             {"name": "email", "type": "String"}
           ]
         }' \
     --output Customer-crud.zip


 # 🛠 Spring Boot CRUD Scaffolder API

A Spring Boot-based automation tool that generates **Entity**, **Repository**, **Service**, and **Controller** source code for any Java class from a single API request.

It outputs the generated files in a **.zip** file, ready to drop into your project — saving hours of repetitive boilerplate coding.

---

## 🚀 Features
- Generate **CRUD layer** for any entity:
  - Entity class (`@Entity`)
  - Repository interface (`JpaRepository`)
  - Service class
  - REST Controller
- Fully customizable **package name** and **fields**
- Output as a **.zip** file
- JSON request-based — can be integrated with any tool or frontend

---

## 📦 Requirements
- **Java 17+**
- **Maven 3+**
- Internet access (to download dependencies)

---

## 🔧 Setup & Run
Clone the repo and run:
```bash
mvn spring-boot:run

