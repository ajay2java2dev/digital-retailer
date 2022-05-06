INSERT INTO customer (customer_id, first_name, last_name, dob) VALUES (99999, 'Test 9 Ajay', 'Menon', '1990-01-01');
INSERT INTO customer (customer_id, first_name, last_name, dob) VALUES (98765, 'Test 8 Ajay', 'Menon', '1989-01-01');
INSERT INTO customer (customer_id, first_name, last_name, dob) VALUES (12345, 'Test 0 Ajay', 'Menon', '1990-02-01');
INSERT INTO customer (customer_id, first_name, last_name, dob) VALUES (54321, 'Test 1 Ajay', 'Menon', '1991-01-01');
INSERT INTO customer (customer_id, first_name, last_name, dob) VALUES (34521, 'Test 2 Ajay', 'Menon', '1992-01-01');
INSERT INTO customer (customer_id, first_name, last_name, dob) VALUES (33333, 'Test 3 Ajay', 'Menon', '1993-01-01');
INSERT INTO customer (customer_id, first_name, last_name, dob) VALUES (44444, 'Test 4 Ajay', 'Menon', '1994-01-01');

INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 99999, 120.00, CURRENT_DATE());
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 98765, 120.00, DATEADD('MONTH', -4, CURRENT_DATE));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 33333, 130.00, DATEADD('MONTH', -4, CURRENT_DATE));

INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 12345, 50.00,  CURRENT_DATE());
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 12345, 50.10,  DATEADD('MONTH', -4, CURRENT_DATE));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 12345, 100.00, DATEADD('MONTH', -3, CURRENT_DATE));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 12345, 100.01, DATEADD('MONTH', -2, CURRENT_DATE));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 12345, 101.00, DATEADD('MONTH', -1, CURRENT_DATE));

INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 54321, 50.00,  DATEADD('MONTH', -4, CURRENT_DATE));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 54321, 50.10,  DATEADD('MONTH', -3, CURRENT_DATE));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 54321, 100.00, DATEADD('MONTH', -2, CURRENT_DATE));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 54321, 100.01, DATEADD('MONTH', -1, CURRENT_DATE));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 54321, 101.00, CURRENT_DATE());

INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 34521, 50.00,  DATEADD('MONTH', -4, CURRENT_DATE));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 34521, 75,     DATEADD('MONTH', -3, CURRENT_DATE));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 34521, 101.00, DATEADD('MONTH', -2, CURRENT_DATE));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 34521, 102.01, DATEADD('MONTH', -1, CURRENT_DATE));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 34521, 103.00, CURRENT_DATE());
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 34521, 20000.00, CURRENT_DATE());

INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 44444, 10.00,  DATEADD('MONTH', -1, CURRENT_DATE()));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 44444, 10,     DATEADD('MONTH', -2, CURRENT_DATE()));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 44444, 10.00, DATEADD('MONTH', -2, CURRENT_DATE()));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 44444, -10.01, DATEADD('MONTH', -3, CURRENT_DATE()));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 44444, 9999999999999999999999999999999.00, DATEADD('MONTH', -3, CURRENT_DATE()));
INSERT INTO payment_transactions (trans_id, customer_id, amount, trans_date_time) VALUES (default, 44444, 49.99, CURRENT_DATE());
