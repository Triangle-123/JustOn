package com.ssafy.mvc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.model.dto.CustomUserDetails;
import com.ssafy.mvc.model.dto.Diary;
import com.ssafy.mvc.model.service.DiaryService;
import com.ssafy.mvc.model.service.ExDataService;
import com.ssafy.mvc.model.service.VideoService;

@RestController
@RequestMapping("/api-ex")
@CrossOrigin
public class ExDataRestController {
	
	private final DiaryService diaryService;
	private final VideoService videoService;
	private final ExDataService exDataService;
	
	public ExDataRestController(DiaryService diaryService, VideoService videoService, ExDataService exDataService) {
		this.diaryService = diaryService;
		this.videoService = videoService;
		this.exDataService = exDataService;
	}
	
	@PostMapping
	public ResponseEntity<?> registExData(@RequestBody Diary diary, @AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			// diary의 regDate와 userId로 diaryNo를 가져오거나 diaryExList 가져오기
			// diaryExList의 videoNo로 diaryEx 하나당 videoExList를 가져온다.
			// 그 후 부위에 가중치를 곱하고, diaryEx의 play_Num을 곱한다
			// 결과 데이터(부위, regDate, userId)를 ExRecord 객체에 넣어 DB에 추가
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.internalServerError().build();
	}
	
	@GetMapping
	public ResponseEntity<?> getExData(@AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			// userId의 모든 ExRecord를 가져오는데
			// sum 집계함수를 이용하여 기간별(생략?) 또는 전체 부위 기록을 가져온다.
			// group by user_id
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.internalServerError().build();
	}
	
}
