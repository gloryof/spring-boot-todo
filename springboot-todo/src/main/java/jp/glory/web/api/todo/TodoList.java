package jp.glory.web.api.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.framework.web.exception.InvalidRequestException;
import jp.glory.usecase.todo.SaveTodo;
import jp.glory.usecase.todo.SearchTodo;
import jp.glory.web.api.ApiPaths;
import jp.glory.web.api.todo.request.TodoCreateRequest;
import jp.glory.web.api.todo.response.TodoCreateSuccessResponse;
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

    /**
     * 一覧を表示する.
     * 
     * @param userInfo ユーザ情報
     * @return TODOリスト
     */
    @ApiOperation(
            value = "TODOリスト取得",
            notes="**[概要]**  \r\nログインしているユーザのTODOのリストを取得する。\r\n\r\n**[事前条件]**\r\n- 任意のユーザでログインしている\r\n\r\n**[事後条件]**\r\n - ログインしているユーザの全てのTODOのリストが取得できる"
    )
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoListResponse> list(@AuthenticationPrincipal final UserInfo userInfo) {

        final TodoListResponse response = new TodoListResponse(searchTodo.searchTodosByUser(userInfo.getUserId()));

        return new ResponseEntity<TodoListResponse>(response, HttpStatus.OK);
    }

    /**
     * TODOを新規作成する.
     * @param request 作成リクエスト
     * @param userInfo ユーザ情報
     * @return 入力エラーがない場合：成功レスポンス、入力エラーがある場合：エラーレスポンス
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "TODO新規作成",
            notes="**[概要]**  \r\nログインしているユーザでTODOを作成する。\r\n\r\n**[事前条件]**\r\n- 任意のユーザでログインしている\r\n\r\n**[事後条件]**\r\n - 新規にTODOが作成される\r\n - 作成されたTODOのIDが返却される\r\n"
    )
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
