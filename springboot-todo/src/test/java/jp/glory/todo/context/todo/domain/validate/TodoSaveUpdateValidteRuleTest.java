package jp.glory.todo.context.todo.domain.validate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.todo.domain.repository.TodoRepositoryMock;
import jp.glory.todo.context.todo.domain.validate.TodoSaveUpdateValidteRule;
import jp.glory.todo.context.todo.domain.value.Memo;
import jp.glory.todo.context.todo.domain.value.Summary;
import jp.glory.todo.context.todo.domain.value.TodoId;
import jp.glory.todo.context.user.domain.entity.User;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.test.validate.ValidateAssert;

class TodoSaveUpdateValidteRuleTest {

    private TodoSaveUpdateValidteRule sut = null;
    private TodoRepositoryMock repositoryMock = null;
    private ValidateErrors actualErrors = null;
    private Todo saveTodo = null;

    @DisplayName("すべての値が正常に設定されている場合")
    @Nested
    class WhenValueValid {

        @BeforeEach
        void setUp() {

            final Todo beforeTodo = new Todo(new TodoId(100l), new UserId(200l), new Summary("タイトル"), new Memo("メモ"), true);
            saveTodo = new Todo(beforeTodo.getId(), beforeTodo.getUserId(), new Summary("あたらいしタイトル"), new Memo("新しいメモ"), false);

            repositoryMock = new TodoRepositoryMock();
            repositoryMock.addTestData(beforeTodo);

            sut =  new TodoSaveUpdateValidteRule(repositoryMock, saveTodo);

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

            final Todo beforeTodo = new Todo(new TodoId(100l), new UserId(200l), new Summary("タイトル"), new Memo("メモ"), true);
            saveTodo = new Todo(TodoId.notNumberingValue(), UserId.notNumberingValue(), Summary.empty(), Memo.empty(), false);

            repositoryMock = new TodoRepositoryMock();
            repositoryMock.addTestData(beforeTodo);

            sut =  new TodoSaveUpdateValidteRule(repositoryMock, saveTodo);

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
            expectedErrors.add(new ValidateError(ErrorInfo.Required, User.LABEL));
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

            saveTodo = new Todo(new TodoId(100l), new UserId(200l), new Summary("タイトル"), new Memo("メモ"), false);

            repositoryMock = new TodoRepositoryMock();
            sut =  new TodoSaveUpdateValidteRule(repositoryMock, saveTodo);

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

            final Todo beforeTodo = new Todo(new TodoId(100l), new UserId(200l), new Summary("タイトル"), new Memo("メモ"), true);
            saveTodo = new Todo(beforeTodo.getId(), new UserId(300l), beforeTodo.getSummary(), beforeTodo.getMemo(),
                    beforeTodo.isCompleted());

            repositoryMock = new TodoRepositoryMock();
            repositoryMock.addTestData(beforeTodo);

            sut =  new TodoSaveUpdateValidteRule(repositoryMock, saveTodo);

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
