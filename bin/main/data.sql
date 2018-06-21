INSERT INTO product (name, sku, price, available) VALUES ('Milch', '102', 2.00, true);

INSERT INTO product_eans (product_sku, eans) VALUES ('102', '12345678');
INSERT INTO product_eans (product_sku, eans) VALUES ('102', '77777777');
INSERT INTO product_eans (product_sku, eans) VALUES ('102', '23498128');

INSERT INTO chart (name, totalprice, checkedout) VALUES ('JanHenning', 10, false);

INSERT INTO chart_items(chart_name, items) VALUES ('JanHenning', '102;2');
INSERT INTO chart_items(chart_name, items) VALUES ('JanHenning', 'S-155;2');
INSERT INTO chart_items(chart_name, items) VALUES ('JanHenning', '1488;4');

/*INSERT INTO product_eans (product_sku, eans) VALUES ('2035', '34558821');
INSERT INTO product_eans (product_sku, eans) VALUES ('2035', '12323410');
INSERT INTO product_eans (product_sku, eans) VALUES ('S-155', '34598146');
INSERT INTO product_eans (product_sku, eans) VALUES ('S-155', '43565922');
INSERT INTO product_eans (product_sku, eans) VALUES ('S-155', '23454045');
INSERT INTO product_eans (product_sku, eans) VALUES ('1488', '18754629');
INSERT INTO product_eans (product_sku, eans) VALUES ('1488', '46025548');
INSERT INTO product_eans (product_sku, eans) VALUES ('B001', '54342316');
INSERT INTO product (name, sku, price, available) VALUES ('Brot', '2035', 1.30, true);
INSERT INTO product (name, sku, price, available) VALUES ('Kaese', 'S-155', 1.00, true);
INSERT INTO product (name, sku, price, available) VALUES ('Wurst', '1488', 1.25, true);
INSERT INTO product (name, sku, price, available) VALUES ('Couscous', 'B001', 4.99, false);*/
