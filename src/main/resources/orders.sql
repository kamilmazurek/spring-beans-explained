INSERT INTO orders (id, order_date, total_amount) VALUES
    (1, '2025-11-15', 150.00),
    (2, '2025-11-16', 250.50),
    (3, '2025-11-16', 0.00);

INSERT INTO order_products (order_id, product) VALUES
    (1, 'Product A'),
    (1, 'Product B'),
    (2, 'Product C'),
    (2, 'Product D'),
    (2, 'Product E');