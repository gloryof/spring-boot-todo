SELECT
	todos.todo_id,
	todos.user_id,
	todos.summary,
	detail.memo,
	todos.completed
FROM
	todos 
	LEFT JOIN todos_detail AS detail
		ON todos.todo_id = detail.todo_id
WHERE
	todos.todo_id = /*todoId*/1