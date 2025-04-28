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
		int result1 = diaryDao.insertDiary(diary);
		int result2 = 1;
		for(DiaryEx diaryEx : diary.getDiaryExList()) {
			diaryEx.setDiaryNo(diary.getDiaryNo());
			result2 = diaryDao.insertDiaryExList(diaryEx);
		}
		return result1 == 1 && result2 == 1;
	}
	
	// 특정 유저의 다이어리 전체 조회
	@Override
	public Map<String, Object> selectAllDiary(DiarySearch diarySearch, String userId) {
		// params 에 유저 아이디와 검색 정보 담기
		Map<String, Object> params = new HashMap<>();
		params.put("diarySearch", diarySearch);
		params.put("userId", userId);
		System.out.println("#33");
		// result에 결과를 담아서 Contoller로 넘기기 
		Map<String, Object> result = new HashMap<>();
		result.put("list", diaryDao.selectAllDiary(params));
		result.put("pr", new PageResult(
					diarySearch.getPage(),
					diaryDao.selectAllDiaryCount(userId),
					diarySearch.getListSize()
			)
		);
		return result;
	}


	// 다이어리 날짜 선택 조회
	@Override
	public Diary selectDiaryByRegDate(String userId, String regDate) {
		Map<String, String> info = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		info.put("userId", userId);
		info.put("regDate", regDate);
		
		Diary diary = diaryDao.selectDiaryByRegDate(info);
		if(diary == null) return null;
		int diaryNo = diary.getDiaryNo();
		diary.setDiaryExList(diaryDao.selectDiaryExList(diaryNo));
		return diary;
		
//		result.put("diary", diary);
//		result.put("ExList", diaryDao.selectDiaryExList(diaryNo));
		
		// 확인 필요
//		return result;
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
		// 기존 다이어리 Ex 삭제 후
		int result2 = diaryDao.deleteDiaryExList(diary.getDiaryNo());
		// 돌리면서 저장
		int result3 = 1;
		for(DiaryEx diaryEx : diary.getDiaryExList()) {
			diaryEx.setDiaryNo(diary.getDiaryNo());
			result3 = diaryDao.insertDiaryExList(diaryEx);
		}
//		int result3 = diaryDao.insertDiaryExList(diary.getDiaryExList());
		return result >= 1 && result2 >= 0 && result3 >= 1;
	}

	// 다이어리 삭제
	@Override
	public boolean deleteDiary(int diaryNo) {
		int result = diaryDao.deleteDiary(diaryNo);
		return result == 1;
	}

	// 특정 다이어리의 운동 리스트 조회
	@Override
	public List<DiaryEx> selectDiaryExList(int diaryNo) {
		List<DiaryEx> result = diaryDao.selectDiaryExList(diaryNo);
		return result;
	}

}
