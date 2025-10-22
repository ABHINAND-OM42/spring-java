# **Full-Stack Application: React, Spring Boot (Java 17), & Oracle DB**

This repository contains a full-stack application leveraging a React frontend and a Spring Boot backend, both managed via Docker Compose, connecting to a separately running Oracle database container.

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
└── docker-compose.yml        \# Orchestrates the React and Spring Boot services

### **1.1 Spring Boot Database Configuration**

Since the Spring Boot application runs inside a Docker container, it needs a special hostname to reach the Oracle DB running on the host network. We will use host.docker.internal in the configuration.

Ensure your Spring Boot configuration (e.g., application.properties in backend/src/main/resources) is set up as follows:

\# Spring Boot Configuration Example  
\# NOTE: The host is set to host.docker.internal to reach the DB running on the host machine.  
spring.datasource.url=jdbc:oracle:thin:@host.docker.internal:1521/XEPDB1  
spring.datasource.username=AB42  
spring.datasource.password=ab42  
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver  
\# Optional: Hibernate settings for schema management  
spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true

## **2\. Oracle Database Setup (Standalone)**

The Oracle database will run as a separate Docker instance. The following steps must be completed **before** running Docker Compose.

### **2.1 Start the Oracle Container**

Run the Oracle XE container in detached mode. This command creates a persistent volume named oracle-data.

docker run \-d \--name oracle-db \\  
  \-p 1521:1521 \\  
  \-e ORACLE\_PASSWORD=your\_password \\  
  \-e ORACLE\_USER=your\_username \\  
  \-v oracle-data:/opt/oracle/oradata \\  
  gvenzl/oracle-xe:slim

### **2.2 Create/Verify Application User (AB42)**

You must wait a few minutes for the container to initialize, then connect to create the application user.

1. **Access the container's shell:**  
   docker exec \-it oracle-db bash

2. **Log in to SQL\*Plus as SYSDBA:**  
   sqlplus / as sysdba

3. **Set the Pluggable Database (PDB) context:**  
   ALTER SESSION SET CONTAINER=XEPDB1;

4. **Create User AB42 (or verify existence):**  
   If the user does not exist, run the commands below. If the user already exists, you can skip this step.  
   \-- Check if user exists  
   SELECT username FROM dba\_users WHERE username='AB42';

   \-- IF USER DOES NOT EXIST, run these commands:  
   CREATE USER AB42 IDENTIFIED BY ab42;  
   GRANT CONNECT, RESOURCE TO AB42;  
   ALTER USER AB42 QUOTA UNLIMITED ON USERS;

5. **Exit SQL\*Plus and the container shell:**  
   EXIT;

   exit

## **3\. Build and Run Application Containers**

The docker compose up \--build command will only build and run the Spring Boot and React application containers.

docker compose up \--build

### **Access Points**

| Service | Host Port | Purpose |
| :---- | :---- | :---- |
| **Frontend (React)** | http://localhost:3000 | User Interface |
| **Backend (Spring Boot)** | http://localhost:8080 | REST API |
| **Database (Oracle)** | localhost:1521 | Oracle DB Connection |

**Troubleshooting Connection:** If the Spring Boot container running inside Docker cannot connect to the Oracle container running on the host, host.docker.internal may not be supported (common on some Linux setups). In that case, replace host.docker.internal in your application configuration with your **host machine's actual IP address** on the Docker bridge network.