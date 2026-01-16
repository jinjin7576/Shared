package org.joonzis.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/sample/*")
public class SampleController {
	@GetMapping("/all")
	public void doAll() {
		log.info("do all");
	}
	@GetMapping("/member")
	public void doMember() {
		log.info("do all");
	}
	@GetMapping("/admin")
	public void doAdmin() {
		log.info("do all");
	}
	
	// annotation을 활용한 시큐리티 설정
	// 여기서 어노테이션 사용할려면 서블릿콘텍스트에서 설정해야함
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')") // 그나마 최신, 표현식 사용 가능
	@GetMapping("/annoMember")
	public void doMember2() {
		log.info("로그인 멤버 어노테이션");
	}
	@Secured({"ROLE_ADMIN"}) // 조금 이전 버전, 권한만 명시 가능
	@GetMapping("/annoAdmin")
	public void doAdmin2() {
		log.info("로그인 어드민 어노테이션");
	}
}
