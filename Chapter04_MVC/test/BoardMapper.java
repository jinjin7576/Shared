package org.joonzis.mapper;

import java.util.List;

import org.joonzis.domain.BoardVO;

public interface BoardMapper {
	// 전체 리스트
	public List<BoardVO> getList();
	
	// 데이터 삽입 INSERT
	public int insert(BoardVO vo);
	// 단일 데이터 READ
	public BoardVO read(int bno);
	// 데이터 수정 UPDATE -- PK가 조건, 수정할 내용 : 제목, 내용, 작성자, 수정 날짜
	public int update(BoardVO vo);
	// 데이터 삭제 DELETE -- PK가 조건
	public int delete(int bno);
}
