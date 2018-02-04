package jp.glory.todo.context.user.domain.entity;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.todo.context.user.domain.entity.User;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.context.user.domain.value.UserName;

class UserTest {

    private User sut;

    @DisplayName("全ての値が設定されている場合")
    @Nested
    class WhenAllValueIsSet {

        private final UserId USER_ID_VALUE = new UserId(123456L);

        @BeforeEach
        void setUp() {

            sut = new User(USER_ID_VALUE, new LoginId("test-user"), new UserName("シュンツ"),
                    new Password("19CB2A070DDBE8157E17C5DDA0EA38E8AA16FAE1725C1F7AC22747D870368579"));
        }

        @DisplayName("設定したユーザIDが設定されている")
        @Test
        void testGetUserId() {

            assertTrue(sut.getUserId().isSame(USER_ID_VALUE));
        }

        @DisplayName("isRegisteredにtrueが設定されている")
        @Test
        void testIsRegistered() {

            assertTrue(sut.isRegistered());
        }
    }

    @DisplayName("未登録のユーザの場合")
    @Nested
    class WhenNotRegisteredUser {

        @BeforeEach
        void setUp() {

            sut = new User(UserId.notNumberingValue(), new LoginId("test-user"), new UserName("シュンツ"),
                    new Password("19CB2A070DDBE8157E17C5DDA0EA38E8AA16FAE1725C1F7AC22747D870368579"));
        }

        @DisplayName("isRegisteredにfalseが設定されている")
        @Test
        void testIsRegistered() {

            assertFalse(sut.isRegistered());
        }
    }
}