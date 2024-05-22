CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role ENUM('LOCATAIRE', 'PROPRIETAIRE', 'INVITE') DEFAULT 'LOCATAIRE'
);

CREATE TABLE listings (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT,
                          title VARCHAR(255),
                          description TEXT,
                          price DECIMAL(10, 2),
                          location VARCHAR(255),
                          type VARCHAR(50),
                          availability BOOLEAN DEFAULT TRUE,
                          FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE bookings (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          listing_id INT,
                          user_id INT,
                          start_date DATE,
                          end_date DATE,
                          status ENUM('PENDING', 'CONFIRMED', 'CANCELLED') DEFAULT 'PENDING',
                          FOREIGN KEY (listing_id) REFERENCES listings(id),
                          FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE tracking (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          booking_id INT,
                          status VARCHAR(255),
                          timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

CREATE TABLE user_details (
                              id INT PRIMARY KEY,
                              email VARCHAR(255),
                              phone VARCHAR(20),
                              address VARCHAR(255),
                              FOREIGN KEY (id) REFERENCES users(id)
);

INSERT INTO users (username, password, role) VALUES
                                                 ('john_doe', 'password123', 'LOCATAIRE'),
                                                 ('jane_smith', 'password123', 'INVITE');

INSERT INTO listings (user_id, title, description, price, location, type) VALUES
                                                                              (1, 'Cozy Apartment', 'A nice and cozy apartment.', 100.00, 'City Center', 'Apartment'),
                                                                              (1, 'Luxury Villa', 'A luxurious villa with pool.', 500.00, 'Beachside', 'Villa');

INSERT INTO bookings (listing_id, user_id, start_date, end_date, status) VALUES
                                                                             (1, 2, '2024-06-01', '2024-06-10', 'CONFIRMED'),
                                                                             (2, 2, '2024-07-01', '2024-07-15', 'PENDING');

INSERT INTO tracking (booking_id, status) VALUES
                                              (1, 'CONFIRMED'),
                                              (2, 'PENDING');

INSERT INTO user_details (id, email, phone, address) VALUES
                                                         (1, 'john_doe@example.com', '123-456-7890', '123 Main St, City, Country'),
                                                         (2, 'jane_smith@example.com', '098-765-4321', '456 Elm St, City, Country');
