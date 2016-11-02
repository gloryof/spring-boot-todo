package jp.glory.infra.inmemory.repository.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface InMemoryRepository {

}
