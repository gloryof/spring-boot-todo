package jp.glory.todo.context.base.usecase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional
public @interface Usecase {

}
