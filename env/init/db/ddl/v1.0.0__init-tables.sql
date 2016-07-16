-- users関係のDDL
CREATE TABLE users (
	user_id BIGINT NOT NULL,
	login_id VARCHAR(50) NOT NULL,
	user_name VARCHAR(50) NOT NULL,
	password TEXT NOT NULL,
	PRIMARY KEY(user_id)
);

CREATE INDEX idx_users_login_id ON users (login_id);

CREATE SEQUENCE user_id_seq;

-- todos関係のDDL
CREATE TABLE todos (
	todo_id BIGINT NOT NULL,
	user_id BIGINT NOT NULL,
	summary VARCHAR(20) NOT NULL,
	completed BOOLEAN NOT NULL,
	version BIGINT NOT NULL,
	PRIMARY KEY(todo_id)
);

CREATE TABLE todos_detail (
	todo_id BIGINT NOT NULL REFERENCES todos(todo_id),
	memo TEXT NOT NULL,
	PRIMARY KEY(todo_id)
);

CREATE INDEX idx_todos_user_id ON todos (user_id);

CREATE SEQUENCE todo_id_seq;
