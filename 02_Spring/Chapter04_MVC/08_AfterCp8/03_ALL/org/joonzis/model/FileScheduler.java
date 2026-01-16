package org.joonzis.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joonzis.domain.BoardAttachVO;
import org.joonzis.mapper.BoardAttachMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
//@Configuration
//@EnableScheduling
public class FileScheduler {
	// 스케줄러 어노테이션을 사용하려면 root-context.xml 의 수정이 필요함
	/*	1. root-context.xml의 Namespace에 task 추가
	 * 	2. 컴포넌트 스캔에 이 클래스도 포함하게끔 설정 추가
	 * 	3. <task:annotation-driven/> 을 추가
	 * */
	@Autowired
	BoardAttachMapper baMapper;
	
	private String savePath = "C:\\upload\\";
	
	// 현재 게시글에 업로드된 파일들과 DB를 비교하여 더미 업로드 파일들을 삭제

	// cron="0 0 2 * * ?" -> 매일 새벽 2시(cron표현식) -> "초(*) 분(*) 시(*) 일(*) 월(*) 요일(?)"
	@Scheduled(fixedDelay = 600000, 		// 10분 마다 (600초)
				initialDelay = 0) 			// 서버가 시작하자마자
	public void removeNotUploadedFile(){
		System.out.println("FileScheduler::removeNotUploadedFile");
		
		// 1. DB에서 데이터들 가져오기
		List<BoardAttachVO> db = baMapper.getList();
		
		// 2. 실제 파일들 가져오기 (C드라이브의 upload 파일 안에 있는 것들)
		List<Path> fileList = null;
		try {
			fileList = getFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 3. 그리고 비교 (UUID로 비교)
		
		// 3-1 DB UUID Set으로 만들기
			// 이렇게 하면 UUID만을 담은 SET이 만들어지는걸까?
		Set<String> uuidSet = db.stream().map(BoardAttachVO::getUuid).collect(Collectors.toSet());
		
		// 3-2 실제 파일 순회하면서 판단
		for (Path path : fileList) {
			File currFile = path.toFile();
			String fileUuid = (currFile.getName().split("_"))[0]; // 실제 파일의 UUID
		
			// 3-3 판단
			// DB에 없는 파일 → 삭제
			if(!uuidSet.contains(fileUuid)) {
				try {
					Files.deleteIfExists(path); // 삭제
					System.out.println("다음과 같은 파일이 삭제됨 : " + currFile.getName());
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("다음과 같은 파일을 삭제하는 도중 오류 발생 : " + currFile.getName());
				}				
			}
		}
	}
	
	private List<Path> getFiles() throws IOException {
		//leaf 디렉토리 판별 -> 그 안의 파일만 수집?
		Path root = Paths.get(savePath);

		List<Path> leafFiles = null;
		try (Stream<Path> paths = Files.walk(root)) {

		    leafFiles =
		        paths
		        .filter(Files::isDirectory)                // 디렉토리만
		        .filter(dir -> {
		            try (Stream<Path> children = Files.list(dir)) {
		                return children.noneMatch(Files::isDirectory);
		            } catch (IOException e) {
		                return false;
		            }
		        })
		        .flatMap(dir -> {
		            try {
		                return Files.list(dir).filter(Files::isRegularFile);
		            } catch (IOException e) {
		                return Stream.empty();
		            }
		        })
		        .collect(Collectors.toList());
		    
		    //가져온 파일 리스트 확인용
		    System.out.println("file List-----------------------");
		    for (Path path : leafFiles) {
		    	System.out.println("FileScheduler::" + path);
			}
		}
		return leafFiles;
	}
}
