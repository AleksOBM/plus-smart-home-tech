CREATE TABLE IF NOT EXISTS shopping_carts (
    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) NOT NULL,
    is_open BOOLEAN NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uq_open_cart_per_user
ON shopping_carts(username)
WHERE is_open = true;

CREATE TABLE IF NOT EXISTS products_in_cart (
    shopping_cart_id UUID REFERENCES shopping_carts(id),
    product_id UUID NOT NULL,
    product_count INT,
    PRIMARY KEY (shopping_cart_id, product_id)
);
