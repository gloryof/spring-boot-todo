package jp.glory.domain.todo.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;

@RunWith(Enclosed.class)
public class TodoTest {

    public static class すべての値が正常に設定されている場合 {

        private Todo sut = null;

        @Before
        public void setUp() {

            sut = new Todo(new TodoId(1000l), new UserId(1l), new Summary("概要テスト"), new Memo("新しいメモ"), false);
        }

        @Test
        public void versionでバージョンが設定されたエンティティが帰る() {

            final long expectedVersion = 10l;

            assertThat(sut.getEntityVersion(), is(1l));

            sut.version(expectedVersion);

            assertThat(sut.getEntityVersion(), is(expectedVersion));
        }

        @Test
        public void isRegisteredにtrueが設定されている() {

            assertThat(sut.isRegistered(), is(true));
        }

        @Test
        public void markAsCompleteで完了フラグがON_unmarkAsCompleteで完了フラグがOFFになる() {

            assertThat(sut.isCompleted(), is(false));

            sut.markAsComplete();

            assertThat(sut.isCompleted(), is(true));

            sut.unmarkFromComplete();

            assertThat(sut.isCompleted(), is(false));
        }
    }
}
