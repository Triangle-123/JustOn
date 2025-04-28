package com.ssafy.mvc.controller;

import java.util.ArrayList;
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
import com.ssafy.mvc.model.dto.DiaryEx;
import com.ssafy.mvc.model.dto.ExRecord;
import com.ssafy.mvc.model.dto.VideoEx;
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
	public ResponseEntity<?> registExData(@RequestBody Diary diary,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			List<DiaryEx> diaryExList = diary.getDiaryExList();
			List<ExRecord> exList = new ArrayList<>();
			for (int i = 0; i < diaryExList.size(); i++) {
				DiaryEx d = diaryExList.get(i);
				int playNum = d.getPlayNum();
				List<VideoEx> list = (List<VideoEx>) videoService.getVideoInfo(d.getVideoNo()).get("videoEx");
				int weight = list.get(0).getExWeight();
				for (int j = 0; j < list.size(); j++) {
					VideoEx v = list.get(j);
					int partValue = v.getSelCnt() * weight * playNum;
					if (i == 0) {
						ExRecord exRecord = new ExRecord();
						exRecord.setPart(v.getPart());
						exRecord.setPartValue(partValue);
						exRecord.setExDate(diary.getRegDate());
						exRecord.setUserId(userDetails.getUsername());
						exList.add(exRecord);
					} else {
						ExRecord e = exList.get(j);
						e.setPartValue(e.getPartValue() + partValue);
						exList.set(j, e);
					}
				}

			}
			if (!exList.isEmpty()) {
				if (exDataService.registExData(exList)) {
					return ResponseEntity.ok("운동기록을 저장하였습니다.");
				} else
					throw new Exception();
			} else
				return ResponseEntity.noContent().build();

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("운동기록을 저장에 실패하였습니다.");
		}
	}

	@GetMapping
	public ResponseEntity<?> getExData(@AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			// userId의 모든 ExRecord를 가져오는데
			// sum 집계함수를 이용하여 기간별(생략?) 또는 전체 부위 기록을 가져온다.
			// group by user_id
			List<ExRecord> list = exDataService.getExData(userDetails.getUsername());
			if(list != null && !list.isEmpty()) {
				return ResponseEntity.ok(list);
			}
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

}
