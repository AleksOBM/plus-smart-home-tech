CREATE TABLE IF NOT EXISTS products (
    product_id UUID PRIMARY KEY,
    quantity_all INT NOT NULL,
    quantity_booked INT NOT NULL,
    fragile BOOLEAN NOT NULL,
    weight REAL NOT NULL,

    CONSTRAINT chk_quantity CHECK (
        quantity_all >= 0
        AND quantity_booked >= 0
        AND quantity_booked <= quantity_all
    )
);

CREATE TABLE IF NOT EXISTS dimensions (
    product_id UUID PRIMARY KEY,
    width REAL NOT NULL,
    height REAL NOT NULL,
    depth REAL NOT NULL,

    CONSTRAINT fk_dimensions_product
        FOREIGN KEY (product_id)
        REFERENCES products(product_id)
        ON DELETE CASCADE
);