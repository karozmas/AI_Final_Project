
#  Installation Guide – MediSense (JavaFX Medical Assistant)

This guide explains how to set up the database and run the JavaFX-based medical assistant system on your local machine.

---

##  Prerequisites

Make sure you have the following installed:

-  Java JDK 8+
-  JavaFX SDK (if using JDK < 11)
-  MySQL Server or XAMPP
-  MySQL Workbench (or any MySQL GUI)
-  IntelliJ IDEA, Eclipse, or any Java IDE

---

## Step 1: Import the Database

1. Open **MySQL Workbench** or any SQL tool.
2. Create a new database named:

   ```sql
   CREATE DATABASE expert_system;
   ```

3. Use the database:

   ```sql
   USE expert_system;
   ```

4. Import the SQL file:

   - If you have a `.sql` file (e.g., `expert_system.sql`), go to:
     `File > Open SQL Script > Select File > Run`
   - Or run in terminal:

     ```bash
     mysql -u root -p expert_system < path/to/expert_system.sql
     ```

 This will create all necessary tables like:

- `users`
- `diagnosis_history`
- `rules`

---

##  Step 2: Configure Java Project

1. Open the JavaFX project in your IDE.
2. Make sure you have the following libraries:
   - JavaFX SDK (for GUI)
   - JDBC Driver for MySQL (`mysql-connector-java-x.x.x.jar`)
3. In your code, check the **DB connection file**, and make sure it matches your MySQL settings:

   ```java
   String url = "jdbc:mysql://localhost:3306/expert_system";
   String user = "root";
   String password = "";
   ```

>  If you use XAMPP, make sure MySQL is **running**.

---

##  Step 3: Run the Project

1. Compile and run the `Main.java` class.
2. The application should open with the login or register screen.

---

##  Notes

- All diagnosis rules are stored in the `rules` table.
- You must create at least one admin user to manage the system.
- Diagnoses will be saved in `diagnosis_history`.

---

##  Sample Admin Credentials

If included in the database:

- **Username**: `admin`
- **Password**: `admin123`

Otherwise, register as admin from the UI and manually mark the user as admin in the database.

---


