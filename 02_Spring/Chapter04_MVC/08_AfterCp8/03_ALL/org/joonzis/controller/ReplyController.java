package org.joonzis.controller;

import java.util.List;

import org.joonzis.Service.ReplyService;
import org.joonzis.domain.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
	ReplyService service;
	
	//등록
	@PostMapping(
			value="/new", 
			consumes = "application/json", 		// 수신 데이터 포맷
			produces=MediaType.TEXT_PLAIN_VALUE	// 송신 데이터 포맷
			)
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		int result = service.register(vo);
		// 삼항 연산자
		// result가 1이면 상태가 ok인 객체 반환
		// 아니면 내부 서버 오류 상태인 객체 반환
		return result == 1 ?
				new ResponseEntity<String>("success",HttpStatus.OK):
				new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//목록 - /reply/pages/:bno/:page
	@GetMapping(
			value="/pages/{bno}",
			produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<ReplyVO>> getList(@PathVariable("bno") int bno) {
		List<ReplyVO> list = service.getList(bno);
		ResponseEntity<List<ReplyVO>> result = new ResponseEntity<>(list, HttpStatus.OK);
		return result;
	}
	
	// 조회 /reply/:rno - GET
	@GetMapping(
			value="/{rno}",
			produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ReplyVO> getReply(@PathVariable("rno") int rno) {
		ReplyVO vo = service.get(rno);
		ResponseEntity<ReplyVO> result = new ResponseEntity<ReplyVO>(vo, HttpStatus.OK);
		return result;
	}
	
	// 삭제 /reply/:rno - DELETE
	@DeleteMapping(
			value="/{rno}",
			produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> deleteReply(@PathVariable("rno") int rno) {
		boolean sResult = service.remove(rno);
		return sResult ? 
				new ResponseEntity<String>("success",HttpStatus.OK):
				new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 수정 /reply/:rno - PUT or PATCH
	/*
	 *  put과 patch는 둘 다 비슷하고, 하는 일도 유사함?
	 *  그래서 둘 다 열어두는 편이 좋다.
	 *  둘 다 받기 위해 requestMapping 어노테이션으로 만들어서
	 *  method 속성을 아래와 같이 배열로 put과 patch로 선언
	 */
	@RequestMapping(	// put과 patch, 두 가지 방식의 요청을 모두 받기 위해서 requestMapping으로
			method= {RequestMethod.PUT , RequestMethod.PATCH},	//요청 방식 (put,patch)
			value="/{rno}",										//요청 경로+pathVariable로 받을 데이터
			consumes = "application/json",						//수신 데이터의 형태
			produces = { MediaType.APPLICATION_XML_VALUE,		//송신 데이터의 형태
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> modifyReply(
			@PathVariable("rno") int rno, 
			@RequestBody ReplyVO vo
			) {
		
		// qs로 들어온 rno와 vo에 들어온 rno가 다를 경우?
		// 또는 비어있는?
		if (rno != vo.getRno()) vo.setRno(rno);
		
		boolean sResult = service.modify(vo);
		return sResult ? 
				new ResponseEntity<String>("success",HttpStatus.OK):
				new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
