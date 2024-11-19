package com.ssafy.mvc.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.model.dto.CustomUserDetails;
import com.ssafy.mvc.model.dto.Diary;
import com.ssafy.mvc.model.dto.DiaryEx;
import com.ssafy.mvc.model.dto.DiarySearch;
import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.service.DiaryService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api-diary")
@CrossOrigin
public class DiaryRestController {
	private final DiaryService diaryService;

	public DiaryRestController(DiaryService diaryService) {
		this.diaryService = diaryService;
	}
	
	// 다이어리 등록 
	// 등록 시 동시에 부위별 테이블 등록
	@PostMapping("/diary")
	public ResponseEntity<?> insertDiary(@RequestBody Diary diary, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		diary.setUserId(userDetails.getUsername());
		
		// DiaryExList 추가하기
		List<DiaryEx> ExList = diary.getDiaryExList();
		
		// 등록 후 조회에 사용될 수 있도록 diaryNo 받아서 return
		try {
			if(diaryService.writeDiary(diary)) {
				int diaryNo = diary.getDiaryNo();
				return ResponseEntity.status(HttpStatus.CREATED).body(diaryNo);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("All Insert Failed");
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("All Insert Failed");
		}
	}
	
	
	// 특정 유저의 다이어리 전체 조회 
	@GetMapping("/diary") // @RequestBody(required = false) 
	public ResponseEntity<?> getUserDiaryList(DiarySearch diarySearch, HttpSession session, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		// 체크 필요 : 일반적인 방식 session.getAttribute("userId")
//		String userId = session.getId();
//		String userId = user.getUserId();
		
		Map<String, Object> result = diaryService.selectAllDiary(diarySearch, userDetails.getUsername());
		// 이게 맞나??
		List<Diary> list = (List<Diary>) result.get("list");
//		List<Diary> list = diaryService.selectAllDiary(userId);
		
		System.out.println(list);
		try {
			if(list == null || list.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
			}
			return ResponseEntity.status(HttpStatus.OK).body(result);			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}
	
	
	

	// 다이어리 날짜 선택 조회
	@GetMapping("/diary/list/{regDate}")
	public ResponseEntity<?> getUserDiaryListByRegDate(@PathVariable("regDate") String regDate, HttpSession session, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
//		List<Diary> list = diaryService.selectDiaryByRegDate(session.getId(), regDate);
		List<Diary> list = diaryService.selectDiaryByRegDate(userDetails.getUsername(), regDate);
		try {
			if(list == null || list.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No diaries found by regDate");
			}
			return ResponseEntity.status(HttpStatus.OK).body(list);			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(list);		
		}
	}
	
	// 특정 다이어리 상세 조회 
	@GetMapping("/diary/{diaryNo}")
	public ResponseEntity<?> getUserDiaryList(@PathVariable("diaryNo") int diaryNo) {
		Diary diary = diaryService.selectOneDiaryByNo(diaryNo);
		// 이게 맞나??
		if(diary == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No diaries found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(diary);
	}
	
	
	// 특정 유저의 다이어리 수정
	@PutMapping("/diary/{diaryNo}")
	public ResponseEntity<?> updateUserDiary(@PathVariable("diaryNo") int diaryNo, @RequestBody Diary diary) {
		diary.setDiaryNo(diaryNo);
		boolean isUpdated = diaryService.modifyDiary(diary);
		if(isUpdated) {
			return ResponseEntity.status(HttpStatus.OK).body(diaryNo);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Failed");
		}
	}
	
	// 다이어리 삭제 
	@DeleteMapping("/diary/{diaryNo}")
	public ResponseEntity<?> deleteUserDiary(@PathVariable("diaryNo") int diaryNo) {
		boolean isDeleted = diaryService.deleteDiary(diaryNo);
		if(isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body("Deleted Suscess");
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deleted Failed");
		}
	}
	
	
}
