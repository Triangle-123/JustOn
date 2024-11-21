package com.ssafy.mvc.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.mvc.model.dto.AddVideoDTO;
import com.ssafy.mvc.model.dto.Video;
import com.ssafy.mvc.model.dto.VideoGroup;

public interface VideoService {
	
		// 영상 등록
		boolean addVideo(AddVideoDTO addVideoDto);
		
		// 영상 삭제
		boolean removeVideo(int videoNo);

		Map<String, Object> getVideoInfo(int videoNo);
		
		boolean modifyVideo(int videoNo, AddVideoDTO addVideoDto);
		
		// 영상 조회(그룹(카테고리)별)
		List<Video> videoList(String categoryName, String id);
		
		// 재생목록 넣기 전 => 유저별 영상 제목 조회
		List<Video> titleList(String id);
		
		// 재생목록에 영상 추가
		boolean addVideoToList(String categoryName, int videoNo, String id);
		
		// 재생목록에서 영상 삭제
		boolean removeVideoFromList(String categoryName, int videoNo);
		
		List<VideoGroup> showPlaylist(String id);
		
		Map<String, Object> showRegistedPlaylist(int videoNo, String id);
		
		// 재생목록 추가
		boolean addPlaylist(VideoGroup videoGroup);

		// 재생목록 삭제
		boolean removePlaylist(VideoGroup videoGroup);

}