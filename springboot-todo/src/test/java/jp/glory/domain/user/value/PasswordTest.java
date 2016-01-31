package jp.glory.domain.user.value;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class PasswordTest {

    public static class emptyのテスト {

        @Test
        public void 初期値が返却される() {

            final Password actual = Password.empty();

            assertThat(actual, is(not(nullValue())));
            assertThat(actual.getValue(), is(""));
        }
    }

    public static class 値が設定されている場合 {

        private Password sut = null;

        private static final String ENCRYPTED_VALUE = "19CB2A070DDBE8157E17C5DDA0EA38E8AA16FAE1725C1F7AC22747D870368579";

        @Before
        public void setUp() {

            sut = new Password(ENCRYPTED_VALUE);
        }

        @Test
        public void パスワードが一致している場合_isMatchでtrueが返却される() {

            final Password comparePassword = new Password(ENCRYPTED_VALUE);

            assertThat(sut.isMatch(comparePassword), is(true));
        }

        @Test
        public void パスワードが一致していない場合_isMatchでfalseが返却される() {

            final Password comparePassword = new Password("test");

            assertThat(sut.isMatch(comparePassword), is(false));
        }

        @Test
        public void Nullが渡された場合_falseが返却される() {

            assertThat(sut.isMatch(null), is(false));
        }
    }

    public static class 空文字が設定されている場合 {

        private Password sut = null;

        @Before
        public void setUp() {

            sut = new Password("");
        }

        @Test
        public void パスワードが一致している場合でも_isMatchでfalseが返却される() {

            final Password comparePassword = new Password("");

            assertThat(sut.isMatch(comparePassword), is(false));
        }
    }

    public static class Nullが設定されている場合 {

        private Password sut = null;

        @Before
        public void setUp() {

            sut = new Password("");
        }

        @Test
        public void パスワードが一致している場合でも_isMatchでfalseが返却される() {

            final Password comparePassword = new Password("");

            assertThat(sut.isMatch(comparePassword), is(false));
        }
    }
}