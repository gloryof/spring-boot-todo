package jp.glory.domain.todo.value;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.test.util.TestUtil;
import jp.glory.test.validate.ValidateMatcher;

@RunWith(Enclosed.class)
public class MemoTest {

    public static class emptyのテスト {

        @Test
        public void ブランクが返却される() {

            final Memo actual = Memo.empty();

            assertThat(actual.getValue(), is(""));
        }
    }

    public static class 正常な値が設定されている場合 {

        private Memo sut = null;

        @Before
        public void setUp() {

            sut = new Memo("テスト");
        }

        @Test
        public void validateを行っても入力チェックエラーにならない() {

            final ValidateErrors actualErrors = sut.validate();

            assertThat(actualErrors.hasError(), is(false));
        }
    }

    public static class 値が未設定の場合 {

        private Memo sut = null;

        @Before
        public void setUp() {

            sut = new Memo("");
        }

        @Test
        public void validateを行うと必須チェックエラーにならない() {

            final ValidateErrors actualErrors = sut.validate();

            assertThat(actualErrors.hasError(), is(false));
        }
    }

    public static class 値が1000文字の場合 {

        private Memo sut = null;

        @Before
        public void setUp() {

            sut = new Memo(TestUtil.repeat("a", 1000));
        }

        @Test
        public void validateを行っても入力チェックエラーにならない() {

            final ValidateErrors actualErrors = sut.validate();

            assertThat(actualErrors.hasError(), is(false));
        }
    }

    public static class 値が1001文字以上の場合 {

        private Memo sut = null;

        @Before
        public void setUp() {

            sut = new Memo(TestUtil.repeat("a", 1001));
        }

        @Test
        public void validateを行うと文字数オーバーエラーになる() {

            final ValidateErrors actualErrors = sut.validate();

            assertThat(actualErrors.hasError(), is(true));

            final List<ValidateError> actualList = actualErrors.toList();

            final ValidateError expectedError = new ValidateError(ErrorInfo.MaxLengthOver, Memo.LABEL, "1,000");
            assertThat(actualList.size(), is(1));
            assertThat(actualList.get(0), is(ValidateMatcher.validatedBy(expectedError)));
        }
    }
}