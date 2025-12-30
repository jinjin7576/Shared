package org.joonzis.service;

import java.util.List;

import org.joonzis.Service.ReplyService;
import org.joonzis.domain.ReplyVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration( 
		"file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ReplyServiceTests {
	@Autowired
	ReplyService service;
	private String before = this.getClass().toString()+"::";
	
	@Test
	public void getTest() {
		int rno = 1;
		ReplyVO vo = service.get(rno);
		log.info(this.getClass().toString()+"::getTest() : " +vo.toString());
	}
	
	@Test
	public void getListTest() {
		int bno = 1024;
		List<ReplyVO> list = service.getList(bno);
		for (ReplyVO replyVO : list) {
			log.info(this.getClass().toString()+"::getTest() : " +replyVO.toString());
		}
	}
	
	@Test
	public void registerTest() {
		log.info(this.getClass().toString()+"::register()");
		ReplyVO vo = new ReplyVO();
		vo.setBno(1024);
		vo.setReply("Insert Test ");
		vo.setReplyer("Tester");
		service.register(vo);
	}
	
	@Test
	public void modifyTest() {
		List<ReplyVO> list = service.getList(1024);
		int lastIndex = list.size() - 1;
		
		ReplyVO vo = new ReplyVO();
		vo.setRno(list.get(lastIndex).getRno());
		vo.setReply(list.get(lastIndex).getReply()+"@");
		if(service.modify(vo)) log.info(before+"modifyTest() is sucsess!");
		else log.info(before+"modifyTest() is fail...");
		log.info(before+"modifyed Data is " + service.get(list.get(lastIndex).getRno()));
	}
	
	@Test
	public void deleteTest() {
		List<ReplyVO> list = service.getList(1024);
		int lastIndex = list.size() - 1;
		int lastRno = list.get(lastIndex).getRno();
		if(service.remove(lastRno)) log.info(before+"deleteTest() is sucsess!");
		else log.info(before+"deleteTest() is fail...");
	}
}
