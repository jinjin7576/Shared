package org.joonzis.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joonzis.domain.BoardAttachVO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileUploader {

	String uploadFolder = "C:\\upload";
	
	public List<BoardAttachVO> upload(MultipartFile[] uploadFiles){

		List<BoardAttachVO> list = new ArrayList<BoardAttachVO>();
		
		String uploadFolder = "C:\\upload";
		
		
		// 업로드 경로(년/월/일)
		File uploadPath = new File(uploadFolder, getFolder());
		if(!uploadPath.exists()) {
			// 만약 해당 경로에 폴더가 존재하지 않을 경우 새롭게 생성
			uploadPath.mkdirs();
		}
		
		log.info("uploadAsyncAction...");
		
		for (MultipartFile multipartFile : uploadFiles) {
			
			BoardAttachVO attachDTO = new BoardAttachVO();
			
			log.info("----------------------");
			log.info("UploadFileName : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1); // 예외처리 코드??
			
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
		return list;
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
