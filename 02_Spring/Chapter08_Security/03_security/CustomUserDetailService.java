package org.joonzis.security;

import org.joonzis.domain.MemberVO;
import org.joonzis.mapper.MemberMapper;
import org.joonzis.security.domain.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailService implements UserDetailsService{
	@Autowired
	private MemberMapper mapper;
	
	// 로그인 시 호출
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		log.warn("load user by name : " + username);
		
		MemberVO vo = mapper.read(username);
		log.warn("mapper : " + vo);
		
		return vo == null ? null : new CustomUser(vo); //security.domain에 만들어둔 유저 정보
	}
	//왜이리 복잡함? -> 자체적으로 보안을 올리기 위해서
}
