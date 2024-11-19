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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.model.dto.CustomUserDetails;
import com.ssafy.mvc.model.dto.Music;
import com.ssafy.mvc.model.dto.MusicGroup;
import com.ssafy.mvc.model.service.MusicService;

@RestController
@RequestMapping("/api-music")
@CrossOrigin
public class MusicRestController {
	private MusicService musicService;
	
	public MusicRestController(MusicService musicService) {
		this.musicService = musicService;
	}
	
	@PostMapping
	public ResponseEntity<String> addMusic(@RequestBody Music music, @AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			music.setUserId(userDetails.getUsername());
//			System.out.println(addDto.video);
			if(musicService.addMusic(music)) {
				return ResponseEntity.ok("음악이 등록되었습니다.");
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 등록된 음악입니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("음악 등록에 실패하였습니다.");
		}
	}
	
	
	
	@DeleteMapping("/{no}")
	public ResponseEntity<String> removeMusic(@PathVariable("no") int musicNo) {
		try {
			if(musicService.removeMusic(musicNo)) {
				return ResponseEntity.ok("음악이 삭제되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("음악 삭제에 실패하였습니다.");
		}
	}
	
	@GetMapping
	public ResponseEntity<?> titleList(@AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			List<Music> list = musicService.titleList(userDetails.getUsername());
			if(list != null && !list.isEmpty()) {
				return ResponseEntity.ok(list);
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("등록된 음악이 없습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("음악 목록 불러오기 실패");
		}
	}
	
	@GetMapping("/{no}")
	public ResponseEntity<?> getMusicInfo(@PathVariable("no") int musicNo) {
		try {
			Music music = musicService.getMusicInfo(musicNo);	
			if(music != null) {
				return ResponseEntity.ok(music);
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("음악이 없습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("음악 불러오기 실패");
		}
	}
	
	@PutMapping("/{no}")
	public ResponseEntity<String> modifyMusic(@PathVariable("no") int musicNo, @RequestBody Music music, @AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			music.setUserId(userDetails.getUsername());
			if(musicService.modifyMusic(musicNo, music)) {
				return ResponseEntity.ok("음악 수정을 완료했습니다.");
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 등록된 음악입니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("음악 수정에 실패하였습니다.");
		}
	}
	
	@GetMapping("/{playlistName}/music")
	public ResponseEntity<?> musicList(@RequestBody MusicGroup musicGroup) {
		try {
			List<Music> list = musicService.musicList(musicGroup);
			if(list != null && !list.isEmpty()) {
				return ResponseEntity.ok(list);
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("음악 불러오기 실패");
		}
	}
	
	@PostMapping("/{playlistName}/{musicNo}")
	public ResponseEntity<String> addMusicToList(@PathVariable("playlistName") String playlistName, @PathVariable("musicNo") int musicNo) {
		try {
			if(musicService.addMusicToList(playlistName, musicNo)) {
				return ResponseEntity.ok("재생목록에 음악이 추가되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("음악 추가에 실패하였습니다.");
		}
	}
	
	@DeleteMapping("/{playlistName}/{musicNo}")
	public ResponseEntity<String> removeMusicFromList(@PathVariable("playlistName") String playlistName, @PathVariable("musicNo") int musicNo) {
		try {
			if(musicService.removeMusicFromList(playlistName, musicNo)) {
				return ResponseEntity.ok("재생목록에서 음악이 삭제되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("음악 삭제에 실패하였습니다.");
		}
	}
	
	@GetMapping("/playlist/{musicNo}")
	public ResponseEntity<?> showPlaylist(@PathVariable("musicNo") int musicNo, @AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			Map<String, Object> result = musicService.showPlaylist(musicNo, userDetails.getUsername());
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
	public ResponseEntity<String> addPlaylist(@RequestBody MusicGroup musicGroup, @AuthenticationPrincipal CustomUserDetails userDetails) {
		musicGroup.setUserId(userDetails.getUsername());
		try {
			if(musicService.addPlaylist(musicGroup)) {
				return ResponseEntity.ok("재생목록이 추가되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("재생목록 추가에 실패하였습니다.");
		}
	}
		
	@DeleteMapping("/playlist/{playlistName}")
	public ResponseEntity<String> removePlaylist(@PathVariable("playlistName") String playlistName, @AuthenticationPrincipal CustomUserDetails userDetails) {
		MusicGroup musicGroup = new MusicGroup();
		musicGroup.setPlaylistName(playlistName);
		musicGroup.setUserId(userDetails.getUsername());
		try {
			if(musicService.removePlaylist(musicGroup)) {
				return ResponseEntity.ok("재생목록이 삭제되었습니다.");
			}
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("재생목록 삭제에 실패하였습니다.");
		}
	}
}
