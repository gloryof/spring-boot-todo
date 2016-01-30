package jp.glory.domain.common.error;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static jp.glory.test.validate.ValidateMatcher.*;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ValidateErrorsTest {

    public static class 初期状態でエラーがない場合 {

        private ValidateErrors sut;

        @Before
        public void setUp() {

            sut = new ValidateErrors();
        }

        @Test
        public void toListで空のリストが返却される() {

            final List<ValidateError> actual = sut.toList();
            assertThat(actual, is(not(nullValue())));
            assertThat(actual.isEmpty(), is(true));
        }

        @Test(expected = UnsupportedOperationException.class)
        public void toListの戻り値リストは不変() {

            final List<ValidateError> actual = sut.toList();

            actual.add(null);
        }

        @Test
        public void hasErrorでfalseが返却される() {

            assertThat(sut.hasError(), is(false));
        }

        @Test
        public void addでエラーが追加される() {

            final Object[] messageParam = {"テスト"};
            final ValidateError expectError = new ValidateError(ErrorInfo.Required, messageParam);

            sut.add(expectError);
            assertThat(sut.hasError(), is(true));

            final List<ValidateError> actualList = sut.toList();
            assertThat(actualList.size(), is(1));

            final ValidateError actual = actualList.get(0);
            assertThat(actual, is(validatedBy(expectError)));
        }

        @Test
        public void addAllで全てのエラーが追加される() {

            final ValidateErrors expectedErrors = new ValidateErrors();

            expectedErrors.add(new ValidateError(ErrorInfo.Required, "テスト"));
            expectedErrors.add(new ValidateError(ErrorInfo.MaxLengthOver, "テスト2", 250));
            expectedErrors.add(new ValidateError(ErrorInfo.InvalidCharacters, "テスト3", "半角"));

            sut.addAll(expectedErrors);

            assertThat(sut.hasError(), is(true));

            final List<ValidateError> expectedList = expectedErrors.toList();
            final List<ValidateError> actualList = sut.toList();

            assertThat(actualList.size(), is(expectedList.size()));

            IntStream.range(1, expectedList.size()).forEach(
                i -> {
                    final ValidateError expectedError = expectedList.get(i);
                    final ValidateError actualError = actualList.get(i);
                    assertThat(actualError, is(validatedBy(expectedError)));
                });
        }
    }

    public static class エラーが1件追加されている場合 {

        private ValidateErrors sut;
        private final Object[] messageParam = {"テスト"};
        private final ValidateError baseError = new ValidateError(ErrorInfo.Required, messageParam);

        @Before
        public void setUp() {

            sut = new ValidateErrors();
            sut.add(baseError);
        }

        @Test
        public void 同一のエラーを追加しても件数は変わらない() {

            sut.add(baseError);

            assertThat(sut.toList().size(), is(1));
        }

        @Test
        public void 異なるエラーの場合は件数が増える() {

            final Object[] otherParam = {"テスト2"};
            final ValidateError otherError = new ValidateError(ErrorInfo.Required, otherParam);
            sut.add(otherError);

            assertThat(sut.toList().size(), is(2));
        }
    }
}