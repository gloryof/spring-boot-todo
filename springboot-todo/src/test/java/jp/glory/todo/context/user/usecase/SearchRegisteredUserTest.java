package jp.glory.todo.context.user.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.todo.context.user.domain.entity.RegisteredUser;
import jp.glory.todo.context.user.domain.repository.RegisteredUserRepository;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.test.util.TestUserUtil;

class SearchRegisteredUserTest {

    private SearchRegisteredUser sut;
    private RegisteredUserRepository mockRepository;

    @BeforeEach
    void setUp() {

        mockRepository = mock(RegisteredUserRepository.class);

        sut = new SearchRegisteredUser(mockRepository);
    }

    @Nested
    @DisplayName("LoginIdによる検索のテスト")
    class TestFindByLoginId {

        @Test
        @DisplayName("存在しない場合はEmpty")
        void whenNotExisit() {

            when(mockRepository.findBy(any(LoginId.class))).thenReturn(Optional.empty());

            Optional<RegisteredUser> actual = sut.findBy(new LoginId("test-1"));

            assertFalse(actual.isPresent());
        }

        @Test
        @DisplayName("存在する場合は登録済みユーザが返る")
        void whenExisit() {

            final RegisteredUser expected = TestUserUtil.createDefault();

            when(mockRepository.findBy(any(LoginId.class))).thenReturn(Optional.of(expected));

            RegisteredUser actual = sut.findBy(new LoginId("test-1")).get();

            assertEquals(expected.getUserId().getValue(), actual.getUserId().getValue());
            assertEquals(expected.getLoginId().getValue(), actual.getLoginId().getValue());
            assertEquals(expected.getPassword().getValue(), actual.getPassword().getValue());
            assertEquals(expected.getUserName().getValue(), actual.getUserName().getValue());
        }
    }
}
