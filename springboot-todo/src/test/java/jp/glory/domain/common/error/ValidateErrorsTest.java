package jp.glory.domain.common.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.test.validate.ValidateAssert;

class ValidateErrorsTest {

    private ValidateErrors sut;

    @DisplayName("初期状態でエラーがない場合")
    @Nested
    class NotExistErrors {

        @BeforeEach
        void setUp() {

            sut = new ValidateErrors();
        }

        @DisplayName("toListで空のリストが返却される")
        @Test
        void testToList() {

            final List<ValidateError> actual = sut.toList();
            assertNotNull(actual);
            assertTrue(actual.isEmpty());
        }

        @DisplayName("toListの戻り値リストは不変")
        @Test
        void testToListReturnImmutable() {

            final List<ValidateError> actual = sut.toList();

            assertThrows(UnsupportedOperationException.class, () -> actual.add(null));
        }

        @DisplayName("hasErrorでfalseが返却される")
        @Test
        void testHasError() {

            assertFalse(sut.hasError());
        }

        @DisplayName("addでエラーが追加される")
        @Test
        void testAdd() {

            final Object[] messageParam = {"テスト"};
            final ValidateError expectError = new ValidateError(ErrorInfo.Required, messageParam);

            sut.add(expectError);
            assertTrue(sut.hasError());

            final ValidateAssert validate = new ValidateAssert(expectError, sut);
            validate.assertAll();
        }

        @DisplayName("addAllで全てのエラーが追加される")
        @Test
        void testAddAll() {

            final ValidateErrors expectedErrors = new ValidateErrors();

            expectedErrors.add(new ValidateError(ErrorInfo.Required, "テスト"));
            expectedErrors.add(new ValidateError(ErrorInfo.MaxLengthOver, "テスト2", 250));
            expectedErrors.add(new ValidateError(ErrorInfo.InvalidCharacters, "テスト3", "半角"));

            sut.addAll(expectedErrors);

            assertTrue(sut.hasError());

            final ValidateAssert validate = new ValidateAssert(expectedErrors, sut);
            validate.assertAll();
        }
    }

    @DisplayName("エラーが1件追加されている場合")
    @Nested
    class ExistError {

        private ValidateErrors sut;
        private final Object[] messageParam = {"テスト"};
        private final ValidateError baseError = new ValidateError(ErrorInfo.Required, messageParam);

        @BeforeEach
        void setUp() {

            sut = new ValidateErrors();
            sut.add(baseError);
        }

        @DisplayName("同一のエラーを追加しても件数は変わらない")
        @Test
        void testAddSameValue() {

            sut.add(baseError);

            assertEquals(1, sut.toList().size());
        }

        @DisplayName("異なるエラーの場合は件数が増える")
        @Test
        void testAddDiffrentValue() {

            final Object[] otherParam = {"テスト2"};
            final ValidateError otherError = new ValidateError(ErrorInfo.Required, otherParam);
            sut.add(otherError);

            assertEquals(2, sut.toList().size());
        }
    }
}