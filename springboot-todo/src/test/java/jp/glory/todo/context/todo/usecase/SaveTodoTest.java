package jp.glory.todo.context.todo.usecase;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.todo.domain.repository.TodoRepository;
import jp.glory.todo.context.todo.domain.value.Memo;
import jp.glory.todo.context.todo.domain.value.Summary;
import jp.glory.todo.context.todo.domain.value.TodoId;
import jp.glory.todo.context.todo.usecase.SaveTodo.Result;
import jp.glory.todo.context.user.domain.value.UserId;

class SaveTodoTest {

    private SaveTodo sut = null;

    private TodoRepository mock = null;

    private Result actual = null;

    @BeforeEach
    void setUp() {

        mock = mock(TodoRepository.class);
        sut = new SaveTodo(mock);
    }

    @DisplayName("saveのテスト")
    @Nested
    class Save {

        @DisplayName("未採番号の場合")
        @Nested
        class WhenNotNumbering {

            @DisplayName("入力内容に不備がない")
            @Nested
            class ValueIsValid {

                @BeforeEach
                void setUp() {

                    final Todo todo = new Todo(TodoId.notNumberingValue(), new UserId(2000l), new Summary("test"));
                    todo.setMemo(Memo.empty());
                    todo.unmarkFromComplete();
                    actual = sut.save(todo);
                }

                @DisplayName("入力エラーはない")
                @Test
                void assertErrors() {

                    assertFalse(actual.getErrors().hasError());
                }

                @DisplayName("TODOが保存される")
                @Test
                void assertSaved() {

                    verify(mock, times(1)).save(any(Todo.class));
                }
            }

            @DisplayName("入力内容に不備がある")
            @Nested
            class ValueIsInvalid {

                @BeforeEach
                void setUp() {
                    final Todo todo = new Todo(TodoId.notNumberingValue(), new UserId(2000l), Summary.empty());
                    todo.setMemo(Memo.empty());
                    todo.unmarkFromComplete();
                    actual = sut.save(todo);
                }

                @DisplayName("入力エラーがある")
                @Test
                void assertErrors() {

                    assertTrue(actual.getErrors().hasError());
                }

                @DisplayName("TODOが保存されない")
                @Test
                void assertSaved() {

                    verify(mock, never()).save(any(Todo.class));
                }
            }
        }

        @DisplayName("採番済みのTODOの場合")
        @Nested
        class WhenNumbering {

            @DisplayName("入力内容に不備がない")
            @Nested
            class ValueIsValid {

                @BeforeEach
                void setUp() {

                    final Todo todo = new Todo(new TodoId(1000l), new UserId(2000l), new Summary("test"));
                    todo.setMemo(Memo.empty());
                    todo.unmarkFromComplete();

                    final Todo beforeTodo = new Todo(todo.getId(), todo.getUserId(), new Summary("test-before"));

                    when(mock.findBy(any(TodoId.class))).thenReturn(Optional.of(beforeTodo));

                    actual = sut.save(todo);
                }

                @DisplayName("入力エラーはない")
                @Test
                void assertErrors() {

                    assertFalse(actual.getErrors().hasError());
                }

                @DisplayName("TODOが保存される")
                @Test
                void assertSaved() {

                    verify(mock, times(1)).save(any(Todo.class));
                }
            }

            @DisplayName("入力内容に不備がある")
            @Nested
            class ValueIsInvalid {

                @BeforeEach
                void setUp() {

                    final Todo todo = new Todo(new TodoId(1000l), new UserId(2000l), Summary.empty());
                    todo.setMemo(Memo.empty());
                    todo.unmarkFromComplete();
                    actual = sut.save(todo);
                }

                @DisplayName("入力エラーがある")
                @Test
                void assertErrors() {

                    assertTrue(actual.getErrors().hasError());
                }

                @DisplayName("TODOが保存されない")
                @Test
                void assertSaved() {

                    verify(mock, never()).save(any(Todo.class));
                }
            }
        }
    }
    
}
