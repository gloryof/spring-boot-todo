package jp.glory.todo.context.todo.web.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.base.web.UserInfo;
import jp.glory.todo.context.base.web.api.ApiPaths;
import jp.glory.todo.context.base.web.api.OriginalOperationDoc;
import jp.glory.todo.context.base.web.api.OriginalRequestlDoc;
import jp.glory.todo.context.base.web.api.response.InvalidErrorResponse;
import jp.glory.todo.context.base.web.exception.InvalidRequestException;
import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.todo.domain.value.Memo;
import jp.glory.todo.context.todo.domain.value.Summary;
import jp.glory.todo.context.todo.domain.value.TodoId;
import jp.glory.todo.context.todo.usecase.SaveTodo;
import jp.glory.todo.context.todo.usecase.SearchTodo;
import jp.glory.todo.context.todo.web.api.request.TodoDetailSaveRequest;
import jp.glory.todo.context.todo.web.api.response.TodoDetailResponse;

/**
 * TODO詳細のAPI.
 * @author Junki Yamada
 *
 */
@RestController
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
@RequestMapping(path = ApiPaths.Todo.PATH + "/{id}")
@Api(tags = {"Todo Detail Operation"})
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
     * コンストラクタ.
     * @param searchTodo TODO検索
     * @param saveTodo TODO保存
     */
    @Autowired
    public TodoDetail(final SearchTodo searchTodo, final SaveTodo saveTodo) {

        this.searchTodo = searchTodo;
        this.saveTodo = saveTodo;
    }

    @OriginalOperationDoc(
            name = "TODO詳細取得",
            summary = "TODOの詳細を取得する",
            preconditions = {
                                "対象のTODOがすでに登録されている"
                            },
            postcondition = {
                                "TODOが取得できる"
                            }
    )
    @ApiResponses({
        @ApiResponse(code = 404, message = "対象のTODOが存在しない場合")
    })
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TodoDetailResponse> view(@PathVariable @OriginalRequestlDoc(name = "TODOのID", key = true) final long id) {

        final TodoId todoId = new TodoId(id);

        final Optional<Todo> optTodo = searchTodo.searchById(todoId);

        if (!optTodo.isPresent()) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new TodoDetailResponse(optTodo.get()), HttpStatus.OK);
    }

    @OriginalOperationDoc(
            name = "TODO更新",
            summary = "TODOを更新する",
            preconditions = {
                                "対象のTODOがすでに登録されている"
                            },
            postcondition = {
                                "入力した内容で保存される"
                            }
    )
    @ApiResponses({
        @ApiResponse(code = 400, message = "入力不備がある場合", response = InvalidErrorResponse.class),
        @ApiResponse(code = 404, message = "対象のTODOが存在しない場合"),
    })
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> save(@PathVariable @OriginalRequestlDoc(name = "TODOのID", key = true) final long id,
            final TodoDetailSaveRequest request,
            @AuthenticationPrincipal final UserInfo userInfo) {

        if (!isExists(id)) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return executeSaving(id, request, userInfo);
    }

    /**
     * 保存の実行をする.
     * @param id TODOのID
     * @param request 保存リクエスト
     * @param userInfo ユーザ情報
     * @return レスポンス
     */
    private ResponseEntity<Object> executeSaving(final long id, final TodoDetailSaveRequest request,  final UserInfo userInfo) {

        final Todo saveTargetTodo = new Todo(new TodoId(id), userInfo.getUserId(),
                new Summary(request.getSummary()));
        saveTargetTodo.setMemo(new Memo(request.getMemo()));

        if (request.isCompleted()) {

            saveTargetTodo.markAsComplete();
        } else {

            saveTargetTodo.unmarkFromComplete();
        }

        saveTargetTodo.version(request.getVersion());
        final SaveTodo.Result result = saveTodo.save(saveTargetTodo);

        final ValidateErrors errors = result.getErrors();
        if (errors.hasError()) {

            throw new InvalidRequestException(errors);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
