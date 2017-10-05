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
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.value.UserId;
import jp.glory.test.util.TestUtil;
import jp.glory.test.validate.ValidateAssert;

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
