package jp.glory.domain.common.annotation.param;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ValidCharcterTypeTest {

    public static class OnlySingleByteCharsのテスト {

        @Test
        public void 半角文字のみの場合isMatcheでtrueが返却される() {

            final ValidCharcterType sut = ValidCharcterType.OnlySingleByteChars;

            assertThat(sut.isMatch("0123456789"), is(true));
            assertThat(sut.isMatch("abcdefghijklmnopqrstuvwxyz"), is(true));
            assertThat(sut.isMatch("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), is(true));
            assertThat(sut.isMatch("!\"#$%&'()=~|+*}<>?_,./\\"), is(true));
        }

        @Test
        public void 半角文字以外の場合isMatcheでtrueが返却される() {

            final ValidCharcterType sut = ValidCharcterType.OnlySingleByteChars;
            assertThat(sut.isMatch("１"), is(false));
        }
    }
}
