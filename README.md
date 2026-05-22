# Employee Record Management System

## Project Overview

The Employee Record Management System is a Java-based application developed for ICT711 Programming and Algorithms. The system was designed to manage employee records efficiently using both a Graphical User Interface (GUI) and a Text-Based Interface (TBI).

The project demonstrates the implementation of object-oriented programming principles, searching and sorting algorithms, modular software design, exception handling, file persistence, and automated software testing using JUnit 5.

---

# Features

## Employee Management
- Add Employee
- Update Employee
- Delete Employee
- View Employee Records
- Employee Performance Evaluation

## Searching Algorithms
- Linear Search by Employee Name
- Binary Search by Employee ID
- HashMap Search by Employee ID

## Sorting Algorithms
- Merge Sort by Employee Name
- Merge Sort by Salary Ascending
- Merge Sort by Salary Descending

## Validation and Exception Handling
- Duplicate Employee ID Prevention
- Invalid Salary Validation
- Invalid Phone Number Validation
- Exception Handling using try-catch blocks

## GUI Features
- JTable Employee Display
- Row Highlighting
- Success Notifications
- Confirmation Dialogs
- Event-Driven Operations

## File Handling
- Employee data stored in `employees.txt`
- Query results stored in `query_results.txt`

## Automated Testing
- JUnit 5 Testing
- Searching and Sorting Validation
- CRUD Operation Testing
- Edge Case Testing

---

# Technologies Used

- Java
- Java Swing
- Eclipse IDE
- JUnit 5
- Object-Oriented Programming
- Merge Sort
- Binary Search
- HashMap

---

# Project Structure

```text
EmployeeRecordSystem
│
├── src
│   ├── Employee.java
│   ├── FullTimeEmployee.java
│   ├── PartTimeEmployee.java
│   ├── EmployeeManager.java
│   ├── EmployeeAlgorithms.java
│   ├── EmployeeGUI.java
│   ├── EmployeeCLI.java
│   ├── FileHandler.java
│   ├── InputValidator.java
│   ├── EmployeeManagerJUnitTest.java
│   └── MainApp.java
│
├── employees.txt
├── query_results.txt
└── README.md
