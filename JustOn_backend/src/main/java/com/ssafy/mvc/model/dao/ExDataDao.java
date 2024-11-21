package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.ExRecord;

public interface ExDataDao {
	
	int insertOrUpdateExRecord(ExRecord exRecord);
	
	List<ExRecord> selectExRecord(List<String> column, String userId);
	
	List<String> selectExRecordColumnName();
	
}
