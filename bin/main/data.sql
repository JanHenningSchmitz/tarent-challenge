INSERT INTO product (id, name, sku, price, available) VALUES (1, 'Milch', '102', 2.00, true);
INSERT INTO product (id, name, sku, price, available) VALUES (2, 'Brot', '2035', 1.30, true);
INSERT INTO product (id, name, sku, price, available) VALUES (3, 'Kaese', 'S-155', 1.00, true);
INSERT INTO product (id, name, sku, price, available) VALUES (4, 'Wurst', '1488', 1.25, true);
INSERT INTO product (id, name, sku, price, available) VALUES (5, 'Couscous', 'B001', 4.99, false);

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

INSERT INTO chart (id, name, totalprice, checkedout) VALUES (1, 'JanHenning', 10, false);

INSERT INTO chartitem(chart_id, sku, quantity) VALUES (1, '102', 2);
INSERT INTO chartitem(chart_id, sku, quantity) VALUES (1, 'S-155', 2);
INSERT INTO chartitem(chart_id, sku, quantity) VALUES (1, '1488', 4);

