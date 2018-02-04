package jp.glory.todo.context.base.infra.repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Profile("in-memory")
public @interface InMemoryRepository {

}
