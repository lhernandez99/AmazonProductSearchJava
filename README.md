# Amazon Product Search

> **Work in Progress:** This project is actively being developed and improved.

## Overview

Amazon Product Search is a full-stack web application built with a Java backend (using Spark framework) and a React frontend. It allows users to search through a catalog of products with filters such as price range, rating, and category.

This project demonstrates:

- Building a REST API server in Java to serve product data.
- Creating a React frontend that consumes the API with dynamic search and filtering.
- Handling asynchronous data fetching and rendering results.
- Using CSV data as the product source.

---

## Features

- Search products by keywords.
- Filter by minimum/maximum price.
- Filter by minimum rating.
- Filter by category.
- Responsive UI with loading states and error handling.

---
<img width="633" height="207" alt="image" src="https://github.com/user-attachments/assets/b1dc99d4-39df-4e5c-b8c7-c6a9f5fb0ef4" />

## Getting Started

### Prerequisites

- Java 17 or later
- Maven or your preferred build tool (for Java dependencies)
- Node.js and npm (for React frontend)

### Running the Backend

1. Navigate to the project root.
2. Compile and run the Java server:

   ```bash
   javac -cp "lib/*" -d bin src/main/*.java
   java -cp "bin;lib/*" main.Server
