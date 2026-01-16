package org.joonzis.mapper;

import org.apache.ibatis.annotations.Insert;

public interface Sample1Mapper {
	// Mapper.xml 없이 쿼리 날리기
	@Insert("Insert into tbl_sample1(col1) values(#{data})")
	public int insertCol1(String data);
	
}
