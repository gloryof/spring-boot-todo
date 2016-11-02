package jp.glory.domain.todo.validate;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

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
import jp.glory.test.validate.ValidateErrorsHelper;

@RunWith(Enclosed.class)
public class TodoSaveUpdateValidteRuleTest {

    public static class すべての値が正常に設定されている場合 {

        private TodoSaveUpdateValidteRule sut = null;

        private TodoRepositoryMock repositoryMock = null;
        private Todo saveTodo = null;

        private ValidateErrors actualErrors = null;

        @Before
        public void setUp() {

            final Todo beforeTodo = new Todo(new TodoId(100l), new UserId(200l), new Summary("タイトル"), new Memo("メモ"), true);
            saveTodo = new Todo(beforeTodo.getId(), beforeTodo.getUserId(), new Summary("あたらいしタイトル"), new Memo("新しいメモ"), false);

            repositoryMock = new TodoRepositoryMock();
            repositoryMock.addTestData(beforeTodo);

            sut =  new TodoSaveUpdateValidteRule(repositoryMock, saveTodo);

            actualErrors = sut.validate();
        }

        @Test
        public void validateを実行しても入力チェックエラーにならない() {

            Assert.assertThat(actualErrors.hasError(), CoreMatchers.is(false));
        }
    }

    public static class すべての値が未設定の場合 {

        private TodoSaveUpdateValidteRule sut = null;

        private TodoRepositoryMock repositoryMock = null;
        private Todo saveTodo = null;

        private ValidateErrors actualErrors = null;

        @Before
        public void setUp() {

            final Todo beforeTodo = new Todo(new TodoId(100l), new UserId(200l), new Summary("タイトル"), new Memo("メモ"), true);
            saveTodo = new Todo(TodoId.notNumberingValue(), UserId.notNumberingValue(), Summary.empty(), Memo.empty(), false);

            repositoryMock = new TodoRepositoryMock();
            repositoryMock.addTestData(beforeTodo);

            sut =  new TodoSaveUpdateValidteRule(repositoryMock, saveTodo);

            actualErrors = sut.validate();
        }

        @Test
        public void validateで必須項目がエラーになる() {

            Assert.assertThat(actualErrors.hasError(), CoreMatchers.is(true));

            final List<ValidateError> errorList = new ArrayList<>();

            errorList.add(new ValidateError(ErrorInfo.NotRegister, Todo.LABEL));
            errorList.add(new ValidateError(ErrorInfo.Required, User.LABEL));
            errorList.add(new ValidateError(ErrorInfo.Required, Summary.LABEL));

            final ValidateErrorsHelper helper = new ValidateErrorsHelper(actualErrors);
            helper.assertErrors(errorList);
        }
    }

    public static class 対象のTODOが存在しない場合 {

        private TodoSaveUpdateValidteRule sut = null;

        private TodoRepositoryMock repositoryMock = null;
        private Todo saveTodo = null;

        private ValidateErrors actualErrors = null;

        @Before
        public void setUp() {

            saveTodo = new Todo(new TodoId(100l), new UserId(200l), new Summary("タイトル"), new Memo("メモ"), false);

            repositoryMock = new TodoRepositoryMock();
            sut =  new TodoSaveUpdateValidteRule(repositoryMock, saveTodo);

            actualErrors = sut.validate();
        }

        @Test
        public void validateで登録されていないTODOとしてエラーになる() {

            Assert.assertThat(actualErrors.hasError(), CoreMatchers.is(true));

            final List<ValidateError> errorList = new ArrayList<>();

            errorList.add(new ValidateError(ErrorInfo.NotRegister, Todo.LABEL));

            final ValidateErrorsHelper helper = new ValidateErrorsHelper(actualErrors);
            helper.assertErrors(errorList);
        }
    }

    public static class 異なるユーザの場合 {

        private TodoSaveUpdateValidteRule sut = null;

        private TodoRepositoryMock repositoryMock = null;
        private Todo saveTodo = null;

        private ValidateErrors actualErrors = null;

        @Before
        public void setUp() {

            final Todo beforeTodo = new Todo(new TodoId(100l), new UserId(200l), new Summary("タイトル"), new Memo("メモ"), true);
            saveTodo = new Todo(beforeTodo.getId(), new UserId(300l), beforeTodo.getSummary(), beforeTodo.getMemo(),
                    beforeTodo.isCompleted());

            repositoryMock = new TodoRepositoryMock();
            repositoryMock.addTestData(beforeTodo);

            sut =  new TodoSaveUpdateValidteRule(repositoryMock, saveTodo);

            actualErrors = sut.validate();
        }

        @Test
        public void validateで異なるユーザとしてエラーになる() {

            Assert.assertThat(actualErrors.hasError(), CoreMatchers.is(true));

            final List<ValidateError> errorList = new ArrayList<>();

            errorList.add(new ValidateError(ErrorInfo.SavedToOtherUser));

            final ValidateErrorsHelper helper = new ValidateErrorsHelper(actualErrors);
            helper.assertErrors(errorList);
        }
    }
}
