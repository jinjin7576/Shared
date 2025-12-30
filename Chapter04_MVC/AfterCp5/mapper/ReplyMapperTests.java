package org.joonzis.mapper;

import java.util.ArrayList;
import java.util.List;

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
public class ReplyMapperTests {
	@Autowired
	private ReplyMapper mapper;
	// 댓글 추가 테스트 코드
	@Test
	public void testInsert() {
		for(int i = 0; i < 5; i++) {		//반복문을 통해 이후 테스트에 활용할 데이터 확보
			ReplyVO vo = new ReplyVO();
			vo.setBno(1024); 				// 1024번 게시글에 댓글 추가
			vo.setReply("Insert Test "+i);	// 각 데이터의 차이점 확인하기 위해 i를 추가
			vo.setReplyer("Tester"+i);		
			int result = mapper.insert(vo);
			log.info(this.getClass().toString()+"::"+result);	// 실행된 위치 확인 및 결과 확인		
		}
	}
	
	// 댓글 리스트 가져오기 테스트 코드
	@Test
	public void getListTest() {
		List<ReplyVO> list = mapper.getList(1024);	// 댓글 추가를 1024번 게시글에 했으니, 해당 게시글 번호의 댓글 리스트 가져오기
		for (ReplyVO replyVO : list) {				// 가져온 데이터들을 하나씩 log.info로 찍어보기
			log.info(this.getClass().toString()+replyVO.getRno()+" " + replyVO.toString()); // 실행 위치 및 결과 확인
		}
	}

	// 댓글 상세보기 테스트 코드
	@Test
	public void readTest() {
		 //가장 최신 글 하나만 지정하기 위한 로직
		List<ReplyVO> list = mapper.getList(1024);
		int lastIndex = list.size() - 1;	//가장 최신 댓글
		
		int rno = list.get(lastIndex).getRno();	//가장 최신 댓글의 rno를 가져오기
		ReplyVO vo = mapper.read(rno);
		
		//로그 내용
		/*	class org.joonzis.mapper.ReplyMapperTests::
			ReplyVO(rno=6, bno=1024, reply=Insert Test 4, replyer=Tester4, 
			replydate=2025-12-30, updatedate=2025-12-30) */
		log.info(this.getClass().toString()+"::"+vo.toString());
	}
	
	// 댓글 수정 테스트 코드
	@Test
	public void updateTest() {
		List<ReplyVO> list = mapper.getList(1024);
		int lastIndex = list.size()-1;
		
		ReplyVO vo = new ReplyVO();
		vo.setRno(list.get(lastIndex).getRno());	//가장 최신 댓글
		vo.setReply(list.get(lastIndex).getReply()+"@");	// 댓글의 수정 횟수  = 골뱅이 갯수
		vo.setReplyer("Updater");
		
		log.info(this.getClass().toString()+"::Before Update vo : "+vo.toString()); // 수정 전 데이터 확인
		int result = mapper.update(vo);
		// 수정 이후 결과에 따른 로그 출력
		if(result >0) {
			log.info(this.getClass().toString()+"::After Update is sucsess!");
		} else {
			log.info(this.getClass().toString()+"::After Update is fail...");
		}
		//수정 된 이후 데이터 확인
		vo = mapper.read(vo.getRno());
		log.info(this.getClass().toString()+":: result is " + vo.toString());
	}
	
	@Test
	public void deleteTest() {
		List<ReplyVO> list = mapper.getList(1024);
		int LastVoIndex = list.size()-1;
		
		ReplyVO vo = list.get(LastVoIndex);
		int deleteIndex = vo.getRno();
		log.info(this.getClass().toString()+"::deleteIndex is "+deleteIndex);
		
		int result = mapper.delete(deleteIndex);
		if(result > 0) {
			log.info(this.getClass().toString()+"::delete is sucsess!");
		} else {
			log.info(this.getClass().toString()+"::delete is fail...");			
		}
	}
	// 원래는 테스트가 끝나면 모두 주석 처리하는 것이 좋다.
}
