package org.joonzis.controller;

import org.joonzis.domain.TestVO;
import org.joonzis.domain.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/test")
@RestController //REST 컨트롤러라고 선언
public class TestController {
	
	@GetMapping(value="/getText", produces="text/plain; charset=utf-8")
	//produces : 리턴할 데이터의 형태 및 인코딩 방식(생략가능, 기본이 xml과
	public String getText() {
		log.info("Mime Type : " + MediaType.TEXT_PLAIN_VALUE);
		// jsp의 파일 이름이 아닌 순수 데이터를 전달
		return "안녕하세요";
	}
	
	// 만약 마크다운(xml)언어로 들어온다면, 경로뒤에 .json을 붙이면 JSON으로 받을 수 있음
	// TestVO를 반환
	@GetMapping(value="/getObject",produces= {
			MediaType.APPLICATION_JSON_UTF8_VALUE,	// json으로 반환
			MediaType.APPLICATION_ATOM_XML_VALUE	// xml로 반환
	})
	public TestVO getObject() {
		return new TestVO(100, "kim");
	}
	
	/* 1. 요청 URL : /test/check
	 * 2. 파라미터 : 실수형 age
	 * 3. 리턴 타입 : TestVO
	 * 		- vo 객체 생성
	 * 		- no 필드는 0으로 고정
	 * 		- 전달 받은 age를 문자열로 name 필드에 저장
	 * */
	@GetMapping(value="/check",produces= {
			MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_ATOM_XML_VALUE	
	})
	public ResponseEntity<TestVO> check(@RequestParam("age") double age) {
		//@RequestParam에 옵션을 추가하여 디폴트값이나 필수값으로 설정가능 -> 에러 회피 가능
		//또는 age를 원시가 아닌 래퍼클래스(Double)로 받으면 Null이 들어와서 500에러 회피 가능
		String ageStr = Double.toString(age);
		TestVO vo = new TestVO(0,ageStr);
		
		// 데이터의 상태값도 보낼 수 있음(ResponseEntity)
		ResponseEntity<TestVO> result = null;
		if(age > 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo); //502
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo); //200
		}
		// 결과는 달라지는게 없음
		// 상태값을 전해줌으로써 프론트(js)가 파싱하기 전에 데이터의 상태를 확인가능
		return result;
	}
	
	// REST의 규칙을 따른 기본적인 통신 방법?
	@GetMapping("/product/{cat}/{pid}") //경로에 데이터를 실어서 보내기
	public String[] getPath( @PathVariable("cat") String cat, @PathVariable("pid") int pid) {
		return new String[] {"category : "+cat+", "+"productId : " +pid};
	}
	
	// JSON을 받아서 처리하는 메서드
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket t) {
		log.info("convert..ticket:"+t); // convert..ticket:Ticket(tno=1, owner=kim, grade=gold)
		String result = new Gson().toJson(t);
		log.info(result);	// {"tno":1, "owner":"kim", "grade":"gold"}
		return t;
	}
	
	// gson을 활용해서 직접 JSON 작성해서 응답
	@GetMapping("/info")
	public String makeJson() {
		//gson
		JsonObject json = new JsonObject(); //응답으로 보낼 제일 큰 JSON
		
		json.addProperty("name", "kim");	// 응답 JSON에 담기
		json.addProperty("age", 10);		// 응답 JSON에 담기
		json.addProperty("job", "student");	// 응답 JSON에 담기
		
		JsonArray ja = new JsonArray();		// 응답 JSON에 users 배열(user 하나가 json)을 담기 위한 JSON 배열 만들기
		for(int i=0; i < 5; i++) {
			JsonObject j = new JsonObject();
			j.addProperty("user"+i, i);
			ja.add(j);
		}
		json.add("users", ja);				// 반복문을 통해 만들어진 JSON 배열을 응답 JSON에 담기
		return json.toString();
		/*	return된 응답 JSON
		 * {
		 * "name":"kim",
		 * "age":10,
		 * "job":"student",
		 * "users":[{"user0":0},{"user1":1},{"user2":2},{"user3":3},{"user4":4}]
		 * }
		 * */
	}
}
