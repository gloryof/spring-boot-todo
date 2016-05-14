package jp.glory.infra.db.todo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.entity.Todos;
import jp.glory.domain.todo.repository.TodoRepository;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;
import jp.glory.infra.db.todo.dao.TodosDao;
import jp.glory.infra.db.todo.dao.TodosDetailDao;
import jp.glory.infra.db.todo.entity.TodoInfo;
import jp.glory.infra.db.todo.entity.TodosDetailTable;
import jp.glory.infra.db.todo.entity.TodosTable;

/**
 * TODOリポジトリ.
 * @author Junki Yamada
 *
 */
@Repository
public class TodoRepositoryDbImpl implements TodoRepository {

    /**
     * TODO概要のDAO.
     */
    private final TodosDao todosDao;

    /**
     * TODO詳細のDAO.
     */
    private final TodosDetailDao detailDao;

    /**
     * コンストラクタ.
     * @param todosDao TODO概要のDAO
     * @param detailDao TODO詳細のDAO
     */
    @Autowired
    public TodoRepositoryDbImpl(final TodosDao todosDao, final TodosDetailDao detailDao) {

        this.todosDao = todosDao;
        this.detailDao = detailDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Todo> findBy(final TodoId todoId) {

        return todosDao.selectById(todoId.getValue())
                .map(v -> Optional.of(convertToEntity(v)))
                .orElse(Optional.empty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Todos findTodosBy(final UserId userId) {

        final List<Todo> todoList = todosDao.selectByUserId(userId.getValue()).stream()
                                        .map(this::convertToEntity)
                                        .collect(Collectors.toList());

        return new Todos(todoList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TodoId save(final Todo todo) {

        final TodoId savedTodoId;
        if (!todo.isRegistered()) {

            savedTodoId = registerNewTodo(todo);
        } else {

            savedTodoId = todo.getId();

            updateTodo(todo);
        }

        return savedTodoId;
    }

    /**
     * 新しいTODOを登録する.
     * @param todo TODOエンティティ
     * @return 新しいTODOのID
     */
    private TodoId registerNewTodo(final Todo todo) {

        final TodoId newTodoId = new TodoId(todosDao.selectTodoId());

        todosDao.insert(convertToSummaryRecord(newTodoId, todo));

        if (!todo.getMemo().getValue().isEmpty()) {

            detailDao.insert(convertToDetailRecord(newTodoId, todo));
        }

        return newTodoId;
    }

    /**
     * TODOを更新する.
     * @param todo TODOエンティティ
     */
    private void updateTodo(final Todo todo) {

        todosDao.update(convertToSummaryRecord(todo.getId(), todo));
        updateTodoDetail(todo);
    }

    /**
     * todos_detailテーブルを更新する.
     * @param todo TODOエンティティ
     */
    private void updateTodoDetail(final Todo todo) {

        final TodosDetailTable record = convertToDetailRecord(todo.getId(), todo);

        if (todo.getMemo().getValue().isEmpty()) {

            detailDao.delete(record);
            return;
        }

        final Optional<TodosDetailTable> optDetail = detailDao.selectById(todo.getId().getValue());

        if (optDetail.isPresent()) {
            
            detailDao.update(record);
            return;
        }

        detailDao.insert(record);
    }

    /**
     * レコードからエンティティに変換する.
     * @param record レコード
     * @return エンティティ
     */
    private Todo convertToEntity(final TodoInfo record) {

        final Todo todo = new Todo(new TodoId(record.getTodoId()), new UserId(record.getUserId()),
                new Summary(record.getSummary()), new Memo(record.getMemo()), record.isCompleted());
        todo.version(record.getVersion());

        return todo;
    }

    /**
     * エンティティからtodosテーブルのレコードに変換する.
     * @param todoId TODOのID
     * @param todo TODOエンティティ
     * @return レコード
     */
    private TodosTable convertToSummaryRecord(final TodoId todoId, final Todo todo) {

        final TodosTable record = new TodosTable();

        record.setTodoId(todoId.getValue());
        record.setUserId(todo.getUserId().getValue());
        record.setSummary(todo.getSummary().getValue());
        record.setCompleted(todo.isCompleted());
        record.setVersion(todo.getEntityVersion());

        return record;
    }

    /**
     * エンティティからtodos_detailテーブルのレコードに変換する.
     * @param todoId TODOのID
     * @param todo TODOエンティティ
     * @return レコード
     */
    private TodosDetailTable convertToDetailRecord(final TodoId todoId, final Todo todo) {

        final TodosDetailTable record = new TodosDetailTable();

        record.setTodoId(todoId.getValue());
        record.setMemo(todo.getMemo().getValue());

        return record;
    }
}
