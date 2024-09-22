# Data Conversion API

## Overview

The *Data Conversion API* is a Spring Boot application that allows users to upload CSV and converts them into JSON format. This project aims to automate the process of file conversion for easy data handling and manipulation.

## Features

- *CSV to JSON Conversion*: Upload CSV files and convert them into JSON format.
- *Swagger Integration*: Use Swagger UI for easy API testing and interaction.
- *Supports Large File Uploads*: Handles file uploads up to 1000MB.

##Key Highlights:
1. Multipart File Handling: Controller and Service layers handle both CSV and Excel files, converting them into JSON format.
2. CSV Parsing: Using OpenCSV to parse CSV files is efficient and straightforward.
3. Excel Parsing: Handling Excel files with Apache POI.

## Technologies Used

- *Java 17*
- *Spring Boot 3.3.4*
- *OpenCSV* (for CSV file handling)
- *Swagger UI* (for API documentation)

## Prerequisites

- Java 17 or later
- Maven 3.x
- Internet connection (for downloading dependencies)

## Getting Started

### Step 1: Clone the Repository

git clone <https://github.com/ManjulaDoddavula/FILEDATACONVERSION>
cd <repository_directory>

Step 2: Build the Project

mvn clean install
Step 3: Run the Application

mvn spring-boot:run

The API will be available at http://localhost:8089.

Step 4: Access Swagger UI

Open your browser and navigate to http://localhost:8089/swagger-ui.html to access the Swagger UI, which provides an interactive interface for testing the API endpoints.