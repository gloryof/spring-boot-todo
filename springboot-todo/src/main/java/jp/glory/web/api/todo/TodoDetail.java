package jp.glory.web.api.todo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
import jp.glory.web.api.todo.request.TodoDetailSaveRequest;
import jp.glory.web.api.todo.response.TodoDetailResponse;
import jp.glory.web.api.todo.response.TodoDetailSaveErrorResponse;
import jp.glory.web.session.UserInfo;

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
     * TODO保存.
     */
    private final SaveTodo saveTodo;

    /**
     * ユーザ情報.
     */
    private final UserInfo userInfo;

    /**
     * コンストラクタ.
     * @param searchTodo TODO検索
     * @param userInfo ユーザ情報
     */
    @Autowired
    public TodoDetail(final SearchTodo searchTodo, final SaveTodo saveTodo, final UserInfo userInfo) {

        this.searchTodo = searchTodo;
        this.saveTodo = saveTodo;
        this.userInfo = userInfo;
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

        if (!optTodo.isPresent()) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new TodoDetailResponse(optTodo.get()), HttpStatus.OK);
    }

    /**
     * TODOを保存する.
     * @param id TODOのID
     * @param request TODO保存リクエスト
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<TodoDetailSaveErrorResponse> save(@PathVariable final long id, final TodoDetailSaveRequest request) {

        if (!isExists(id)) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final SaveTodo.Result result = saveTodo.save(new Todo(new TodoId(id), userInfo.getUserId(),
                new Summary(request.getSummary()), new Memo(request.getMemo()), request.isCompleted()));

        final ValidateErrors errors = result.getErrors();
        if (!errors.hasError()) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        final TodoDetailSaveErrorResponse response = new TodoDetailSaveErrorResponse();
        final List<String> errorMessages = 
                errors.toList().stream()
                    .map(ValidateError::getMessage)
                    .collect(Collectors.toList());
        response.getErrors().addAll(errorMessages);
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * 対象となるTODOが存在するかをチェックする.
     * @param id ID
     * @return 存在する場合：true、存在しない場合：false
     */
    private boolean isExists(final long id) {

        return searchTodo.searchById(new TodoId(id)).isPresent();
    }
}
