INSERT INTO esm_module2.certificates (id, name, description, price, create_date, last_update_date, duration)
VALUES (NEXTVAL('esm_module2.certificates_id_seq'), 'test1', 'Test description 1', 100, '2020-11-01 00:00:00.000000', null, 1),
       (NEXTVAL('esm_module2.certificates_id_seq'), 'test2', 'Test description 2', 200, '2020-11-01 00:00:00.000000', null, 2),
       (NEXTVAL('esm_module2.certificates_id_seq'), 'test3', 'Test description 3', 300, '2020-11-01 00:00:00.000000', null, 3),
       (NEXTVAL('esm_module2.certificates_id_seq'), 'test4', 'Test description 4', 400, '2020-11-01 00:00:00.000000', null, 4);
INSERT INTO esm_module2.tags (id, name)
VALUES (NEXTVAL('esm_module2.tags_id_seq'), 'firsttag'),
       (NEXTVAL('esm_module2.tags_id_seq'), 'secondtag'),
       (NEXTVAL('esm_module2.tags_id_seq'), 'thirdtag'),
       (NEXTVAL('esm_module2.tags_id_seq'), 'fourthtag');
INSERT INTO esm_module2.certificate_tag (certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (3, 1),
       (4, 1),
       (4, 2);
INSERT INTO esm_module2.users (id, name, password)
VALUES (NEXTVAL('esm_module2.users_id_seq'), 'user1', 'password1'),
       (NEXTVAL('esm_module2.users_id_seq'), 'user2', 'password2');
INSERT INTO esm_module2.orders (id, cost, purchase_date, user_id)
VALUES (NEXTVAL('esm_module2.orders_id_seq'), 300, '2020-11-01 00:00:00.000000', 1),
       (NEXTVAL('esm_module2.orders_id_seq'), 300, '2020-11-01 00:00:00.000000', 2);

INSERT INTO esm_module2.certificate_order (certificate_id, order_id)
VALUES (1, 1),
       (2, 1),
       (1, 2),
       (2, 2);