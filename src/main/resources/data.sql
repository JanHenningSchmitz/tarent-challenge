INSERT INTO product (id, name, sku, price) VALUES (1, 'Milch', '102', 2.00);
INSERT INTO product (id, name, sku, price) VALUES (2, 'Brot', '2035', 1.30);
INSERT INTO product (id, name, sku, price) VALUES (3, 'Kaese', 'S-155', 1.00);
INSERT INTO product (id, name, sku, price) VALUES (4, 'Wurst', '1488', 1.25);
INSERT INTO product (id, name, sku, price) VALUES (5, 'Couscous', 'B001', 4.99);

INSERT INTO product_eans (product_id, eans) VALUES (1, '12345678');
INSERT INTO product_eans (product_id, eans) VALUES (1, '77777777');
INSERT INTO product_eans (product_id, eans) VALUES (1, '23498128');
INSERT INTO product_eans (product_id, eans) VALUES (2, '34558821');
INSERT INTO product_eans (product_id, eans) VALUES (2, '12323410');
INSERT INTO product_eans (product_id, eans) VALUES (3, '34598146');
INSERT INTO product_eans (product_id, eans) VALUES (3, '43565922');
INSERT INTO product_eans (product_id, eans) VALUES (3, '23454045');
INSERT INTO product_eans (product_id, eans) VALUES (4, '18754629');
INSERT INTO product_eans (product_id, eans) VALUES (4, '46025548');
INSERT INTO product_eans (product_id, eans) VALUES (5, '54342316');

INSERT INTO chart (id, name, totalprice) VALUES (1, 'JanHenning', 10);

INSERT INTO chartitem(chart_id, sku, quantity) VALUES (1, '102', 2);
INSERT INTO chartitem(chart_id, sku, quantity) VALUES (1, 'S-155', 2);
INSERT INTO chartitem(chart_id, sku, quantity) VALUES (1, '1488', 4);

