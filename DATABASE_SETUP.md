# Database Setup Guide

## Overview

This POS system has been migrated from text file storage to MySQL database for better data management and reliability.

## Prerequisites

1. MySQL Server installed and running
2. MySQL Workbench (or any MySQL client)
3. MySQL JDBC Driver (mysql-connector-java) in your project classpath

## Setup Steps

### 1. Install MySQL JDBC Driver

Add the MySQL JDBC driver to your project:

1. Download `mysql-connector-java-8.0.33.jar` from MySQL website
2. Add it to your project's classpath (lib folder in NetBeans)

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

Run the application and verify:

- No connection errors in console
- Data can be loaded from database
- Operations (add, modify, delete) work correctly

## Database Schema

### Tables Created:

- **products**: Product information (id, name, price)
- **members**: Member/customer information (id, name, gender, age)
- **orders**: Order information (id, details)
- **payments**: Payment information (id, order_id, amounts, method)
- **users**: System user credentials (username, password)

### Default Data:

- Default admin user: `admin` / `1234`

## Migration from Text Files

If you have existing data in text files, you can:

1. **Manual Migration**: Use the application UI to re-enter data
2. **Script Migration**: Create a migration script to import from text files
3. **Fresh Start**: Start with empty database (recommended for testing)

## Troubleshooting

### Connection Errors

- Verify MySQL server is running
- Check username/password in DatabaseConnection.java
- Ensure database `tarumt_pos_db` exists
- Verify JDBC driver is in classpath

### Table Not Found Errors

- Re-run `database_schema.sql` script
- Verify all tables were created successfully

### Foreign Key Errors

- Payments are linked to orders - delete payments before deleting orders
- Or temporarily disable foreign key checks during migration

## Notes

- All text file operations have been replaced with database operations
- The system no longer reads/writes to `.txt` files
- Data is now persistent in MySQL database
- Backup your database regularly!
