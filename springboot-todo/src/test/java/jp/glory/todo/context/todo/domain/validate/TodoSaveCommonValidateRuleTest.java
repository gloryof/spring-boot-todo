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
import jp.glory.todo.context.todo.domain.validate.TodoSaveCommonValidateRule;
import jp.glory.todo.context.todo.domain.value.Memo;
import jp.glory.todo.context.todo.domain.value.Summary;
import jp.glory.todo.context.todo.domain.value.TodoId;
import jp.glory.todo.context.user.domain.entity.User;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.test.util.TestUtil;
import jp.glory.todo.test.validate.ValidateAssert;

class TodoSaveCommonValidateRuleTest {

    @DisplayName("正常な値が入力されている場合")
    @Nested
    class WhenValidValue {

        private TodoSaveCommonValidateRule sut = null;

        @BeforeEach
        void setUp() {

            final Todo todo = new Todo(new TodoId(100l), new UserId(1000l), new Summary("概要"), new Memo("メモ"), false);
            sut = new TodoSaveCommonValidateRule(todo);
        }

        @DisplayName("validateを実行しても入力チェックエラーにならない")
        @Test
        void testValidate() {

            final ValidateErrors actualErros = sut.validate();

            assertFalse(actualErros.hasError());
        }
    }

    @DisplayName("ユーザID未設定の場合")
    @Nested
    class WhenUserIdNotSet {

        private TodoSaveCommonValidateRule sut = null;

        @BeforeEach
        void setUp() {

            final Todo todo = new Todo(new TodoId(100l), UserId.notNumberingValue(), new Summary("概要"), new Memo("メモ"),
                    false);
            sut = new TodoSaveCommonValidateRule(todo);
        }

        @DisplayName("validateを実行すると入力チェックエラーになる")
        @Test
        void testValidate() {

            final ValidateErrors actualErros = sut.validate();

            assertTrue(actualErros.hasError());

            final ValidateErrors expectedErrors = new ValidateErrors();

            expectedErrors.add(new ValidateError(ErrorInfo.Required, User.LABEL));

            final ValidateAssert validate = new ValidateAssert(expectedErrors, actualErros);
            validate.assertAll();
        }
    }

    @DisplayName("概要に入力不備がある場合")
    @Nested
    class WhenSummaryInvalid {

        private TodoSaveCommonValidateRule sut = null;

        @BeforeEach
        void setUp() {

            final Todo todo = new Todo(new TodoId(100l), new UserId(1000l), new Summary(""), new Memo("メモ"), false);
            sut = new TodoSaveCommonValidateRule(todo);
        }

        @DisplayName("validateを実行すると入力チェックエラーにる")
        @Test
        void testValidate() {

            final ValidateErrors actualErros = sut.validate();

            assertTrue(actualErros.hasError());

            final ValidateErrors expectedErrors = new ValidateErrors();
            expectedErrors.add(new ValidateError(ErrorInfo.Required, Summary.LABEL));

            final ValidateAssert validate = new ValidateAssert(expectedErrors, actualErros);
            validate.assertAll();
        }
    }

    @DisplayName("メモに入力不備がある場合")
    @Nested
    class WhenMemoInvalid {

        private TodoSaveCommonValidateRule sut = null;

        @BeforeEach
        void setUp() {

            final Todo todo = new Todo(new TodoId(100l), new UserId(1000l), new Summary("概要"),
                    new Memo(TestUtil.repeat("a", 1001)), false);
            sut = new TodoSaveCommonValidateRule(todo);
        }

        @DisplayName("validateを実行すると入力チェックエラーにる")
        @Test
        void testValidate() {

            final ValidateErrors actualErros = sut.validate();

            assertTrue(actualErros.hasError());

            final ValidateErrors expectedErrors = new ValidateErrors();

            expectedErrors.add(new ValidateError(ErrorInfo.MaxLengthOver, Memo.LABEL, 1000));

            final ValidateAssert validate = new ValidateAssert(expectedErrors, actualErros);
            validate.assertAll();
        }
    }
}
