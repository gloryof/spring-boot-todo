package jp.glory.test.framework.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockUserFactory.class)
public @interface MockLoginUser {

    /**
     * Alias for loginId.
     */
    String value() default "mock-user";

    String loginId() default "";
}
