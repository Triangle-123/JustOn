package com.ssafy.mvc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.model.dto.AddVideoDTO;
import com.ssafy.mvc.model.dto.CustomUserDetails;
import com.ssafy.mvc.model.dto.Video;
import com.ssafy.mvc.model.dto.VideoEx;
import com.ssafy.mvc.model.dto.VideoGroup;
import com.ssafy.mvc.model.service.VideoService;


@RestController
@RequestMapping("/api-video")
@CrossOrigin
public class VideoRestController {
	
	private VideoService videoService;
	
	public VideoRestController(VideoService videoService) {
		this.videoService = videoService;
	}	
	
	@PostMapping
	public ResponseEntity<String> addVideo(@RequestBody AddVideoDTO addVideoDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			
			addVideoDto.getVideo().setUserId("ssafy");
//			System.out.println(addDto.video);
			if(videoService.addVideo(addVideoDto)) {
				return ResponseEntity.ok("영상이 등록되었습니다.");
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 등록된 영상입니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("영상 등록에 실패하였습니다.");
		}
	}
	
	
	
	@DeleteMapping("/{no}")
	public ResponseEntity<String> removeVideo(@PathVariable("no") int videoNo) {
		try {
			if(videoService.removeVideo(videoNo)) {
				return ResponseEntity.ok("영상이 삭제되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("영상 삭제에 실패하였습니다.");
		}
	}
	
	@GetMapping
	public ResponseEntity<?> titleList(@AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			List<Video> list = videoService.titleList(userDetails.getUsername());
			if(list != null && !list.isEmpty()) {
				return ResponseEntity.ok(list);
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("등록된 영상이 없습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("영상 목록 불러오기 실패");
		}
	}
	
	@GetMapping("/{no}")
	public ResponseEntity<?> getVideoInfo(@PathVariable("no") int videoNo) {
		try {
			Map<String, Object> result = videoService.getVideoInfo(videoNo);	
			if(result != null && !result.isEmpty()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("영상이 없습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("영상 불러오기 실패");
		}
	}
	
	@PutMapping("/{no}")
	public ResponseEntity<String> modifyVideo(@PathVariable("no") int videoNo, @RequestBody AddVideoDTO addVideoDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			addVideoDto.getVideo().setUserId(userDetails.getUsername());
			if(videoService.modifyVideo(videoNo, addVideoDto)) {
				return ResponseEntity.ok("영상 수정을 완료했습니다.");
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 등록된 영상입니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("영상 수정에 실패하였습니다.");
		}
	}
	
	@GetMapping("/{categoryName}/videos")
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
	
	@PostMapping("/{categoryName}/{videoNo}")
	public ResponseEntity<String> addVideoToList(@PathVariable("categoryName") String categoryName, @PathVariable("videoNo") int videoNo) {
		try {
			if(videoService.addVideoToList(categoryName, videoNo)) {
				return ResponseEntity.ok("재생목록에 영상이 추가되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("영상 추가에 실패하였습니다.");
		}
	}
	
	@DeleteMapping("/{categoryName}/{videoNo}")
	public ResponseEntity<String> removeVideoFromList(@PathVariable("categoryName") String categoryName, @PathVariable("videoNo") int videoNo) {
		try {
			if(videoService.removeVideoFromList(categoryName, videoNo)) {
				return ResponseEntity.ok("재생목록에서 영상이 삭제되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("영상 삭제에 실패하였습니다.");
		}
	}
	
	@GetMapping("/playlist/{videoNo}")
	public ResponseEntity<?> showPlaylist(@PathVariable("videoNo") int videoNo, @AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			Map<String, Object> result = videoService.showPlaylist(videoNo, userDetails.getUsername());
//			List<VideoGroup> list = (List<VideoGroup>) result.get("list");
			if(result != null && !result.isEmpty()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("재생목록이 없습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("재생목록 불러오기에 실패하였습니다.");
		}
	}
	
	@PostMapping("/playlist")
	public ResponseEntity<String> addPlaylist(@RequestBody VideoGroup videoGroup, @AuthenticationPrincipal CustomUserDetails userDetails) {
		videoGroup.setUserId(userDetails.getUsername());
		try {
			if(videoService.addPlaylist(videoGroup)) {
				return ResponseEntity.ok("재생목록이 추가되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("재생목록 추가에 실패하였습니다.");
		}
	}
		
	@DeleteMapping("/playlist/{categoryName}")
	public ResponseEntity<String> removePlaylist(@PathVariable("categoryName") String categoryName, @AuthenticationPrincipal CustomUserDetails userDetails) 	{
		VideoGroup videoGroup = new VideoGroup();
		videoGroup.setCategoryName(categoryName);
		videoGroup.setUserId(userDetails.getUsername());
		try {
			if(videoService.removePlaylist(videoGroup)) {
				return ResponseEntity.ok("재생목록이 삭제되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("재생목록 삭제에 실패하였습니다.");
		}
	}
	
}