CREATE TABLE persons (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INTEGER
);

INSERT INTO persons (name, age) VALUES ('John Doe', 30);
INSERT INTO persons (name, age) VALUES ('Jane Doe', NULL);

