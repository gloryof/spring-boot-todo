CREATE TABLE users (
	user_id BIGINT NOT NULL,
	login_id VARCHAR(50) NOT NULL,
	user_name VARCHAR(50) NOT NULL,
	password TEXT NOT NULL,
	PRIMARY KEY(user_id)
);

CREATE INDEX idx_users_login_id ON users (login_id);

CREATE SEQUENCE user_id_seq;
	

