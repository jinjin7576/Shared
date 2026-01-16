package org.joonzis.Service;

import org.joonzis.domain.MemberVO;

public interface MemberService {
	public void userRegister(String userId, String userPw, String userName);
	public void adminRegister(String id, String pw, String name);
	public void managerRegister(String id, String pw, String name);
}
