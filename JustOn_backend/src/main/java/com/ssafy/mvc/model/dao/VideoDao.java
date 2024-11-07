package com.ssafy.mvc.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.mvc.model.dto.Video;
import com.ssafy.mvc.model.dto.VideoGroup;

public interface VideoDao {
	
	// 영상 등록
	int insertVideo(Video video);
	
	// 영상 삭제
	int deleteVideo(int no);

	// 영상 조회(그룹(카테고리)별)
	List<Video> selectVideoByGroup(VideoGroup videoGroup);
	
	// 재생목록 넣기 전 => 유저별 영상 제목 조회
	List<String> selectVideoTitle(String id);
	
	// 재생목록에 영상 추가
	int insertVideoToGroup(Video video);
	
	// 재생목록에서 영상 삭제
	int deleteVideoFromGroup(Map<String, Integer> map);
	
	// 재생목록 추가
	int insertVideoGroup(VideoGroup videoGroup);

	// 재생목록 삭제
	int deleteVideoGroup(int no);
	
}
