# **Full-Stack Application: React, Spring Boot (Java 17), & Oracle DB**

This repository contains a full-stack application leveraging a React frontend, a Spring Boot backend, and a containerized Oracle database, all managed via Docker Compose.

## **Prerequisites**

Before starting, ensure you have the following software installed:

* **Java 17+** (For the Spring Boot Backend)  
* **Node.js / npm** (For the React Frontend)  
* **Docker** and **Docker Compose** (For containerization)

## **1\. Project Structure and Configuration**

Ensure your project structure includes the following directories and files:

.  
├── backend/                  \# Your Spring Boot project files  
│   ├── src/  
│   ├── pom.xml  
│   └── Dockerfile            \# Required for Docker Compose build  
├── frontend/                 \# Your React project files  
│   ├── src/  
│   ├── package.json  
│   └── Dockerfile            \# Required for Docker Compose build  
└── docker-compose.yml        \# Orchestrates all services (provided below)

### **1.1 Spring Boot Database Configuration**

The backend application connects to the Oracle container using the service name defined in docker-compose.yml (oracle-db).

Ensure your Spring Boot configuration (e.g., application.properties in backend/src/main/resources) is set up as follows:

\# Spring Boot Configuration Example  
spring.datasource.url=jdbc:oracle:thin:@oracle-db:1521/XEPDB1  
spring.datasource.username=AB42  
spring.datasource.password=ab42  
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver  
\# Optional: Hibernate settings for schema management  
spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true

## **2\. Oracle Database Setup**

The Oracle database container is defined in the docker-compose.yml file. The following steps guide you through creating the dedicated application user (AB42) inside the database.

### **2.1 Start the Oracle Container (Initial Run)**

Start *only* the Oracle service to configure the user.

docker compose up \-d oracle-db

### **2.2 Create/Verify Application User (AB42)**

You will now connect to the running Oracle container and use SQL to manage users.

1. **Access the container's shell:**  
   docker exec \-it oracle-db bash

2. **Log in to SQL\*Plus as SYSDBA:**  
   sqlplus / as sysdba

3. **Set the Pluggable Database (PDB) context:**  
   ALTER SESSION SET CONTAINER=XEPDB1;

4. **Check for existing user AB42:**  
   SELECT username FROM dba\_users WHERE username='AB42';

5. Option A: User Exists  
   If the user already exists, you can verify its status and quotas:  
   SELECT username, default\_tablespace, account\_status   
   FROM dba\_users   
   WHERE username='AB42';

   SELECT username, tablespace\_name, bytes, max\_bytes  
   FROM dba\_ts\_quotas  
   WHERE username='AB42';

6. Option B: User Does NOT Exist (Create New User)  
   If the SELECT query returns no rows, create the user with necessary permissions:  
   CREATE USER AB42 IDENTIFIED BY ab42;  
   GRANT CONNECT, RESOURCE TO AB42;  
   ALTER USER AB42 QUOTA UNLIMITED ON USERS;

7. **Exit SQL\*Plus and the container shell:**  
   EXIT;

   exit

## **3\. Build and Run the Full Stack**

Stop the single DB container, and then use the main command to build and run all services together.

\# Optional: Stop and remove the single DB container started earlier  
docker compose down

\# Build images and start all services (Backend, Frontend, DB)  
docker compose up \--build

### **Access Points**

| Service | Host Port | Purpose |
| :---- | :---- | :---- |
| **Frontend** | http://localhost:3000 | User Interface |
| **Backend** | http://localhost:8080 | REST API |
| **Database** | localhost:1521 | Oracle DB Connection |

**Troubleshooting Connection:** If the Spring Boot container cannot connect to the Oracle container, ensure the hostname in your application.properties (oracle-db) correctly matches the service name in the docker-compose.yml. If running the Spring Boot app directly on the host machine, replace oracle-db in the connection URL with your **host machine's IP address**.