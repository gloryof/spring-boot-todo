package jp.glory.infra.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Primary
@Profile("default")
public @interface DbRepository {

}
