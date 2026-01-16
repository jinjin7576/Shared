package org.joonzis.controller;

import org.apache.ibatis.javassist.compiler.ast.Member;
import org.joonzis.Service.MemberService;
import org.joonzis.domain.AuthVO;
import org.joonzis.domain.MemberVO;
import org.joonzis.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class CommonController {
	@Autowired
	MemberService service;
	@Autowired
	PasswordEncoder pwencoder;
	@GetMapping("/accessError")
	public String accessDenied(Authentication auth, Model model) {
		log.info("권한 거부 : " + auth);
		model.addAttribute("msg","Access Denied");
		return "/accessError";
	}
	@GetMapping("/customLogin")
	public String loginInput(
			String error, String logout, Model model) {
		log.info("error : " + error);
		log.info("logout : " + logout);
		if(error != null) model.addAttribute("error", "Login Error Check");
		if(logout != null) model.addAttribute("logout", "Logout..");
		return "/customLogin";
	}
	
	@GetMapping("/customLogout")
	public String logout() {
		log.info("custom logout");
		return "/customLogout";
	}
	// 현재 유저 정보를 비동기로 반환
	@ResponseBody
	@GetMapping("/api/currentUser")
	public Authentication getCurrentUser() {
		return SecurityContextHolder
				.getContext()
				.getAuthentication();
	}
	@GetMapping("/customRegister")
	public void userRegister() {}
	
	@PostMapping("/user/register")
	public String userInfoInsert(
			 String userId,
			 String userPw,
			 String userName
			) {
		log.info(userId);
		log.info(userPw);
		log.info(userName);
		
		
		return "/customLogin";
	}
}
