package org.joonzis.domain;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardVO {
	
	private int bno, replycnt;
	private String title, content, writer;
	private Date regdate, updatedate;
	
	private List<BoardAttachVO> attachList;
}
