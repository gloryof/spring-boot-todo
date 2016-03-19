package jp.glory.web.api.todo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.usecase.todo.SaveTodo;
import jp.glory.usecase.todo.SearchTodo;
import jp.glory.web.api.ApiPaths;
import jp.glory.web.api.todo.request.TodoCreateRequest;
import jp.glory.web.api.todo.response.TodoCreateErrorResponse;
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
     * ユーザ情報.
     */
    private final UserInfo userInfo;

    /**
     * コンストラクタ.
     * 
     * @param searchTodo TODO検索
     * @param saveTodo TODO保存
     * @param userInfo ユーザ情報
     */
    @Autowired
    public TodoList(final SearchTodo searchTodo, final SaveTodo saveTodo, final UserInfo userInfo) {

        this.searchTodo = searchTodo;
        this.saveTodo = saveTodo;
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

    /**
     * TODOを新規作成する.
     * @param request 作成リクエスト
     * @return 入力エラーがない場合：成功レスポンス、入力エラーがある場合：エラーレスポンス
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> save(final TodoCreateRequest request) {

        final SaveTodo.Result result = saveTodo.save(new Todo(TodoId.notNumberingValue(), userInfo.getUserId(),
                new Summary(request.getSummary()), new Memo(request.getMemo()), request.isCompleted()));

        final ValidateErrors errors = result.getErrors();
        if (!errors.hasError()) {

            return new ResponseEntity<>(new TodoCreateSuccessResponse(result.getSavedTodoId()), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(createErrorResponse(errors),  HttpStatus.BAD_REQUEST);
    }

    /**
     * 入力チェック時のエラーレスポンスを作成する.
     * 
     * @param errors
     *            入力チェックエラー
     * @return エラーレスポンス
     */
    private TodoCreateErrorResponse createErrorResponse(final ValidateErrors errors) {

        final TodoCreateErrorResponse response = new TodoCreateErrorResponse();
        final List<String> errorMessages = errors.toList().stream().map(ValidateError::getMessage)
                .collect(Collectors.toList());
        response.getErrors().addAll(errorMessages);

        return response;
    }

}
