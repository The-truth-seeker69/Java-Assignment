# How to Add New Admin

1. Navigate to the database 'users' table.

2. Add a new row with your username values:
   - username: user-name

3. Because the password is using SHA-256 encryption, you need to use a SHA-256 hash generator to generate the hash of your password. You can use online tools like https://emn178.github.io/online-tools/sha256.html to generate the hash of your password. Copy the generated hash and paste it into the 'password' column.    

4. After finished, click 'Apply' to save the changes.

5. Verify that the new admin has been added successfully by logging in with the new admin's credentials.