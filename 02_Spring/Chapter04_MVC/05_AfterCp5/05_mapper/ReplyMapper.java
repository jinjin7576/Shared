package org.joonzis.mapper;

import java.util.List;

import org.joonzis.domain.ReplyVO;

public interface ReplyMapper {
	// 메서드 이름(id)가 같은데...?
	/* 하지만, Mapper 인터페이스가 다르기 때문에...
	 	이 친구는 ReplyMapper.xml의 쿼리들만 부른다.
	 	즉, BoardMapper.xml과 이름(id)가 겹치더라도 충돌나지 않음
 	*/
	
	//댓글 삽입
	public int insert(ReplyVO vo);
	// 댓글 목록
	public List<ReplyVO> getList(int bno);
	// 댓글 읽기
	public ReplyVO read(int rno);
	// 댓글 삭제
	public int delete(int rno);
	// 댓글 수정
	public int update(ReplyVO vo);
}
