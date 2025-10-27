Spark Java Collectibles API - Sprint 1
📌 Overview
This project is the Sprint 1 deliverable for the Digital NAO Challenge: "Spark in Java Web Application Development".

The primary goal of this sprint was to build the foundational backend API service for an online collectible items store. This service uses Java 17 and the lightweight Spark Java framework to handle web requests.

Key accomplishments for this sprint include:

Setting up a Maven project structure.

Integrating core dependencies: Spark Java, Gson (for JSON), and Logback (for logging).

Defining and implementing API routes (endpoints) for basic User management (CRUD operations + OPTIONS).

Using an in-memory data structure (ConcurrentHashMap) to simulate a database for rapid development.

🛠️ Technologies & Tools Used (Sprint 1)
This project leverages the following technologies:

Core Language:

☕ Java 17: The main programming language.

Web Framework:

✨ Spark Java (v2.9.4): A micro-framework used to quickly define web routes (endpoints).

JSON Handling:

🔄 Gson (v2.10.1): A library from Google used to convert Java Objects (like our User class) into JSON strings and vice-versa.

Logging:

📝 Logback (v1.4.14) via SLF4J (v2.0.9): Standard logging framework to output information and errors during application runtime.

Build & Dependency Management:

🧩 Apache Maven: Tool used to manage project dependencies (the libraries we need) and build the application.

Development Environment:

💻 IDE: Visual Studio Code (or any preferred Java IDE).

📦 JDK: Java Development Kit version 17.

API Testing:

📬 Postman (Recommended): Used to send HTTP requests to test the API endpoints.

▶️ How to Run the Project
Follow these steps to get the application running locally:

Prerequisites:

Ensure you have JDK 17 and Apache Maven installed on your system.

You'll need Git to clone the repository.

Clone the Repository:

git clone <Your-Repository-URL>
cd spark-collectibles-api

(Replace <Your-Repository-URL> with your actual repository URL)

Compile and Run with Maven:
Navigate to the project's root directory (spark-collectibles-api which contains the pom.xml) in your terminal and run:

# This command cleans previous builds, compiles the code, and runs the main class
mvn clean compile exec:java -Dexec.mainClass="com.ramoncollectibles.Main"

Application is Running:
If successful, you will see log messages indicating the server has started, typically ending with:

Spark routes defined. Server running on http://localhost:8080

The API is now accessible at http://localhost:8080.

📡 API Endpoints (Sprint 1: Users)
You can interact with the API using tools like Postman:

Method

Path

Description

Request Body (JSON Example)

Success Response

Failure Response (e.g.)

GET

/users

Retrieve all users

(None)

200 OK + User List

500 Internal Error

GET

/users/{id}

Retrieve a specific user by ID

(None)

200 OK + User

404 Not Found

POST

/users

Add a new user

{"name": "Jane Doe", "email": "jane@email.com"}

201 Created + User

400 Bad Request

PUT

/users/{id}

Update an existing user by ID

{"name": "Jane Smith", "email": "jane@new.com"}

200 OK + User

404 Not Found

OPTIONS

/users/{id}

Check if a user with the ID exists

(None)

200 OK

404 Not Found

DELETE

/users/{id}

Delete a specific user by ID

(None)

204 No Content

404 Not Found

(Note: For POST and PUT, the Content-Type header should be application/json)

🧠 Key Decisions & Justification (Sprint 1)
Framework Choice: Spark Java was used as mandated by the challenge. Its simplicity allowed for rapid definition of RESTful routes with minimal boilerplate code, focusing on the core API logic.

In-Memory Data Store: A ConcurrentHashMap within UserService acts as a temporary, in-memory database. This approach was chosen for Sprint 1 to expedite development by avoiding the setup and configuration of a persistent database, allowing focus on the API layer itself.

JSON Library: Gson was selected for its ease of use in converting Java objects to JSON (toJson) and JSON request bodies back to Java objects (fromJson).

API Design: Standard REST conventions were followed for defining endpoints (HTTP verbs, resource paths). Notably, POST /users was implemented for creating users instead of POST /users/:id (as suggested in the prompt) because creating a resource is typically done on the collection path in REST.

Error Handling: Basic error handling (404 Not Found, simple 500 Internal Error JSON) was implemented. More robust, centralized error handling will be addressed in future sprints.