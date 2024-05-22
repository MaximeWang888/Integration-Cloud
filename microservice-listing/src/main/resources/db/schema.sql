CREATE TABLE listings (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT,
                          title VARCHAR(255),
                          description TEXT,
                          price DECIMAL(10, 2),
                          location VARCHAR(255),
                          type VARCHAR(50),
                          availability BOOLEAN DEFAULT TRUE,
                          FOREIGN KEY (user_id) REFERENCES auth_db.users(id)
);