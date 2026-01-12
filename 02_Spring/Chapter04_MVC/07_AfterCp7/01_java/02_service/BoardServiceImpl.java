package org.joonzis.Service;

import java.util.List;

import org.joonzis.domain.BoardAttachVO;
import org.joonzis.domain.BoardVO;
import org.joonzis.domain.Criteria;
import org.joonzis.mapper.BoardAttachMapper;
import org.joonzis.mapper.BoardMapper;
import org.joonzis.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;
@Log4j
@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardMapper bMapper;
	@Autowired
	private ReplyMapper rMapper;
	@Autowired
	private BoardAttachMapper aMapper;
	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("getList::");
		return bMapper.getList(cri);
	}
	
	@Transactional
	@Override
	public void register(BoardVO vo) {
		
		List<BoardAttachVO> aList = vo.getAttachList();
		
		// 1. 게시글 등록
		bMapper.insert(vo);
		
		// 2. bno 가져오기 (만약 properyKey를 사용한다면 생략해도 됨)
		vo = bMapper.getList(new Criteria(1,1)).get(0);
		int bno = vo.getBno();
		// 3. Attach
		if(aList != null && aList.size() > 0) {
			for (BoardAttachVO avo : aList) {
				avo.setBno(bno);
				aMapper.insert(avo);
			}
		}
	}
	
	@Override
	public BoardVO get(int bno) {
		
		return bMapper.read(bno);
	}
	@Transactional
	@Override
	public boolean modify(BoardVO vo) {
		// 첨부 파일 관련 처리
		aMapper.deleteByBno(vo.getBno()); // 해당 게시글 첨부파일 데이터 일괄 삭제
		for (BoardAttachVO bavo : vo.getAttachList()) {
			bavo.setBno(vo.getBno());
			aMapper.insert(bavo); // 일괄 삭제 후 다시 추가
		}
		return bMapper.update(vo) == 1;
	}
	@Transactional
	@Override
	public boolean remove(int bno) {
		System.out.println("BoardServiceImpl::remove");
		// 첨부파일과 댓글들 삭제
		aMapper.deleteByBno(bno); // 실제 파일 삭제는 model의 FileScheduler가 해줄거임(시간이 지나면)
		rMapper.deleteByBno(bno);
		
		// 이후에 문제가 없으면 게시글 삭제
		if(bMapper.delete(bno) == 1)
			return true;
		else
			return false;
	}
	@Override
	public int getTotal() {
		int result = bMapper.getTotal();
		return result;
	}
	
	@Override
	public List<BoardAttachVO> getAttachList(int bno) {
		return aMapper.findByBno(bno);
	}
}
