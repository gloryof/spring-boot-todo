package jp.glory.usecase.todo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.repository.TodoRepositoryMock;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;
import jp.glory.usecase.todo.SaveTodo.Result;

class SaveTodoTest {

    private SaveTodo sut = null;

    private TodoRepositoryMock mockRepository = null;

    private Result actual = null;

    private final long expectedTodoIdVal = 1000;

    @BeforeEach
    void setUp() {

        mockRepository = new TodoRepositoryMock();
        sut = new SaveTodo(mockRepository);
        mockRepository.setSequence(expectedTodoIdVal);
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

                    final Todo todo = new Todo(TodoId.notNumberingValue(), new UserId(2000l), new Summary("test"),
                            Memo.empty(), false);
                    actual = sut.save(todo);
                }

                @DisplayName("入力エラーはない")
                @Test
                void assertErrors() {

                    assertFalse(actual.getErrors().hasError());
                }

                @DisplayName("保存結果に新しいTODOのIDが発行される")
                @Test
                void assertId() {

                    assertEquals((Long) expectedTodoIdVal, actual.getSavedTodoId().getValue());
                }

                @DisplayName("TODOが保存される")
                @Test
                void assertSaved() {

                    assertTrue(mockRepository.getResult(expectedTodoIdVal).isPresent());
                }
            }

            @DisplayName("入力内容に不備がある")
            @Nested
            class ValueIsInvalid {

                @BeforeEach
                void setUp() {
                    final Todo todo = new Todo(TodoId.notNumberingValue(), new UserId(2000l), Summary.empty(),
                            Memo.empty(), false);
                    actual = sut.save(todo);
                }

                @DisplayName("入力エラーがある")
                @Test
                void assertErrors() {

                    assertTrue(actual.getErrors().hasError());
                }

                @DisplayName("保存結果に新しいTODOのIDは発行されない")
                @Test
                void assertId() {

                    assertFalse(actual.getSavedTodoId().isSetValue());
                }

                @DisplayName("TODOが保存されない")
                @Test
                void assertSaved() {

                    assertFalse(mockRepository.getResult(expectedTodoIdVal).isPresent());
                }
            }
        }

        @DisplayName("採番済みのTODOの場合")
        @Nested
        class WhenNumbering {

            @BeforeEach
            void setUp() {

                final Todo beforeTodo = new Todo(new TodoId(expectedTodoIdVal), new UserId(2000l),
                        new Summary("before"), new Memo("メモ"), true);
                mockRepository.addTestData(beforeTodo);
 
                sut = new SaveTodo(mockRepository);

                mockRepository.setSequence(9999);
            }
            

            @DisplayName("入力内容に不備がない")
            @Nested
            class ValueIsValid {

                @BeforeEach
                void setUp() {

                    final Todo todo = new Todo(new TodoId(expectedTodoIdVal), new UserId(2000l), new Summary("test"),
                            Memo.empty(), false);
                    actual = sut.save(todo);
                }

                @DisplayName("入力エラーはない")
                @Test
                void assertErrors() {

                    assertFalse(actual.getErrors().hasError());
                }


                @DisplayName("保存結果に既存のTODOのIDが設定される")
                @Test
                void assertId() {

                    assertEquals((Long) expectedTodoIdVal, actual.getSavedTodoId().getValue());
                }

                @DisplayName("TODOが保存される")
                @Test
                void assertSaved() {

                    final Optional<Todo> actualOpt = mockRepository.getResult(expectedTodoIdVal);
                    assertTrue(actualOpt.isPresent());
                    assertEquals("test", actualOpt.get().getSummary().getValue());
                }
            }

            @DisplayName("入力内容に不備がある")
            @Nested
            class ValueIsInvalid {

                @BeforeEach
                void setUp() {

                    final Todo todo = new Todo(new TodoId(1000l), new UserId(2000l), Summary.empty(),
                            Memo.empty(), false);
                    actual = sut.save(todo);
                }

                @DisplayName("入力エラーがある")
                @Test
                void assertErrors() {

                    assertTrue(actual.getErrors().hasError());
                }

                @DisplayName("保存結果に新しいTODOのIDは発行されない")
                @Test
                void assertId() {

                    assertFalse(actual.getSavedTodoId().isSetValue());
                }

                @DisplayName("TODOは保存されない")
                @Test
                void assertSaved() {

                    final Optional<Todo> actualOpt = mockRepository.getResult(expectedTodoIdVal);
                    assertTrue(actualOpt.isPresent());
                    assertEquals("before", actualOpt.get().getSummary().getValue());
                }
            }
        }
    }
    
}
