package jp.glory.domain.todo.validate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.test.util.TestUtil;
import jp.glory.test.validate.ValidateErrorsHelper;

@RunWith(Enclosed.class)
public class TodoValidateRuleTest {

    public static class 正常な値が入力されている場合 {

        private TodoValidateRule sut = null;

        @Before
        public void setUp() {

            final Todo todo = new Todo(new TodoId(100l), new Summary("概要"), new Memo("メモ"), false);
            sut = new TodoValidateRule(todo);
        }

        @Test
        public void validateを実行しても入力チェックエラーにならない() {

            final ValidateErrors actualErros = sut.validate();

            assertThat(actualErros.hasError(), is(false));
        }
    }

    public static class 概要に入力不備がある場合 {

        private TodoValidateRule sut = null;

        @Before
        public void setUp() {

            final Todo todo = new Todo(new TodoId(100l), new Summary(""), new Memo("メモ"), false);
            sut = new TodoValidateRule(todo);
        }

        @Test
        public void validateを実行すると入力チェックエラーにる() {

            final ValidateErrors actualErros = sut.validate();

            assertThat(actualErros.hasError(), is(true));

            final List<ValidateError> errorList = new ArrayList<>();

            errorList.add(new ValidateError(ErrorInfo.Required, Summary.LABEL));

            final ValidateErrorsHelper helper = new ValidateErrorsHelper(actualErros);
            helper.assertErrors(errorList);
        }
    }

    public static class メモに入力不備がある場合 {

        private TodoValidateRule sut = null;

        @Before
        public void setUp() {

            final Todo todo = new Todo(new TodoId(100l), new Summary("概要"), new Memo(TestUtil.repeat("a", 1001)),
                    false);
            sut = new TodoValidateRule(todo);
        }

        @Test
        public void validateを実行すると入力チェックエラーにる() {

            final ValidateErrors actualErros = sut.validate();

            assertThat(actualErros.hasError(), is(true));

            final List<ValidateError> errorList = new ArrayList<>();

            errorList.add(new ValidateError(ErrorInfo.MaxLengthOver, Memo.LABEL, 1000));

            final ValidateErrorsHelper helper = new ValidateErrorsHelper(actualErros);
            helper.assertErrors(errorList);
        }
    }
}