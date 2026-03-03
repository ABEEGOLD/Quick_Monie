truncate table account cascade;
truncate table transaction cascade;

INSERT INTO account (id, account_number, balance,email, password, authorities) VALUES
('0f88c24e-8f30-4903-9a14-0e5de7c8e756','0123456789',0.00, 'john@gmail.com', '$2a$10$bvTJOkveypE.chWNcgn7me3fsBpf7PeYTJ8NUI7gFyQdP8YkOkjsq', ARRAY[0]);

INSERT INTO transaction (id) VALUES ('9059ca01-3ee1-46ec-a509-b2819f164734');