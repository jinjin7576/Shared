package org.joonzis.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joonzis.domain.BoardAttachVO;
import org.joonzis.model.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
	@Autowired
	FileUploader uploader;
	
	@ResponseBody	//반환값을 뷰로 해석하지말고, 그대로 HTTP 응답 본문에 써라
	@PostMapping(value="/uploadAsyncAction",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<BoardAttachVO>> uploadAsyncAction(@RequestBody MultipartFile[] uploadFile) {
		
		List<BoardAttachVO> list = new ArrayList<BoardAttachVO>();
		
		String uploadFolder = "C:\\upload";
		
		
		// 업로드 경로(년/월/일)
		File uploadPath = new File(uploadFolder, uploader.getFolder());
		if(!uploadPath.exists()) {
			// 만약 해당 경로에 폴더가 존재하지 않을 경우 새롭게 생성
			uploadPath.mkdirs();
		}
		
		log.info("uploadAsyncAction...");
		
		for (MultipartFile multipartFile : uploadFile) {
			
			BoardAttachVO attachDTO = new BoardAttachVO();
			
			log.info("----------------------");
			log.info("UploadFileName : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1); // 예외처리 코드??
			
			log.info("only file name : " + uploadFileName);
			
			// UUID 생성해서 붙이기
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				// 담기
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploader.getFolder());
				attachDTO.setFileName(multipartFile.getOriginalFilename());
				
				list.add(attachDTO);
				
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
		return new ResponseEntity<List<BoardAttachVO>>(list,HttpStatus.OK);
	}
	
	@GetMapping(value="/download",
			produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String fileName){
		log.info("downloadFile" + fileName);
		Resource resource = new FileSystemResource("C:\\upload\\"+fileName);
		log.info("resource : " + resource);
		
		String resourceName = resource.getFilename();
		HttpHeaders headers = new HttpHeaders();
		
		try {
	         headers.add("Content-Disposition",
	            "attachment; fileName=" + new String(resourceName.getBytes("utf-8"),
	            "ISO-8859-1"));
	      } catch (UnsupportedEncodingException e) {
	         e.printStackTrace();
	      }
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	// 파일 삭제
	@SuppressWarnings("deprecation")
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(@RequestBody String dataFile) {
		log.info("fileName : " + dataFile);
		File file;
		try {
			file = new File("C:\\upload\\" + URLDecoder.decode(dataFile));
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("delete",HttpStatus.OK);
	}
}
