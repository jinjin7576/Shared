package org.joonzis.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
@Component	//AOP와는 상관없지만, Bean으로 인식시키기 위한 어노테이션
@Aspect		//해당 클래스의 객체에서 Aspect를 구현한 것
public class LogAdvice {
	// @Before -> BeforeAdivce를 구현한 메소드라는 뜻의 어노테이션
	// @After, @AfterReturning, @AfterThrowing 등도 존재
	
	@Before(
			"execution(* org.joonzis.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("===============================================");
	}
	@Before(
			"execution(* org.joonzis.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1 : " + str1);
		log.info("str2 : " + str2);
		
	}
	@AfterThrowing(
			pointcut="execution(* org.joonzis.service.SampleService*.*(..))",
			throwing="exception"
			)
	public void logException(Exception exception) {
		log.info("Error");
		log.info("Exception::" + exception.getMessage());
	}
	/*	
	 * Advice와 관련된 어노테이션들은 내부적으로 pointcut을 지정
	 * pointcut은 별도의 @PointCut으로 지정해서 사용가능
	 * execution 문자열은 AspectJ의 표현식 -> 접근 제한자와 특정 클래스의 메소드를 지정 가능
	 * 	* org.joonzis.service.SampleService*.*(..)
	 * 
	 */
}
