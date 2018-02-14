package jp.glory.todo.context.todo.domain.specification;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.todo.domain.repository.TodoRepository;
import jp.glory.todo.context.todo.domain.value.Memo;
import jp.glory.todo.context.todo.domain.value.Summary;
import jp.glory.todo.context.todo.domain.value.TodoId;
import jp.glory.todo.context.user.domain.entity.RegisteredUser;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.test.validate.ValidateAssert;

class TodoUpdateSpecTest {

    private TodoUpdateSpec sut = null;
    private TodoRepository mock = null;
    private ValidateErrors actualErrors = null;
    private Todo saveTodo = null;
    private Todo beforeTodo = null;

    @BeforeEach
    void setUp() {

        mock = mock(TodoRepository.class);

        beforeTodo = new Todo(new TodoId(100l), new UserId(200l), new Summary("タイトル"));
        beforeTodo.setMemo(new Memo("メモ"));
        beforeTodo.unmarkFromComplete();
    }

    @DisplayName("すべての値が正常に設定されている場合")
    @Nested
    class WhenValueValid {

        @BeforeEach
        void setUp() {

            saveTodo = new Todo(beforeTodo.getId(), beforeTodo.getUserId(), new Summary("新しいタイトル"));
            saveTodo.setMemo(new Memo("新しいメモ"));
            saveTodo.markAsComplete();

            when(mock.findBy(any(TodoId.class))).thenReturn(Optional.of(beforeTodo));

            sut =  new TodoUpdateSpec(mock, saveTodo);

            actualErrors = sut.validate();
        }

        @DisplayName("validateを実行しても入力チェックエラーにならない")
        @Test
        void testHasError() {

            assertFalse(actualErrors.hasError());
        }
    }

    @DisplayName("すべての値が未設定の場合")
    @Nested
    class WhenAllValueIsNotSet {

        @BeforeEach
        void setUp() {

            saveTodo = new Todo(TodoId.notNumberingValue(), UserId.notNumberingValue(), Summary.empty());
            saveTodo.setMemo(Memo.empty());
            saveTodo.unmarkFromComplete();

            when(mock.findBy(any(TodoId.class))).thenReturn(Optional.of(beforeTodo));

            sut =  new TodoUpdateSpec(mock, saveTodo);

            actualErrors = sut.validate();
        }

        @DisplayName("hasErrorはtrue")
        @Test
        void testHasError() {

            assertTrue(actualErrors.hasError());
        }

        @DisplayName("各エラー情報が設定される")
        @Test
        void assertErrors() {

            final ValidateErrors expectedErrors = new ValidateErrors();

            expectedErrors.add(new ValidateError(ErrorInfo.NotRegister, Todo.LABEL));
            expectedErrors.add(new ValidateError(ErrorInfo.Required, RegisteredUser.LABEL));
            expectedErrors.add(new ValidateError(ErrorInfo.Required, Summary.LABEL));

            final ValidateAssert validate = new ValidateAssert(expectedErrors, actualErrors);
            validate.assertAll();
        }
    }

    @DisplayName("対象のTODOが存在しない場合")
    @Nested
    class WhenTodoNotExists {

        @BeforeEach
        void setUp() {

            saveTodo = new Todo(beforeTodo.getId(), beforeTodo.getUserId(), new Summary("新しいタイトル"));
            saveTodo.setMemo(new Memo("新しいメモ"));
            saveTodo.markAsComplete();

            sut =  new TodoUpdateSpec(mock, saveTodo);

            actualErrors = sut.validate();
        }

        @DisplayName("hasErrorはtrue")
        @Test
        void testHasError() {

            assertTrue(actualErrors.hasError());
        }

        @DisplayName("validateで登録されていないTODOとしてエラーになる")
        @Test
        void assertErrors() {

            final ValidateErrors expectedErrors = new ValidateErrors();

            expectedErrors.add(new ValidateError(ErrorInfo.NotRegister, Todo.LABEL));

            final ValidateAssert validate = new ValidateAssert(expectedErrors, actualErrors);
            validate.assertAll();
        }
    }

    @DisplayName("異なるユーザの場合")
    @Nested
    class WhenOtherUser {

        @BeforeEach
        void setUp() {

            saveTodo = new Todo(beforeTodo.getId(), new UserId(300l), new Summary("新しいタイトル"));
            saveTodo.setMemo(new Memo("新しいメモ"));
            saveTodo.markAsComplete();

            when(mock.findBy(any(TodoId.class))).thenReturn(Optional.of(beforeTodo));

            sut =  new TodoUpdateSpec(mock, saveTodo);
            actualErrors = sut.validate();
        }

        @DisplayName("hasErrorはtrue")
        @Test
        void testHasError() {

            assertTrue(actualErrors.hasError());
        }

        @DisplayName("各エラー情報が設定される")
        @Test
        void assertErrors() {

            final ValidateErrors expectedErrors = new ValidateErrors();

            expectedErrors.add(new ValidateError(ErrorInfo.SavedToOtherUser));

            final ValidateAssert validate = new ValidateAssert(expectedErrors, actualErrors);
            validate.assertAll();
        }
    }
}
