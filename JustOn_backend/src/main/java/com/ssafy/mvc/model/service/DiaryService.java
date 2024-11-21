package com.ssafy.mvc.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dto.Diary;
import com.ssafy.mvc.model.dto.DiaryEx;
import com.ssafy.mvc.model.dto.DiarySearch;

public interface DiaryService  {

	public boolean writeDiary(Diary diary);

	public Map<String, Object> selectAllDiary(DiarySearch diarySearch, String userId);

	public Diary selectDiaryByRegDate(String userId, String regDate);

	public Diary selectOneDiaryByNo(int diaryNo);

	public boolean modifyDiary(Diary diary);

	public boolean deleteDiary(int diaryNo);

	public List<DiaryEx> selectDiaryExList(int diaryNo);
	
}
