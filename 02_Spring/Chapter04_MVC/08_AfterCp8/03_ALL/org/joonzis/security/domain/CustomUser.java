package org.joonzis.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.joonzis.domain.MemberVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class CustomUser extends User{
	private static final long serialVersionUID = 1L;
	private MemberVO member;

	// 무조건 들어가야함!
	public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
	// 위랑 같은 생성자지만, 받는 인자만 다를 뿐임
	public CustomUser(MemberVO vo) {
		super(vo.getUserId(),
				vo.getUserPw(),
				vo.getAuthList().stream().map(auth ->
					new SimpleGrantedAuthority(auth.getAuth())
					).collect(Collectors.toList())
		/*
		 * for (AuthVO auth : vo.getAuthList()) { list.add(new
		 * SimpleGrantedAuthority(auth.getAuth())); 
		 * }
		 * vo.getAuthList의 변환 코드를 풀어 쓰면 이런 느낌이다...정도
		 */
				);
		this.member = vo;
	}
}
