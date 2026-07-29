CREATE TABLE IF NOT EXISTS orders (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        customer_name VARCHAR(255) NOT NULL,
        customer_email VARCHAR(255) NOT NULL,
        status VARCHAR(255) NOT NULL CHECK (status in ('UNDEFINED','CREATED','IN_PROGRESS','COMPLETED')),
        total_price NUMERIC(10,2) NOT NULL,
        status_details VARCHAR(255),
        created_at TIMESTAMP(3) NOT NULL
    );

CREATE TABLE IF NOT EXISTS order_items (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        order_id BIGINT NOT NULL,
        product_id BIGINT NOT NULL,
        product_name VARCHAR(255) NOT NULL,
        quantity INTEGER NOT NULL,
        price NUMERIC(10,2) NOT NULL,
        CONSTRAINT fk_items_in_orders
                FOREIGN KEY (order_id)
                REFERENCES orders (id) MATCH SIMPLE
                ON DELETE CASCADE
    );

