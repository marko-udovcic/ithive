DO $$
BEGIN

-- Users
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
-- 


-- Category 
    CREATE TABLE Category (
        id BIGSERIAL NOT NULL,
        category_name VARCHAR(50) NOT NULL,

        PRIMARY KEY (id)
    );
--


-- Blog
    CREATE TABLE Blog (
        id BIGSERIAL NOT NULL,
      	user_id VARCHAR(50) NOT NULL,
      	title VARCHAR(255) NOT NULL,
      	img_url TEXT NULL,
        content TEXT NOT NULL,
        category_id INT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        
        PRIMARY KEY (id),
        FOREIGN KEY(user_id) REFERENCES Users(id),
        FOREIGN KEY(category_id) REFERENCES Category(id)
    );
--


-- Comments
    CREATE TABLE Comments (
        id BIGSERIAL NOT NULL,
        user_id VARCHAR(50) NOT NULL,
    	blog_id INT NOT NULL,
    	text TEXT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        parent_id INT NOT NULL DEFAULT 0,
        depth INT NOT NULL DEFAULT 0,

        PRIMARY KEY(id),
        FOREIGN KEY(user_id) REFERENCES Users(id),
        FOREIGN KEY(blog_id) REFERENCES Blog(id)
    );
--


-- Followers
    CREATE TABLE Followers (
        id BIGSERIAL NOT NULL,
        follower_id VARCHAR(50) NOT NULL,
        followed_id VARCHAR(50) NOT NULL,
        date_following TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

        PRIMARY KEY(id),
        -- PRIMARY KEY (follower_id, followed_id),
        FOREIGN KEY (follower_id) REFERENCES Users(id),
        FOREIGN KEY (followed_id) REFERENCES Users(id)
    );
--



-- Likes
    CREATE TABLE Likes (
        id BIGSERIAL NOT NULL,
        user_id VARCHAR(50) NOT NULL,
        blog_id INT NOT NULL,
        
        PRIMARY KEY (id),
        FOREIGN KEY(user_id) REFERENCES Users(id),
        FOREIGN KEY(blog_id) REFERENCES Blog(id)
    );
--
 

END;
$$ LANGUAGE  plpgsql;