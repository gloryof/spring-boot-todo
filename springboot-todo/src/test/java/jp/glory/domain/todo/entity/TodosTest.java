package jp.glory.domain.todo.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import jp.glory.domain.todo.entity.Todos.Statistics;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;

@RunWith(Enclosed.class)
public class TodosTest {

    public static class コンストラクタにNullが渡された場合 {

        private Todos sut = null;

        @Before
        public void setUp() {

            sut = new Todos(null);
        }

        @Test
        public void 未実行TODOはない() {

            assertThat(sut.hasUnexecuted(), is(false));
        }

        @Test
        public void トータル件数は0件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getTotal(), is(0));
        }

        @Test
        public void 実行済みは0件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getExecuted(), is(0));
        }

        @Test
        public void 未実行は0件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getUnexecuted(), is(0));
        }

        @Test
        public void asListで空のListが返される() {

            final List<Todo> actual = sut.asList();

            assertThat(actual.isEmpty(), is(true));
        }
    }

    public static class Todoが0件の場合 {

        private Todos sut = null;

        @Before
        public void setUp() {

            sut = new Todos(new ArrayList<>());
        }

        @Test
        public void 未実行TODOはない() {

            assertThat(sut.hasUnexecuted(), is(false));
        }

        @Test
        public void トータル件数は0件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getTotal(), is(0));
        }

        @Test
        public void 実行済みは0件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getExecuted(), is(0));
        }

        @Test
        public void 未実行は0件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getUnexecuted(), is(0));
        }

        @Test
        public void asListで空のListが返される() {

            final List<Todo> actual = sut.asList();

            assertThat(actual.isEmpty(), is(true));
        }
    }

    public static class 未実行Todoが1件の場合 {

        private Todos sut = null;

        @Before
        public void setUp() {

            final List<Todo> list = new ArrayList<>();
            list.add(new Todo(new TodoId(1l), new UserId(10l), Summary.empty(), Memo.empty(), false));

            sut = new Todos(list);
        }

        @Test
        public void 未実行TODOはある() {

            assertThat(sut.hasUnexecuted(), is(true));
        }

        @Test
        public void トータル件数は1件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getTotal(), is(1));
        }

        @Test
        public void 実行済みは0件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getExecuted(), is(0));
        }

        @Test
        public void 未実行は1件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getUnexecuted(), is(1));
        }

        @Test
        public void asListで1件のListが返される() {

            final List<Todo> actual = sut.asList();

            assertThat(actual.size(), is(1));
        }
    }

    public static class 実行済Todoが1件の場合 {

        private Todos sut = null;

        @Before
        public void setUp() {

            final List<Todo> list = new ArrayList<>();
            list.add(new Todo(new TodoId(1l), new UserId(10l), Summary.empty(), Memo.empty(), true));

            sut = new Todos(list);
        }

        @Test
        public void 未実行TODOはない() {

            assertThat(sut.hasUnexecuted(), is(false));
        }

        @Test
        public void トータル件数は1件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getTotal(), is(1));
        }

        @Test
        public void 実行済みは1件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getExecuted(), is(1));
        }

        @Test
        public void 未実行は0件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getUnexecuted(), is(0));
        }

        @Test
        public void asListで1件のListが返される() {

            final List<Todo> actual = sut.asList();

            assertThat(actual.size(), is(1));
        }
    }

    public static class 未実行3件_実行済み2件の合わせて5件の場合 {

        private Todos sut = null;

        @Before
        public void setUp() {

            final List<Todo> list = new ArrayList<>();

            LongStream.rangeClosed(1, 3).forEach(
                    v -> list.add(new Todo(new TodoId(v), new UserId(v), Summary.empty(), Memo.empty(), false)));

            LongStream.rangeClosed(1, 2).forEach(
                    v -> list.add(new Todo(new TodoId(v), new UserId(v), Summary.empty(), Memo.empty(), true)));

            sut = new Todos(list);
        }

        @Test
        public void 未実行TODOはある() {

            assertThat(sut.hasUnexecuted(), is(true));
        }

        @Test
        public void トータル件数は実行済_未実行を合計した数() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getTotal(), is(5));
        }

        @Test
        public void 実行済みは2件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getExecuted(), is(2));
        }

        @Test
        public void 未実行は3件() {

            final Statistics actualStatistics = sut.getStatistics();
            assertThat(actualStatistics.getUnexecuted(), is(3));
        }

        @Test
        public void asListで5件のListが返される() {

            final List<Todo> actual = sut.asList();

            assertThat(actual.size(), is(5));
        }
    }
}
