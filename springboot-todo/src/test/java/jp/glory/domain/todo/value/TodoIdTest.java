package jp.glory.domain.todo.value;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TodoIdTest {

    public static class notNumberingValueのテスト {

        @Test
        public void 値が0で未採番の値が返却される() {

            final TodoId actual = TodoId.notNumberingValue();

            assertThat(actual.getValue(), is(0L));
            assertThat(actual.isSetValue(), is(false));
        }
    }
}
