# springboot-ecommerce

# E-Commerce API

This project is a backend API for an e-commerce application built using Spring Boot. The project allows to browse products, manage shopping carts, place orders, and manage authentication through JWT. 

## Features

- **User Authentication:**
  - Sign up, login, and JWT-based authentication.
  
- **Product Management:**
  - CRUD operations for products and categories.

- **Shopping Cart:**
  - Add, update, remove products from the cart.
  
- **Order Management:**
  - Place an order, view order history, and track order status.
  
- **Security:**
  - Secured API endpoints with JWT authentication.
  - Role-based access for different user types.

## Technologies Used

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **Hibernate**
- **MySQL**
- **Maven**

## Project Structure

```bash
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   └── com
│   │       └── project
│   │           └── miniecommerce
│   │               ├── controller         # API Controllers
│   │               ├── entity             # JPA Entities
│   │               ├── exception          # Custom exceptions
│   │               ├── model              # Request and response models
│   │               ├── repository         # Repository interfaces
│   │               ├── security           # JWT & Security config
│   │               └── service            # Business logic services
│   └── resources
│       └── application.properties         # Application configuration


### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/rehann888/springboot-ecommerce.git

   ```

2. Navigate to the project directory:

   ```bash
   cd springboot-ecommerce

   ```

3. Configure the database:
   Update the `application.properties` file in `src/main/resources` 

4. Build the project:

   ```bash
   mvn clean install

   ```

5. Run the application:
   ```bash
   mvn spring-boot:run
   ```

Usage

- Visit `http://localhost:8080` in your browser.
