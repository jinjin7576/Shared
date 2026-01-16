package org.joonzis.Service;

import java.util.ArrayList;
import java.util.List;

import org.joonzis.domain.AuthVO;
import org.joonzis.domain.MemberVO;
import org.joonzis.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	MemberMapper mapper;
	
	/*
	 * 지금은 메서드를 나눠놨지만, 생성될 계정의 역할을 구분할 만한 인자값을 던져주고 하나의 메서드에서 처리하는 방법이 좋아보임
	 */
	
	@Transactional // 트랜잭션은 예외가 발생하지 않으면 롤백을 하지 않음 (RuntimeException)
	@Override
	public void userRegister(String userId, String userPw, String userName) {
		MemberVO vo = new MemberVO();
		vo.setUserId(userId);
		vo.setUserPw(userPw);
		vo.setUserName(userName);

		if(mapper.register(vo)==0) {
			throw new RuntimeException("회원 정보 등록 실패");
		}
		if(mapper.afterRegister(new AuthVO(userId,"ROLE_USER"))==0) {
			throw new RuntimeException("회원 권한 등록 실패");
		}
	}
	@Transactional
	@Override
	public void managerRegister(String id, String pw, String name) {
		MemberVO vo = new MemberVO();
		vo.setUserId(id);
		vo.setUserPw(pw);
		vo.setUserName(name);

		if(mapper.register(vo)==0) {
			throw new RuntimeException("회원 정보 등록 실패");
		}
		
		List<AuthVO> authList = new ArrayList<AuthVO>();
		authList.add(new AuthVO(id, "ROLE_USER"));
		authList.add(new AuthVO(id, "ROLE_MEMBER"));
		
		for (AuthVO authVO : authList) {
			if(mapper.afterRegister(authVO)==0) {
				throw new RuntimeException("회원 권한 등록 실패");
			}
		}
	}
	@Transactional
	@Override
	public void adminRegister(String id, String pw, String name) {
		MemberVO vo = new MemberVO();
		vo.setUserId(id);
		vo.setUserPw(pw);
		vo.setUserName(name);

		if(mapper.register(vo)==0) {
			throw new RuntimeException("회원 정보 등록 실패");
		}
		
		List<AuthVO> authList = new ArrayList<AuthVO>();
		authList.add(new AuthVO(id, "ROLE_USER"));
		authList.add(new AuthVO(id, "ROLE_MEMBER"));
		authList.add(new AuthVO(id, "ROLE_ADMIN"));
		
		for (AuthVO authVO : authList) {
			if(mapper.afterRegister(authVO)==0) {
				throw new RuntimeException("회원 권한 등록 실패");
			}
		}
	}
	
}
