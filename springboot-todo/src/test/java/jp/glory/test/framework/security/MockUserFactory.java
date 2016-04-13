package jp.glory.test.framework.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserName;
import jp.glory.web.session.UserInfo;

public class MockUserFactory implements WithSecurityContextFactory<MockLoginUser> {

    @Override
    public SecurityContext createSecurityContext(final MockLoginUser annotation) {

        final String key;
        if (annotation.loginId() == null || annotation.loginId().isEmpty()) {

            key = annotation.value();
        } else {

            key = annotation.loginId();
        }

        final Map<String, UserInfo> userMap = createUserMaps();
        final UserInfo info = userMap.getOrDefault(key, new UserInfo(defaultUser()));

        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        final UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(info, info.getPassword(), info.getAuthorities());
        context.setAuthentication(token);

        return context;
    }

    public static User defaultUser() {

        return new User(new UserId(100l), new LoginId("mock-user"), new UserName("モックユーザ"), new Password("パスワード"));
    }

    private Map<String, UserInfo> createUserMaps() {

        final List<User> users = new ArrayList<>();

        users.add(defaultUser());

        return users.stream()
                .map(v -> new UserInfo(v))
                .collect(Collectors.toMap(UserInfo::getUsername, v -> v));
    }
}
