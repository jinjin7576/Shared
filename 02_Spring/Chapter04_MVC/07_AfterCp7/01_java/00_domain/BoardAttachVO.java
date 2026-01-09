package org.joonzis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardAttachVO {
	private String uuid;
	private String uploadPath;
	private String fileName;
	private int bno;
}
