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