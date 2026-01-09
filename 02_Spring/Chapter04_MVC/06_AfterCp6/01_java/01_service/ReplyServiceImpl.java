package org.joonzis.Service;

import java.util.List;

import org.joonzis.domain.ReplyVO;
import org.joonzis.mapper.BoardMapper;
import org.joonzis.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class ReplyServiceImpl implements ReplyService{
	@Autowired
	ReplyMapper mapper;
	@Autowired
	BoardMapper bMapper;
	
	@Override
	public ReplyVO get(int rno) {
		ReplyVO vo = mapper.read(rno);
		return vo;
	}
	
	@Override
	public List<ReplyVO> getList(int bno) {
		List<ReplyVO> list = mapper.getList(bno);
		return list;
	}
	
	
	@Override
	public boolean modify(ReplyVO vo) {
		int result = mapper.update(vo);
		if(result > 0) return true;
		else return false;
	}
	
	@Transactional
	@Override
	public int register(ReplyVO vo) {
		
		int result = mapper.insert(vo);
		// 받아온 매개변수 vo에서 bno 꺼내기
		bMapper.updateReplyCnt(vo.getBno(), 1);
		return result;
	}
	
	@Transactional
	@Override
	public boolean remove(int rno) {
		// 받아온 매개변수 rno로 mapper에서 ReplyVO 가져와서 bno 꺼내기
		bMapper.updateReplyCnt(mapper.read(rno).getBno(), -1);
		int result = mapper.delete(rno);
		if(result > 0 ) return true;
		else return false;
	}
}
