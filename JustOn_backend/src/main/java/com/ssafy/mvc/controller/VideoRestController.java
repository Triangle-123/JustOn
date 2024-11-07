package com.ssafy.mvc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.model.dto.Video;
import com.ssafy.mvc.model.dto.VideoGroup;
import com.ssafy.mvc.model.service.VideoService;


@RestController
@RequestMapping("/api/video")
@CrossOrigin
public class VideoRestController {
	
	private VideoService videoService;
	
	public VideoRestController(VideoService videoService) {
		this.videoService = videoService;
	}
	
	@PostMapping
	public ResponseEntity<String> addVideo(@RequestBody Video video) {
		try {
			video.setUserId("ssafy");
			System.out.println(video);
			if(videoService.addVideo(video)) {
				return ResponseEntity.ok("영상이 등록되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("영상 등록에 실패하였습니다.");
		}
	}
	
	@DeleteMapping("{no}")
	public ResponseEntity<String> removeVideo(@PathVariable("no") int no) {
		try {
			if(videoService.removeVideo(no)) {
				return ResponseEntity.ok("영상이 삭제되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("영상 삭제에 실패하였습니다.");
		}
	}
	
	@GetMapping
	public ResponseEntity<?> titleList() {
		try {
			List<String> list = videoService.titleList("ssafy");
			if(list != null && !list.isEmpty()) {
				return ResponseEntity.ok(list);
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("등록된 영상이 없습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("영상 목록 불러오기 실패");
		}
	}
	
	@GetMapping("/playlist")
	public ResponseEntity<?> videoList(@RequestBody VideoGroup videoGroup) {
		try {
			List<Video> list = videoService.videoList(videoGroup);
			if(list != null && !list.isEmpty()) {
				return ResponseEntity.ok(list);
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("영상 불러오기 실패");
		}
	}
	
	@PostMapping("/playlist")
	public ResponseEntity<String> addVideoToList(@RequestBody Video video) {
		try {
			if(videoService.addVideoToList(video)) {
				return ResponseEntity.ok("재생목록에 영상이 추가되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("영상 추가에 실패하였습니다.");
		}
	}
	
//	@DeleteMapping("/playlist")
//	public ResponseEntity<String> removeVideoFromList() {
//		
//	}
	
//	@PostMapping("/playlist")
//	public ResponseEntity<String> addPlaylist(@RequestBody VideoGroup videoGroup) {
//		
//	}
	
//	@DeleteMapping("/playlist/{no}")
//	public ResponseEntity<String> removePlaylist(@PathVariable("no") int no) {
//		
//	}
	
}
