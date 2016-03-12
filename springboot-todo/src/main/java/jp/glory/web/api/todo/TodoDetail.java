package jp.glory.web.api.todo;

import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.usecase.todo.SearchTodo;
import jp.glory.web.api.ApiPaths;
import jp.glory.web.api.todo.response.TodoDetailResponse;

/**
 * TODO詳細のAPI.
 * @author Junki Yamada
 *
 */
@RestController
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
@RequestMapping(path = ApiPaths.Todo.PATH + "/{id}")
public class TodoDetail {

    /**
     * TODO検索.
     */
    private final SearchTodo searchTodo;

    /**
     * コンストラクタ.
     * @param searchTodo TODO検索
     */
    public TodoDetail(final SearchTodo searchTodo) {

        this.searchTodo = searchTodo;
    }

    /**
     * TODOを表示する.
     * @param id TODOのID
     * @return TODOの詳細レスポンス
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<TodoDetailResponse> view(@PathVariable final long id) {

        final TodoId todoId = new TodoId(id);

        final Optional<Todo> optTodo = searchTodo.searchById(todoId);

        if (optTodo.isPresent()) {

            return new ResponseEntity<>(new TodoDetailResponse(optTodo.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
