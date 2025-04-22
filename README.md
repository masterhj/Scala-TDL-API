# Scala To-Do API 🚀

A production-ready To-Do list API built with:
- **Scala** (2.13)
- **Akka HTTP** (REST endpoints)
- **Slick** (Database access)
- **PostgreSQL** (Data persistence)

![API Demo](https://img.shields.io/badge/status-production%20ready-brightgreen) 
![Scala Version](https://img.shields.io/badge/scala-2.13.8-blue)
![License](https://img.shields.io/badge/license-MIT-green)

## Features ✨

- **CRUD Operations**:
  - Create, Read, Update, Delete todos
  - Mark todos as complete
- **RESTful API** with proper HTTP status codes
- **Database Integration** with PostgreSQL
- **JSON Serialization** using Spray JSON
- **Proper Error Handling**
- **Configuration Management** with Typesafe Config

## Prerequisites 📋

- Java 8+
- Scala 2.13.8
- sbt 1.6.2+
- PostgreSQL 12+
- (Optional) Docker for containerization

## Setup & Installation 🛠️

### 1. Database Setup

```sql
-- Create database
CREATE DATABASE tododb;

-- Connect to database
\c tododb

-- Create todos table
CREATE TABLE todos (
  id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  is_completed BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Create index for better performance
CREATE INDEX idx_todos_completed ON todos(is_completed);
