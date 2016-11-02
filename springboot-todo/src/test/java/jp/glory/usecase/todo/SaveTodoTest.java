package jp.glory.usecase.todo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.repository.TodoRepositoryMock;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;
import jp.glory.usecase.todo.SaveTodo.Result;

@RunWith(Enclosed.class)
public class SaveTodoTest {

    @RunWith(Enclosed.class)
    public static class save {

        public static class 入力内容に不備がない_未採番のTODOの場合 {

            private SaveTodo sut = null;

            private TodoRepositoryMock mockRepository = null;

            private Result actual = null;
            private final long expectedTodoIdVal = 1000;

            @Before
            public void setUp() {

                mockRepository = new TodoRepositoryMock();
                sut = new SaveTodo(mockRepository);

                mockRepository.setSequence(expectedTodoIdVal);
                final Todo todo = new Todo(TodoId.notNumberingValue(), new UserId(2000l), new Summary("test"),
                        Memo.empty(), false);
                actual = sut.save(todo);
            }

            @Test
            public void 入力エラーはない() {

                assertThat(actual.getErrors().hasError(), is(false));
            }

            @Test
            public void 保存結果に新しいTODOのIDが発行される() {

                assertThat(actual.getSavedTodoId().getValue(), is(expectedTodoIdVal));
            }

            @Test
            public void TODOが保存される() {

                assertThat(mockRepository.getResult(expectedTodoIdVal).isPresent(), is(true));
            }
        }

        public static class 入力内容に不備がある_未採番のTODOの場合 {

            private SaveTodo sut = null;

            private TodoRepositoryMock mockRepository = null;

            private Result actual = null;
            private final long expectedTodoIdVal = 1000;

            @Before
            public void setUp() {

                mockRepository = new TodoRepositoryMock();
                sut = new SaveTodo(mockRepository);

                mockRepository.setSequence(expectedTodoIdVal);
                final Todo todo = new Todo(TodoId.notNumberingValue(), new UserId(2000l), Summary.empty(),
                        Memo.empty(), false);
                actual = sut.save(todo);
            }

            @Test
            public void 入力エラーがある() {

                assertThat(actual.getErrors().hasError(), is(true));
            }

            @Test
            public void 保存結果に新しいTODOのIDは発行されない() {

                assertThat(actual.getSavedTodoId().isSetValue(), is(false));
            }

            @Test
            public void TODOが保存されない() {

                assertThat(mockRepository.getResult(expectedTodoIdVal).isPresent(), is(false));
            }
        }

        public static class 入力内容に不備がない_採番済みのTODOの場合 {

            private SaveTodo sut = null;

            private TodoRepositoryMock mockRepository = null;

            private Result actual = null;
            private final long expectedTodoIdVal = 1000;

            @Before
            public void setUp() {

                mockRepository = new TodoRepositoryMock();
                final Todo beforeTodo = new Todo(new TodoId(expectedTodoIdVal), new UserId(2000l),
                        new Summary("before"), new Memo("メモ"), true);
                mockRepository.addTestData(beforeTodo);
 
                sut = new SaveTodo(mockRepository);

                mockRepository.setSequence(9999);
                final Todo todo = new Todo(new TodoId(expectedTodoIdVal), new UserId(2000l), new Summary("test"),
                        Memo.empty(), false);
                actual = sut.save(todo);
            }

            @Test
            public void 入力エラーはない() {

                assertThat(actual.getErrors().hasError(), is(false));
            }

            @Test
            public void 保存結果に既存のTODOのIDが設定される() {

                assertThat(actual.getSavedTodoId().getValue(), is(expectedTodoIdVal));
            }

            @Test
            public void TODOが保存される() {

                assertThat(mockRepository.getResult(expectedTodoIdVal).isPresent(), is(true));
            }
        }

        public static class 入力内容に不備がある_採番のTODOの場合 {

            private SaveTodo sut = null;

            private TodoRepositoryMock mockRepository = null;

            private Result actual = null;
            private final long expectedTodoIdVal = 1000;

            @Before
            public void setUp() {

                mockRepository = new TodoRepositoryMock();
                sut = new SaveTodo(mockRepository);

                mockRepository.setSequence(expectedTodoIdVal);
                final Todo todo = new Todo(new TodoId(1000l), new UserId(2000l), Summary.empty(),
                        Memo.empty(), false);
                actual = sut.save(todo);
            }

            @Test
            public void 入力エラーがある() {

                assertThat(actual.getErrors().hasError(), is(true));
            }

            @Test
            public void 保存結果に新しいTODOのIDは発行されない() {

                assertThat(actual.getSavedTodoId().isSetValue(), is(false));
            }

            @Test
            public void TODOが保存されない() {

                assertThat(mockRepository.getResult(expectedTodoIdVal).isPresent(), is(false));
            }
        }
    }
    
}
