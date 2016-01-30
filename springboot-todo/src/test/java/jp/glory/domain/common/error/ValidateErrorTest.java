package jp.glory.domain.common.error;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValidateErrorTest {
    
    private ValidateError sut;
    private final Object[] messageParam = {"テスト"};
    private final ErrorInfo errorInfo = ErrorInfo.Required;

    @Before
    public void setUp() {

        sut = new ValidateError(errorInfo, messageParam);
    }

    @Test
    public void isSmaeに同一のエラーを設定するとtrueが返却される() {

        final ValidateError paramError = new ValidateError(errorInfo, messageParam);

        assertThat(sut.isSame(paramError), is(true));
    }

    @Test
    public void isSmaeに異なるエラーを設定するとfalseが返却される() {

        final ValidateError paramError = new ValidateError(errorInfo, new Object[] { "テスト2" });

        assertThat(sut.isSame(paramError), is(false));
    }

    @Test
    public void isSmaeにNullを設定するとtrueが返却される() {

        assertThat(sut.isSame(null), is(false));
    }
}