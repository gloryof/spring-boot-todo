package jp.glory.usecase.todo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.entity.Todos;
import jp.glory.domain.todo.repository.TodoRepositoryMock;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;

@RunWith(Enclosed.class)
public class SearchTodosTest {

    public static class searchByUser {

        private SearchTodo sut = null;
        private TodoRepositoryMock mock = null;

        @Before
        public void setUp() {

            mock = new TodoRepositoryMock();

            LongStream.rangeClosed(1, 100).mapToObj(v -> {
                final UserId userId = new UserId(v % 10);

                return new Todo(new TodoId(v), userId, Summary.empty(), Memo.empty(), true);
            }).forEach(mock::save);
            sut = new SearchTodo(mock);
        }

        @Test
        public void 指定したユーザIDのTodoリストが返る() {

            final UserId expectedUserId = new UserId(3l);

            final Todos actual = sut.searchByUser(expectedUserId);
            final Todos.Statistics actualStatistics = actual.getStatistics();

            assertThat(actualStatistics.getTotal(), is(10));

            final List<Todo> actualList = actual.asList();
            assertThat(actualList.size(), is(10));

            final List<Todo> actualUserIdFiltered = actualList.stream()
                    .filter(v -> v.getUserId().isSame(expectedUserId)).collect(Collectors.toList());
            assertThat(actualUserIdFiltered.size(), is(10));
        }
    }
}
