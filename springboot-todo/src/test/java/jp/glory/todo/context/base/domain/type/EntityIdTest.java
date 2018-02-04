package jp.glory.todo.context.base.domain.type;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import jp.glory.todo.context.base.domain.type.EntityId;

class EntityIdTest {


    static class StubClass extends EntityId {

        private static final long serialVersionUID = 1L;

        StubClass(final Long paramValue) {
            super(paramValue);
        }

        @Override
        public String toString() {

            return String.valueOf(getValue());
        }
    }

    static class DiffrentValueProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {

            return Stream.of(new StubClass(2L), new StubClass(4L), null).map(this::createArguments);
        }

        private Arguments createArguments(final StubClass stub) {

            if (stub == null) {

                return Arguments.of(null, null);
            }

            return Arguments.of(stub.getValue(), stub);
        }
    }

    @DisplayName("初期値に1が設定されている場合")
    @Nested
    class WhenInitValueIsOne {

        private EntityId sut = null;

        @BeforeEach
        void setUp() {

            sut = new StubClass(1L);
        }

        @DisplayName("valueには1が設定されている")
        @Test
        void testGetValue() {

            assertEquals((Long)1L, sut.getValue());
        }

        @DisplayName("isSetValueにはtrueが設定されている")
        @Test
        void testSetValue() {

            assertTrue(sut.isSetValue());
        }

        @DisplayName("isSameに同じ値が設定された場合trueが返される")
        @Test
        void testIsSame1() {

            assertTrue(sut.isSame(new StubClass(1L)));
        }

        @DisplayName("isSameに異なる値が設定された場合falseが返される")
        @ParameterizedTest(name = "[{index}] StubClass({0})")
        @ArgumentsSource(DiffrentValueProvider.class)
        void testIsSame2(Long longValue, StubClass value) {

            assertFalse(sut.isSame(value));
        }

        @DisplayName("isSameにNullが設定された場合falseが返される")
        @Test
        void testIsSame3() {

            assertFalse(sut.isSame(null));
        }
    }

    @DisplayName("初期値に0が設定されている場合")
    @Nested
    class WhenInitValueIsZero {

        private EntityId sut = null;

        @BeforeEach
        void setUp() {

            sut = new StubClass(0L);
        }

        @DisplayName("valueには0が設定されている")
        @Test
        void testGetValue() {

            assertEquals((Long) 0L, sut.getValue());
        }

        @DisplayName("isSetValueにはtrueが設定されている")
        @Test
        void testIsSetValue() {

            assertTrue(sut.isSetValue());
        }

        @DisplayName("isSameに同じ値が設定された場合trueが返される")
        @Test
        void testIsSame1() {

            assertTrue(sut.isSame(new StubClass(0L)));
        }

        @DisplayName("isSameに異なる値が設定された場合falseが返される")
        @ParameterizedTest(name = "[{index}] StubClass({0})")
        @ArgumentsSource(DiffrentValueProvider.class)
        void testIsSame2(Long longValue, StubClass value) {

            assertFalse(sut.isSame(value));
        }

        @DisplayName("isSameにNullが設定された場合falseが返される")
        @Test
        void testIsSame3() {

            assertFalse(sut.isSame(null));
        }
    }

    @DisplayName("Nullが設定されている場合")
    @Nested
    class WhenInitValueIsNull {

        private EntityId sut = null;

        @BeforeEach
        void setUp() {

            sut = new StubClass(null);
        }

        @DisplayName("valueには0が設定されている")
        @Test
        void testGetValue() {

            assertEquals((Long) 0L, sut.getValue());
        }

        @DisplayName("isSetValueにはfalseが設定されている")
        @Test
        void testIsSetValue1() {

            assertFalse(sut.isSetValue());
        }

        @DisplayName("isSameにNullが設定された場合falseが返される")
        @Test
        void testIsSame1() {

            assertFalse(sut.isSame(null));
        }

        @DisplayName("isSameに異なる値が設定された場合falseが返却される")
        @ParameterizedTest(name = "[{index}] StubClass({0})")
        @ArgumentsSource(DiffrentValueProvider.class)
        void testIsSame2(Long longValue, StubClass value) {

            assertFalse(sut.isSame(value));
        }
    }

}