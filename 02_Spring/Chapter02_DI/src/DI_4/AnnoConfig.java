package org.joonzis.DI_4_annoConfig;

// cglib 라이브러리를 받아야 한다.
// 받는 법 -> pom.xml에 다음 태그 추가
//
// 중간 생략
// 
// <dependency>
//	<groupId>cglib</groupId>
//		<artifactId>cglib</artifactId>
//		<version>2.2.2</version>
//	</dependency>
//<dependencies>

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // <- applicationContext와 같은 역할을 하는 자바 클래스
public class AnnoConfig {
	@Bean // Bean을 만드는 메서드
	public Person human1() {
		
		Set<String> hobbies = new HashSet<String>();
		hobbies.add("여행");
		hobbies.add("독서");
		hobbies.add("낚시");
		
		Person person = new Person();
		person.setName("김씨");
		person.setAge(50);
		person.setHobbies(hobbies);
		
		return person;
	}
	@Bean(name = "human2")
	public Person abc() {

		Set<String> hobbies = new HashSet<String>();
		hobbies.add("잠");
		hobbies.add("드라이브");
		hobbies.add("여행");
		
		Person person = new Person();
		person.setName("박씨");
		person.setAge(25);
		person.setHobbies(hobbies);
		
		return person;
	}
	@Bean
	public PersonInfo pInfo(){
		Set<String> hobbies = new HashSet<String>();
		hobbies.add("게임");
		hobbies.add("영화");
		hobbies.add("맛집탐방");
		
		PersonInfo info = new PersonInfo();
		info.setPerson(new Person("이씨", 30, hobbies));
		return info;
	}
}
