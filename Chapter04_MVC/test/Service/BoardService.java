package org.joonzis.Service;

import java.util.List;

import org.joonzis.domain.BoardVO;

public interface BoardService {
	// 전체 리스트
	public List<BoardVO> getList();
	public BoardVO get(int bno);
	// 데이터 삽입
	public void register(BoardVO vo);
	// 데이터 삭제
	public boolean remove(int bno);
	// 데이터 수정
	public boolean modify(BoardVO vo);
}
