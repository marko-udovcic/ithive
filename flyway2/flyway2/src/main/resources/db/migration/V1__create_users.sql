 CREATE TABLE Users (
        id VARCHAR(50) NOT NULL,
        username VARCHAR(70) NOT NULL,
        first_name VARCHAR(70) NOT NULL,
        last_name VARCHAR(100) NOT NULL,
        about_me TEXT NULL,
        company_name VARCHAR(100) NULL,
        email VARCHAR(150) UNIQUE NOT NULL,
        password_hash VARCHAR(255) NOT NULL,
        role VARCHAR(50) NOT NULL,
        status VARCHAR(50) NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

        PRIMARY KEY (id)
    );