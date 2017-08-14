package ru.projects.joke.orm.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Данной аннотацией размечаются классы, которые представляют собой таблицы в
 * РСУБД.
 * 
 * @author Alimurad A. Ramazanov
 * @version 1.0.0
 * @since 13.08.2017
 *
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface JokeEntity {

	/**
	 * Имя таблицы в РСУБД.
	 * <p>
	 * 
	 * @return не может быть {@code null}.
	 */
	String name();
}
