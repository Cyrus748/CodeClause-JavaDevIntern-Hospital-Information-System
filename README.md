# CodeClause-JavaDevIntern-Hospital-Information-System

## Project Overview
The **Hospital Information System** is a Java-based desktop application designed to help hospital staff manage:
- Patient records
- Appointments
- Medical history

The system uses **Java Swing** for the user interface and **MySQL** as the backend database for data storage and retrieval. It demonstrates the use of Object-Oriented Programming (OOP) principles, event handling, and database integration.

---

## Features
1. **Patient Management**:
   - Add new patient records (name, age, medical history).
   - View all patient records.

2. **Appointment Management**:
   - Schedule new appointments (patient ID, date, time).
   - View all appointments.

3. **Database Integration**:
   - All data is stored in a MySQL database.
   - Ensures persistent storage and retrieval of information.

---

## Project Structure
```
HospitalInformationSystem/
├── src/
│   └── HospitalInformationSystem.java  # Main Java program
├── lib/
│   └── mysql-connector-java-8.0.34.jar # MySQL JDBC Driver
└── README.md                            # Project documentation
```

---

## Prerequisites

1. **Java Development Kit (JDK)**:
   - Ensure JDK 8 or later is installed.
   - Set up the `JAVA_HOME` environment variable.

2. **MySQL Database**:
   - Install MySQL Server and MySQL Workbench.
   - Create a database and tables as described below.

3. **MySQL JDBC Driver**:
   - Download the MySQL Connector/J `.jar` file from the [official MySQL website](https://dev.mysql.com/downloads/connector/j/).
   - Place the `.jar` file in the `lib/` directory of the project.

---

## Database Setup

1. Open MySQL Workbench.
2. Execute the following SQL commands to set up the database and tables:

```sql
CREATE DATABASE hospital_db;
USE hospital_db;

CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    medical_history TEXT
);

CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);
```

---

## Setting Up the Project

### 1. **Add MySQL JDBC Driver to IntelliJ IDEA**
1. Open your project in IntelliJ IDEA.
2. Go to `File > Project Structure` (or press `Ctrl+Alt+Shift+S`).
3. Navigate to the `Libraries` section.
4. Click the `+` icon and select **Java**.
5. Browse to the `lib/` directory and select the `mysql-connector-java-8.0.34.jar` file.
6. Click **Apply** and then **OK**.

### 2. **Run the Project**
1. Open the `HospitalInformationSystem.java` file.
2. Click the green play button or run the project using `Shift+F10`.
3. Use the GUI to add/view patient records and appointments.

---

## Code Overview

### `HospitalInformationSystem.java`
This file contains the entire implementation of the hospital information system. Below is a high-level overview:

1. **Database Connection**:
   ```java
   private static Connection connectToDatabase() {
       String url = "jdbc:mysql://localhost:3306/hospital_db";
       String user = "root"; // Replace with your MySQL username
       String password = "password"; // Replace with your MySQL password
       try {
           Connection connection = DriverManager.getConnection(url, user, password);
           return connection;
       } catch (SQLException e) {
           System.out.println("Database connection failed: " + e.getMessage());
           return null;
       }
   }
   ```

2. **User Interface (GUI)**:
   - Built using **Java Swing**.
   - Uses `JTabbedPane` to separate patient management and appointment management functionalities.

3. **Event Handling**:
   - Add patient:
     ```java
     addPatientButton.addActionListener(e -> {
         // Logic to add a new patient
     });
     ```
   - View patients:
     ```java
     viewPatientsButton.addActionListener(e -> {
         // Logic to fetch and display patient records
     });
     ```

---

## How to Run
1. Compile the program:
   ```bash
   javac -cp .;lib/mysql-connector-java-8.0.34.jar src/HospitalInformationSystem.java
   ```
2. Run the program:
   ```bash
   java -cp .;lib/mysql-connector-java-8.0.34.jar src/HospitalInformationSystem
   ```

*(On Linux/Mac, replace `;` with `:` in the classpath.)*

---

## Key Learnings
- **Object-Oriented Programming**: Use of classes, methods, and encapsulation.
- **Event-Driven Programming**: Handling user actions in a GUI.
- **Database Integration**: Connecting Java applications to MySQL and performing CRUD operations.
- **Java Swing**: Designing user-friendly interfaces.

---

## Future Enhancements
1. Add authentication for hospital staff.
2. Implement search functionality for patients and appointments.
3. Enhance the UI with modern libraries like JavaFX.
4. Add export options for reports (e.g., PDF or Excel).

---

Feel free to contribute or suggest improvements!

