package com.ssafy.mvc.model.service;

import java.util.List;

import com.ssafy.mvc.model.dto.ExRecord;

public interface ExDataService {
	
	boolean registExData(List<ExRecord> exRecordList);
	
	List<ExRecord> getExData(String userId);
}
