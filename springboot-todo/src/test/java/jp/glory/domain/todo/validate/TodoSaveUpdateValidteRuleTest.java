package jp.glory.domain.todo.validate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.repository.TodoRepositoryMock;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.value.UserId;
import jp.glory.test.validate.ValidateAssert;

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
