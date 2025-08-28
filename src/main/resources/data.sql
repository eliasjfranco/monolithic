INSERT INTO role(ID, NAME) VALUES (1, 'ADMIN');
INSERT INTO role(ID, NAME) VALUES (2, 'USER');

INSERT INTO company(NAME, EMAIL, ENABLED) VALUES ('MOCK', 'mock@mock.com', true);
INSERT INTO users(EMAIL, NAME, PASSWORD, ENABLED, TENANT_ID, ROLE_ID) VALUES ('mock@mock.com', 'mock', '{bcrypt}$2a$10$xUQn77g75md2SxhrURoRyuWvgiQZ4Gnq5xciSi.mYn0VPaCtxfVsW', true, 1, 1);