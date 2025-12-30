package org.joonzis.controller;

import org.joonzis.Service.ReplyService;
import org.joonzis.domain.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
		log.info("ReplyVO "+vo);
		int result = service.register(vo);
		// 삼항 연산자
		// result가 1이면 상태가 ok인 객체 반환
		// 아니면 내부 서버 오류 상태인 객체 반환
		return result == 1 ? 
				new ResponseEntity<String>("success",HttpStatus.OK):
				new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
