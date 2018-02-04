package jp.glory.todo.context.todo.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import io.swagger.annotations.Api;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.base.web.UserInfo;
import jp.glory.todo.context.base.web.api.ApiPaths;
import jp.glory.todo.context.base.web.api.OriginalOperationDoc;
import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.todo.domain.value.Memo;
import jp.glory.todo.context.todo.domain.value.Summary;
import jp.glory.todo.context.todo.domain.value.TodoId;
import jp.glory.todo.context.todo.usecase.SaveTodo;
import jp.glory.todo.context.todo.usecase.SearchTodo;
import jp.glory.todo.context.todo.web.api.request.TodoCreateRequest;
import jp.glory.todo.context.todo.web.api.response.TodoCreateSuccessResponse;
import jp.glory.todo.context.todo.web.api.response.TodoListResponse;
import jp.glory.todo.context.user.web.exception.InvalidRequestException;

/**
 * TODO一覧API.
 * 
 * @author Junki Yamada
 *
 */
@RestController
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@RequestMapping(ApiPaths.Todo.PATH)
@Api(tags = {"Todo List Operation"})
public class TodoList {

    /**
     * TODO検索.
     */
    private final SearchTodo searchTodo;

    /**
     * TODO保存.
     */
    private final SaveTodo saveTodo;

    /**
     * コンストラクタ.
     * 
     * @param searchTodo TODO検索
     * @param saveTodo TODO保存
     */
    @Autowired
    public TodoList(final SearchTodo searchTodo, final SaveTodo saveTodo) {

        this.searchTodo = searchTodo;
        this.saveTodo = saveTodo;
    }

    @OriginalOperationDoc(
            name = "TODOリスト取得",
            summary = "ログインしているユーザのTODOのリストを取得する",
            postcondition = {
                                "ログインしているユーザの全てのTODOのリストが取得できる"
                            }
    )
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TodoListResponse> list(@AuthenticationPrincipal final UserInfo userInfo) {

        final TodoListResponse response = new TodoListResponse(searchTodo.searchTodosByUser(userInfo.getUserId()));

        return new ResponseEntity<TodoListResponse>(response, HttpStatus.OK);
    }

    @OriginalOperationDoc(
            name = "TODO新規作成",
            summary = "ログインしているユーザでTODOを作成する",
            postcondition = {
                                "新規にTODOが作成される",
                                "作成されたTODOのIDが返る"
                            }
    )
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TodoCreateSuccessResponse> save(final TodoCreateRequest request, @AuthenticationPrincipal final UserInfo userInfo) {

        final SaveTodo.Result result = saveTodo.save(new Todo(TodoId.notNumberingValue(), userInfo.getUserId(),
                new Summary(request.getSummary()), new Memo(request.getMemo()), request.isCompleted()));

        final ValidateErrors errors = result.getErrors();
        if (errors.hasError()) {

            throw new InvalidRequestException(errors);
        }

        return new ResponseEntity<>(new TodoCreateSuccessResponse(result.getSavedTodoId()), HttpStatus.CREATED);
    }

}
