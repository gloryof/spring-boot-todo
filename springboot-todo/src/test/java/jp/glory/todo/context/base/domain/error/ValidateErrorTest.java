package jp.glory.todo.context.base.domain.error;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;

class ValidateErrorTest {
    
    private ValidateError sut;
    private final Object[] messageParam = {"テスト"};
    private final ErrorInfo errorInfo = ErrorInfo.Required;

    @BeforeEach
    void setUp() {

        sut = new ValidateError(errorInfo, messageParam);
    }

    @DisplayName("isSmaeに同一のエラーを設定するとtrueが返却される")
    @Test
    void testIsSameReturnTrue() {

        final ValidateError paramError = new ValidateError(errorInfo, messageParam);

        assertTrue(sut.isSame(paramError));
    }

    @DisplayName("isSmaeに異なるエラーを設定するとfalseが返却される")
    @ParameterizedTest(name = "[{index}] argument is \"{arguments}\".")
    @MethodSource("diffrentValues")
    void testIsSameReturnFalse(String value) {

        final ValidateError paramError = new ValidateError(errorInfo, new Object[] { value });

        assertFalse(sut.isSame(paramError));
    }

    static Stream<String> diffrentValues() {

        return Stream.of("テスト2", "" , null);
    }
}