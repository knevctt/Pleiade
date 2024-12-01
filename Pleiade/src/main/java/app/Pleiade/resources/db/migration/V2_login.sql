CREATE TABLE usuario (
                      id SERIAL PRIMARY KEY,
                      username varchar not null,
                      password varchar not null,
                      role varchar not null
);

INSERT INTO usuario (username, password, role)
VALUES ('admin', '$2a$12$xu.vCTUnAFEiI9I6bVk1veUHI00W0QtEOhqFZq.ImRj.19JWTBe5e', 'admin');

