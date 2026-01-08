package org.joonzis.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joonzis.domain.AttachedFileDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class UploadController {
	@GetMapping("/uploadForm")
	public String uploadForm() {
		log.info("upload Form");
		return "uploadForm";
	}
	
	/*
	 * String getName() 			: 파라미터의 이름, input 태그의 이름
	 * String getOriginalFileName() : 업로드 파일의 이름
	 * boolean isEmpty() 			: 파일이 존재하지 않는 경우 true
	 * long getSize() 				: 업로드 파일의 크기
	 * byte[] getBytes()			: byte[]로 파일 데이터 변환
	 * InputStream getInputStream()	: 파일 데이터와 연결된 InputStream 반환
	 * transferTo(File file)		: 파일 저장
	 */
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile) {
		for (MultipartFile multipartFile : uploadFile) {
			log.info("----------------------");
			// 파일의 Original Name 가져오기
			log.info("UploadFileName : " + multipartFile.getOriginalFilename());
			// 파일의 Size 가져오기
			log.info("Upload File Size : " + multipartFile.getSize());
		}
	}
	@GetMapping("/uploadAsync")
	public String uploadAsync() {
		log.info("upload async");
		return "uploadAsync";
	}
	
	@ResponseBody	//?
	@PostMapping(value="/uploadAsyncAction",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<AttachedFileDTO>> uploadAsyncAction(@RequestBody MultipartFile[] uploadFile) {
		
		List<AttachedFileDTO> list = new ArrayList<AttachedFileDTO>();
		
		String uploadFolder = "C:\\upload";
		
		// 업로드 경로(년/월/일)
		File uploadPath = new File(uploadFolder, getFolder());
		if(!uploadPath.exists()) {
			// 만약 해당 경로에 폴더가 존재하지 않을 경우 새롭게 생성
			uploadPath.mkdirs();
		}
		
		log.info("uploadAsyncAction...");
		
		for (MultipartFile multipartFile : uploadFile) {
			
			AttachedFileDTO attachDTO = new AttachedFileDTO();
			
			log.info("----------------------");
			log.info("UploadFileName : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
			log.info("only file name : " + uploadFileName);
			
			//UUID 생성해서 붙이기
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				// 담기
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(getFolder());
				attachDTO.setFileName(multipartFile.getOriginalFilename());
				
				list.add(attachDTO);
				
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
		return new ResponseEntity<List<AttachedFileDTO>>(list,HttpStatus.OK);
	}
	
	
	// 오늘 날짜의 경로를 문자열로 생성
	public String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-",File.separator); // 서버 컴에 맞게 경로를 나눠줌 변경해줌
												// windows 	: \
												// 리눅스,mac : /
	}
}
