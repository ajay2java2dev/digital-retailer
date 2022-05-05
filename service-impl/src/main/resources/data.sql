INSERT INTO customer (customer_id, first_name, last_name, dob) VALUES (12345, 'Test 0 Ajay', 'Menon', '1990-01-01');
INSERT INTO customer (customer_id, first_name, last_name, dob) VALUES (54321, 'Test 1 Ajay', 'Menon', '1991-01-01');
INSERT INTO customer (customer_id, first_name, last_name, dob) VALUES (34521, 'Test 2 Ajay', 'Menon', '1992-01-01');


INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (12345, 50.00, '2022-01-01');
INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (12345, 50.10, '2022-02-01');
INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (12345, 100.00, '2022-03-01');
INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (12345, 100.01, '2022-04-01');
INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (12345, 101.00, '2022-05-01');

INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (54321, 50.00, '2022-01-01');
INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (54321, 50.10, '2022-02-01');
INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (54321, 100.00, '2022-03-01');
INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (54321, 100.01, '2022-04-01');
INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (54321, 101.00, '2022-05-01');

INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (34521, 50.00, '2022-01-01');
INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (34521, 75, '2022-02-01');
INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (34521, 101.00, '2022-03-01');
INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (34521, 102.01, '2022-04-01');
INSERT INTO payment_transactions (customer_id, amount, trans_date_time) VALUES (34521, 103.00, '2022-05-01');