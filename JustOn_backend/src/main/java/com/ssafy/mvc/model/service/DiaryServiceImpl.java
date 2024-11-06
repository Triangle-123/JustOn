package com.ssafy.mvc.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.DiaryDao;
import com.ssafy.mvc.model.dto.Diary;
import com.ssafy.mvc.model.dto.DiaryEx;
import com.ssafy.mvc.model.dto.DiarySearch;
import com.ssafy.util.PageResult;

@Service
public class DiaryServiceImpl implements DiaryService {

	private final DiaryDao diaryDao;
	
	public DiaryServiceImpl(DiaryDao diarydao) {
		this.diaryDao = diarydao;
	}

	// 다이어리 등록
	@Override
	public boolean writeDiary(Diary diary) {
		int result = diaryDao.insertDiary(diary);
		return result == 1;
	}
	
	// 다이어리 등록시 운동 리스트 추가
	@Override
	public boolean addDiaryExList(List<DiaryEx> exList) {
		int result = diaryDao.insertDiaryExList(exList);
		return result == 1;
	}

	// 특정 유저의 다이어리 전체 조회
	@Override
	public Map<String, Object> selectAllDiary(DiarySearch diarySearch, String userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("diarySearch", diarySearch);
		params.put("userId", userId);
		Map<String, Object> result = new HashMap<>();
		result.put("list", diaryDao.selectAllDiary(params));
		result.put("pr", new PageResult(
					diarySearch.getPage(),
					//DiarySearch를 파라미터로 가져온 이유? 추후 혹시 모를 검색 설정 위함 
					diaryDao.selectAllDiaryCount(userId),
					diarySearch.getListSize()
			)
		);
		return result;
	}


	// 다이어리 날짜 선택 조회
	@Override
	public List<Diary> selectDiaryByRegDate(String userId, String regDate) {
		Map<String, String> info = new HashMap<>();
		info.put("userId", userId);
		info.put("regDate", regDate);
		// 확인 필요
		return diaryDao.selectDiaryByRegDate(info);
	}
	
	// 다이어리 상세 조회
	@Override
	public Diary selectOneDiaryByNo(int diaryNo) {
		return diaryDao.selectOneDiaryByNo(diaryNo);
	}

	// 특정 유저의 다이어리 수정 
	@Override
	public boolean modifyDiary(Diary diary) {
		int result = diaryDao.modifyDiary(diary);
		return result == 1;
	}

	// 다이어리 삭제
	@Override
	public boolean deleteDiary(int diaryNo) {
		int result = diaryDao.deleteDiary(diaryNo);
		return result == 1;
	}


}
