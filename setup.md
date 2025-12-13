#  Setup Guide

## Prerequisites

1. MySQL Server installed and running

2. MySQL Workbench (or any MySQL client) [Download here](https://dev.mysql.com/downloads/workbench/)

3. MySQL JDBC Driver (mysql-connector-java) in your project classpath [Download here](https://dev.mysql.com/downloads/connector/j/?os=26)

## Setup Steps

### 1. Install MySQL JDBC Driver

Add the MySQL JDBC driver to your project:

1. Download `mysql-connector-java-9.5.0.jar` from MySQL website
2. Add it to your project's classpath (Libraries folder in NetBeans)

### 2. Create Database

1. Open MySQL Workbench
2. Connect to your MySQL server
3. Open the `database_schema.sql` file
4. Execute the entire script
5. Verify database `tarumt_pos_db` is created with all tables

### 3. Configure Database Connection

Edit `src/database/DatabaseConnection.java` and update these constants:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/tarumt_pos_db";
private static final String DB_USERNAME = "root";        //  MySQL username
private static final String DB_PASSWORD = "";            //  MySQL password
```

**Important:** Update these values to match your MySQL configuration!

### 4. Test Connection

Run the application by right clicking the Login.java -> Run File and verify:

- No connection errors in console
- Data can be loaded from database
- Operations (add, modify, delete) work correctly

