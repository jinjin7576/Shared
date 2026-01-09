package org.joonzis.mapper;

import java.util.List;

import org.joonzis.domain.BoardVO;
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
public class BoardMapperTests {
	@Autowired
	private BoardMapper mapper;
	
//	@Test
//	public void testGetList() {
//		
//		List<BoardVO> list = mapper.getList();
//		
//		for (BoardVO boardVO : list) { 
//			log.info(boardVO);
//		}
//	}
//	@Test
//	public void testInsert() {
//		BoardVO vo = new BoardVO();
//		vo.setTitle("Test");
//		vo.setContent("Test Content");
//		vo.setWriter("Tester01");
//		int result = mapper.insert(vo);
//		log.info(result);
//	}
//	@Test
//	public void testRead() {
//		BoardVO vo = null;
//		vo = mapper.read(6);
//		log.info(vo);
//	}
//	@Test
//	public void testUpdate() {
//		BoardVO vo = new BoardVO();
//		vo.setBno(6);
//		vo.setTitle("UpdateTest");
//		vo.setContent("UpdateContent");
//		vo.setWriter("UpdateWriter");
//		int result = mapper.update(vo);
//		log.info(result);
//	}
//	@Test
//	public void testDelete() {
//		int bno = 1;
//		int result = mapper.delete(bno);
//		log.info(result);
//	}
}