package ru.projects.joke.orm.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.projects.joke.orm.core.exceptions.JokeException;

@Aspect
@Component
public class EntityValidator {

	@Autowired
	private Core core;

	@Pointcut(value = "execution(* ru.projects.joke.orm.core.operations.JokeOperations.save(..))")
	public void beforeSaveQueryPointCut() {

	}

	@Before("beforeSaveQueryPointCut()")
	public void beforeSaveQueryAdvice(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		check(args[0]);
	}

	@Pointcut(value = "execution(* ru.projects.joke.orm.core.operations.JokeOperations.update(..))")
	public void beforeUpdateQueryPointCut() {

	}

	@Before("beforeUpdateQueryPointCut()")
	public void beforeUpdateQueryAdvice(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		check(args[0]);
	}

	private void check(Object arg) {
		if (arg == null || !core.getEntityClasses().contains(arg.getClass()))
			throw new JokeException("Illegal type of entity!");
	}
}
