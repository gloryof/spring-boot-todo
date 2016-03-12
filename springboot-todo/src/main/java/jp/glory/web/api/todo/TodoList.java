package jp.glory.web.api.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import jp.glory.usecase.todo.SearchTodo;
import jp.glory.web.api.ApiPaths;
import jp.glory.web.api.todo.response.TodoListResponse;
import jp.glory.web.session.UserInfo;

/**
 * TODO一覧API.
 * 
 * @author Junki Yamada
 *
 */
@RestController
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@RequestMapping(ApiPaths.Todo.PATH)
public class TodoList {

    /**
     * TODO検索.
     */
    private final SearchTodo searchTodo;

    /**
     * ユーザ情報.
     */
    private final UserInfo userInfo;

    /**
     * コンストラクタ.
     * 
     * @param searchTodo
     *            TODO検索
     * @param userInfo
     *            ユーザ情報
     */
    @Autowired
    public TodoList(final SearchTodo searchTodo, final UserInfo userInfo) {

        this.searchTodo = searchTodo;
        this.userInfo = userInfo;
    }

    /**
     * 一覧を表示する.
     * 
     * @return TODOリスト
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<TodoListResponse> list() {

        final TodoListResponse response = new TodoListResponse(searchTodo.searchTodosByUser(userInfo.getUserId()));

        return new ResponseEntity<TodoListResponse>(response, HttpStatus.OK);
    }
}
