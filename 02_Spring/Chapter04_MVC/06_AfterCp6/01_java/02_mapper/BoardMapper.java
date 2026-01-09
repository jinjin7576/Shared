package org.joonzis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.joonzis.domain.BoardVO;
import org.joonzis.domain.Criteria;

public interface BoardMapper {
	// 전체 리스트
	public List<BoardVO> getList(Criteria cri);
	
	// 데이터 삽입 INSERT
	public int insert(BoardVO vo);
	// 단일 데이터 READ
	public BoardVO read(int bno);
	// 데이터 수정 UPDATE -- PK가 조건, 수정할 내용 : 제목, 내용, 작성자, 수정 날짜
	public int update(BoardVO vo);
	// 데이터 삭제 DELETE -- PK가 조건
	public int delete(int bno);
	
	public int getTotal();
	// 댓글 데이터 변경
	public void updateReplyCnt(@Param("bno") int bno, @Param("amount") int amount);
	/* @Param : 전달받은 인자들을 Map으로 만들어서 MyBatis한테 던져준다.
	 * Map<String, Object> paramMap = new HashMap();
	 * paramMap.put("bno",bno);
	 * paramMap.put("amount",amount);
	 * 즉, xml에서는 parameterType="map"이라고 쓰면 됨(명시할 경우)
	 */
}
