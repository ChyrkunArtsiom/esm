INSERT INTO esm_module2.certificates (id, name, description, price, create_date, last_update_date, duration)
VALUES (1, 'test1', 'Test description 1', 1, '2020-11-01 23:24:38.851000', null, 1);
INSERT INTO esm_module2.certificates (id, name, description, price, create_date, last_update_date, duration)
VALUES (2, 'test2', 'Test description 2', 2, '2020-11-01 23:24:58.419000', null, 2);
INSERT INTO esm_module2.tags (id, name) VALUES (1, 'firsttag');
INSERT INTO esm_module2.tags (id, name) VALUES (2, 'secondtag');
INSERT INTO esm_module2.certificate_tag (certificate_id, tag_id) VALUES (1, 1);
INSERT INTO esm_module2.certificate_tag (certificate_id, tag_id) VALUES (1, 2);
INSERT INTO esm_module2.certificate_tag (certificate_id, tag_id) VALUES (2, 1)