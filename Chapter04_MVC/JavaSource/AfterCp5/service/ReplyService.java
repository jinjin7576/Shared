package org.joonzis.Service;

import java.util.List;

import org.joonzis.domain.ReplyVO;

public interface ReplyService {
	// 전체 리스트
		public List<ReplyVO> getList(int bno);
		public ReplyVO get(int rno);
		// 데이터 삽입
		public void register(ReplyVO vo);
		// 데이터 삭제
		public boolean remove(int rno);
		// 데이터 수정
		public boolean modify(ReplyVO vo);
}
