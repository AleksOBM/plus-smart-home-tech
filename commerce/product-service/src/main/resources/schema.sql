CREATE TABLE IF NOT EXISTS categories (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        name VARCHAR(255) NOT NULL UNIQUE,
        description VARCHAR(500)
    );

CREATE TABLE IF NOT EXISTS products (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        description VARCHAR(2000),
        price NUMERIC(10,2) NOT NULL,
        image_url VARCHAR(255),
        active BOOLEAN NOT NULL,
        category_id BIGINT,
        CONSTRAINT fk_products_in_categories
            FOREIGN KEY (category_id)
            REFERENCES categories (id) MATCH SIMPLE
            ON UPDATE CASCADE
            ON DELETE NO ACTION
    );
