package com.ssafy.mvc.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.mvc.model.dto.Diary;
import com.ssafy.mvc.model.dto.DiaryEx;
import com.ssafy.mvc.model.dto.DiarySearch;

public interface DiaryDao {
	
	// 다이어리 등록 및 다이어리 운동 리스트 등록
	int insertDiary(Diary diary);
	int insertDiaryExList(DiaryEx exList);
	
	// 특정 유저의 다이어리 전체 조회
	// 다이어리 수 조회
	List<Diary> selectAllDiary(Map<String, Object> params);
	int selectAllDiaryCount(String userId);
	
	// 특정 유저의 다이어리 날짜 선택 조회
	Diary selectDiaryByRegDate(Map<String, String> info);
		
	// 조회된 다이어리의 ExList 조회 
	List<DiaryEx> selectDiaryExList(int diaryNo);
	
	// 다이어리 상세 조회
	Diary selectOneDiaryByNo(int diaryNo);

	// 특정 유저의 다이어리 수정
	int modifyDiary(Diary diary);
	
	// 특정 유저의 다이어리 삭제
	int deleteDiary(int diaryNo);
	
	// 특정 다이어리의 운동리스트 삭제
	int deleteDiaryExList(int diaryNo);
	
	
}
