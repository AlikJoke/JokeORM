package ru.projects.joke.orm.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ru.projects.joke.orm.core.utils.IndexMethod;

/**
 * Данной аннотацией размечаются поля, по которым будут созданы индексы в РСУБД.
 * 
 * @author Alimurad A. Ramazanov
 * @version 1.0.0
 * @since 13.08.2017
 *
 */
@Target(value = { ElementType.FIELD, ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface JokeIndex {

	/**
	 * Имя индекса в РСУБД.
	 * <p>
	 * 
	 * @return не может быть {@code null}.
	 */
	String name();

	String[] columns() default {};

	String column() default "";

	IndexMethod method() default IndexMethod.BTREE;
}
