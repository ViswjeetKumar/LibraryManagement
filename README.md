# Library Management System (Your Library)

## Description
A Java-based Library Management System with a Swing UI, allowing users to add, view, and remove books from a MySQL database.

## Prerequisites
- Java 8 (e.g., Amazon Corretto 1.8.0_462)
- MySQL Server with 'library_db' database
- MySQL Connector JAR (mysql-connector-j-9.3.0)

## Setup
1. Clone the repository: `git clone https://github.com/ViswjeetKumar/LibraryManagement.git`
2. Import the project into IntelliJ IDEA.
3. Configure the MySQL connection in `BookDAO.java` with your credentials.
4. Run the `LibraryUI.java` file.

## Features
- Add new books with ISBN, title, author, and year.
- View all books in a list.
- Remove books by ISBN.
