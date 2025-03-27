CREATE TABLE IF NOT EXISTS users (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(255)
);

CREATE TABLE IF NOT EXISTS files (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(255),
    file_path varchar(255)
);

CREATE TABLE IF NOT EXISTS events (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id int NOT NULL,
    file_id int NOT NULL,
    event_type varchar(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (file_id) REFERENCES files(id)
);