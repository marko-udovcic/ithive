--  U PSQL ->  NOTICE:  drop cascades to constraint blog_category_id_fkey on table blog
--  Sve pet samo čisto upozorenje

CREATE OR REPLACE FUNCTION drop_all_tables(VARIADIC table_array TEXT[])
    RETURNS VOID AS $$
BEGIN

    FOR i IN 1 .. array_length(table_array, 1) BY 1 LOOP
        IF EXISTS (SELECT 1 FROM pg_catalog.pg_tables WHERE tablename=table_array[i]) THEN
            EXECUTE FORMAT ('DROP TABLE %I CASCADE', table_array[i]);
        END IF;
    END LOOP;

END;
$$ LANGUAGE plpgsql;




DO $$
BEGIN

-- user_role type
    IF EXISTS (SELECT 1 FROM pg_type WHERE typname='user_role') THEN
        PERFORM drop_all_tables ('category', 'likes', 'comments', 'followers', 'blog', 'users');
        DROP TYPE user_role;
    END IF;
    CREATE TYPE user_role AS ENUM ('admin', 'blogger', 'regularUser');
--    
    
-- user_status type
    IF EXISTS (SELECT 1 FROM pg_type WHERE typname='user_status') THEN
        PERFORM drop_all_tables ('category', 'likes', 'comments', 'followers', 'blog', 'users');
        DROP TYPE user_status;
    END IF;
    CREATE TYPE user_status AS ENUM ('approved', 'pending', 'default');
--


    PERFORM drop_all_tables ('category', 'likes', 'comments', 'followers', 'blog', 'users');


-- Users
    CREATE TABLE Users (
        user_id SERIAL PRIMARY KEY,
        username VARCHAR(70) NOT NULL,
        first_name VARCHAR(70) NOT NULL,
        last_name VARCHAR(100) NOT NULL,
        about_me TEXT NULL,
        company_name VARCHAR(100) NULL,
        email VARCHAR(150) UNIQUE NOT NULL,
        password_hash VARCHAR(255) NOT NULL,
        role user_role DEFAULT 'regularUser', 
    	status user_status DEFAULT 'pending',    
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
-- 

-- Category 
    CREATE TABLE Category (
        category_id SERIAL PRIMARY KEY,
        category_name VARCHAR(50) NOT NULL
    );
--

-- Blog
    CREATE TABLE Blog (
        blog_id SERIAL PRIMARY KEY,
      	user_id INT NOT NULL REFERENCES Users(user_id),
      	title VARCHAR(255) NOT NULL,
      	img_url TEXT NULL,
        content TEXT NOT NULL,
        category_id INT REFERENCES Category(category_id),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
--

-- Comments
    CREATE TABLE Comments (
        comment_id SERIAL PRIMARY KEY,
        user_id INT NOT NULL REFERENCES Users(user_id),
    	blog_id INT NOT NULL REFERENCES Blog(blog_id),
    	text TEXT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        parent_id INT NOT NULL DEFAULT 0,
        depth INT NOT NULL DEFAULT 0

    );
--

-- Followers
    CREATE TABLE Followers (
        follower_id INT NOT NULL,
        followed_id INT NOT NULL,
        date_following TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (follower_id, followed_id),
        FOREIGN KEY (follower_id) REFERENCES Users(user_id),
        FOREIGN KEY (followed_id) REFERENCES Users(user_id)
    );
--


-- Likes
    CREATE TABLE Likes (
        like_id SERIAL PRIMARY KEY,
        user_id INT NOT NULL REFERENCES Users(user_id),
        blog_id INT NOT NULL REFERENCES Blog(blog_id)
    );
--
 

END;
$$ LANGUAGE  plpgsql;