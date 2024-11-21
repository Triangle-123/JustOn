package com.ssafy.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.ExDataDao;
import com.ssafy.mvc.model.dto.ExRecord;

@Service
public class ExDataServiceImpl implements ExDataService {

	private final ExDataDao exDataDao;
	
	public ExDataServiceImpl(ExDataDao exDataDao) {
		this.exDataDao = exDataDao;
	}
	
	@Override
	public boolean registExData(ExRecord exRecord) {
		return exDataDao.insertOrUpdateExRecord(exRecord) >= 1;
	}

	@Override
	public List<ExRecord> getExData(String userId) {
		List<String> column = exDataDao.selectExRecordColumnName();
		return exDataDao.selectExRecord(column, userId);
	}

}
