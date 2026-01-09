package org.joonzis.service;

import java.util.List;

import org.joonzis.Service.BoardService;
import org.joonzis.domain.BoardVO;
import org.joonzis.mapper.BoardMapperTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;
import oracle.net.aso.b;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration( 
		"file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardServiceTests {
	@Autowired
	private BoardService bservice;
	
//	@Test
//	public void testGetAllList() {
//		List<BoardVO> list = bservice.getList();
//		for (BoardVO boardVO : list) {
//			log.info("testGetAllList::"+boardVO);
//		}
//	}
//	@Test
//	public void testGetOne() {
//		BoardVO vo = bservice.get(2);
//		log.info("testGetOne::"+ vo);
//	}
//	@Test
//	public void testRegister() {
//		BoardVO vo = new BoardVO();
//		vo.setTitle("Test2");
//		vo.setContent("Test Content2");
//		vo.setWriter("Tester02");
//		log.info("testRegister::"+vo);
//		bservice.register(vo);
//	}
	@Test
	public void testRemove() {
		log.info("testRemove:: "+ bservice.remove(5));
		//testRemove::true
		//testRemove::false
	}
	@Test
	public void testModify() {
		BoardVO vo = new BoardVO();
		vo.setBno(3);
		vo.setContent("Modify Content");
		vo.setTitle("Modify Title");
		vo.setWriter("Modify Writer");
		log.info(vo);
		log.info("testModify:: " + bservice.modify(vo));
		//testModify::true
		//testModify::false
	}
}
