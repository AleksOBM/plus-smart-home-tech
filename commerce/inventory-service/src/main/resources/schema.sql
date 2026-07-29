CREATE TABLE IF NOT EXISTS inventory (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    reserved_quantity INT NOT NULL,
    version BIGINT NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uq_inventory_product_id
ON inventory(product_id);

