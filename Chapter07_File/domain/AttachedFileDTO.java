package org.joonzis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttachedFileDTO {
	private String fileName;
	private String uploadPath;
	private String uuid;
}
