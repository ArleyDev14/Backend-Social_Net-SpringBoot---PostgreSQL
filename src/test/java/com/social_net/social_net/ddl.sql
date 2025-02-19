CREATE TABLE users(
    id SERIAL UNIQUE NOT NULL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    passwordp TEXT NOT NULL,
    fullname VARCHAR(300) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    bio TEXT,
    profilePicture TEXT,
    birthday DATE NOT NULL,
    phone BIGINT NOT NULL
);

CREATE TABLE posts(
    id SERIAL UNIQUE NOT NULL PRIMARY KEY,
    user_id INT NOT NULL,
    content VARCHAR(500),
    imageUrl TEXT,
    createdAt DATE NOT NULL,
    Foreign Key (user_id) REFERENCES users(id)
);

CREATE TABLE likes (
    id SERIAL UNIQUE NOT NULL PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE (post_id, user_id)
);

CREATE TABLE comments (
    id SERIAL UNIQUE NOT NULL PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);