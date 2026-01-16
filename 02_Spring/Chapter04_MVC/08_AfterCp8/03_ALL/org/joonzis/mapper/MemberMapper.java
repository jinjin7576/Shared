package org.joonzis.mapper;

import org.joonzis.domain.AuthVO;
import org.joonzis.domain.MemberVO;

public interface MemberMapper {
	public MemberVO read(String userId);
//	public void register(@Param("userId") String userId, @Param("userPw") String userPw, @Param("userName") String userName);
	public int register(MemberVO vo);
	public int afterRegister(AuthVO auth);
}
