package jp.glory.domain.common.type;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EntityIdTest {

    private static class StubClass extends EntityId {

        public StubClass(final Long paramValue) {
            super(paramValue);
        }
    }

    public static class 初期値に1が設定されている場合 {

        private EntityId sut = null;

        @Before
        public void setUp() {

            sut = new StubClass(1L);
        }

        @Test
        public void valueには1が設定されている() {

            assertThat(sut.getValue(), is(1L));
        }

        @Test
        public void isSetValueにはtrueが設定されている() {

            assertThat(sut.isSetValue(), is(true));
        }

        @Test
        public void isSameにNullが設定された場合falseが返却される() {

            assertThat(sut.isSame(null), is(false));
        }

        @Test
        public void isSameに0のIDが設定された場合falseが返却される() {

            assertThat(sut.isSame(new StubClass(0L)), is(false));
        }

        @Test
        public void isSameに1のIDが設定された場合trueが返却される() {

            assertThat(sut.isSame(new StubClass(1L)), is(true));
        }
    }

    public static class 初期値に0が設定されている場合 {

        private StubClass sut = null;

        @Before
        public void setUp() {

            sut = new StubClass(0L);
        }

        @Test
        public void valueには0が設定されている() {

            assertThat(sut.getValue(), is(0L));
        }

        @Test
        public void isSetValueにはtrueが設定されている() {

            assertThat(sut.isSetValue(), is(true));
        }

        @Test
        public void isSameにNullが設定された場合falseが返却される() {

            assertThat(sut.isSame(null), is(false));
        }

        @Test
        public void isSameに0のIDが設定された場合trueが返却される() {

            assertThat(sut.isSame(new StubClass(0L)), is(true));
        }

        @Test
        public void isSameに1のIDが設定された場合falseが返却される() {

            assertThat(sut.isSame(new StubClass(1L)), is(false));
        }
    }

    public static class Nullが設定されている場合 {

        private EntityId sut = null;

        @Before
        public void setUp() {

            sut = new StubClass(null);
        }

        @Test
        public void valueには0が設定されている() {

            assertThat(sut.getValue(), is(0L));
        }

        @Test
        public void isSetValueにはfalseが設定されている() {

            assertThat(sut.isSetValue(), is(false));
        }

        @Test
        public void isSameにNullが設定された場合falseが返却される() {

            assertThat(sut.isSame(null), is(false));
        }

        @Test
        public void isSameに0のIDが設定された場合falseが返却される() {

            assertThat(sut.isSame(new StubClass(0L)), is(false));
        }

        @Test
        public void isSameに1のIDが設定された場合falseが返却される() {

            assertThat(sut.isSame(new StubClass(1L)), is(false));
        }
    }

}