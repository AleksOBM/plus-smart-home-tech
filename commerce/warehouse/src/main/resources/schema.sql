CREATE TABLE IF NOT EXISTS products (
    product_id UUID NOT NULL PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    image_src TEXT,
    quantity_state VARCHAR(10) NOT NULL,
    product_state VARCHAR(10) NOT NULL,
    product_category VARCHAR(10),
    price REAL NOT NULL,

    CONSTRAINT products_quantity_state_check
            CHECK (quantity_state IN ('ENDED', 'FEW', 'ENOUGH', 'MANY')),

    CONSTRAINT products_product_state_check
            CHECK (product_state IN ('ACTIVE', 'DEACTIVATE')),

    CONSTRAINT products_product_category_check
        CHECK (product_category IN ('LIGHTING', 'CONTROL', 'SENSORS'))
);

