package org.joonzis.Service;

import java.util.List;

import org.joonzis.domain.ReplyVO;
import org.joonzis.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class ReplyServiceImpl implements ReplyService{
	@Autowired
	ReplyMapper mapper;
	
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
	
	@Override
	public void register(ReplyVO vo) {
		mapper.insert(vo);
	}
	
	@Override
	public boolean remove(int rno) {
		int result = mapper.delete(rno);
		if(result > 0 ) return true;
		else return false;
	}
}
