package ru.projects.joke.orm.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Данной аннотацией размечаются поля, которые представляют из себя колонки в
 * РСУБД.
 * 
 * @author Alimurad A. Ramazanov
 * @version 1.0.0
 * @since 13.08.2017
 *
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface JokeColumn {

	/**
	 * Имя колонки в РСУБД.
	 * <p>
	 * 
	 * @return не может быть {@code null}.
	 */
	String name();

	/**
	 * Признак, может ли быть значение {@code == null}.
	 * <p>
	 * 
	 * @return по умолчанию {@code true}.
	 */
	boolean nullable() default true;

	/**
	 * Длина колонки "по умолчанию".
	 * <p>
	 * 
	 * @return длину колонки в символах.
	 */
	long length() default 255;
}
