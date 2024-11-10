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
	
	// 재생목록 넣기 전 => 유저별 영상 제목, 번호 조회
	List<Video> selectVideoTitle(String id);
	
	// 재생목록에 영상 추가
	int insertVideoToGroup(String categoryName, int videoNo);
	
	// 재생목록에서 영상 삭제
	int deleteVideoFromGroup(String categoryName, int videoNo);
	
	// 재생목록 조회
	List<VideoGroup> selectVideoGroup(String id);
	
	// 재생목록 추가
	int insertVideoGroup(VideoGroup videoGroup);

	// 재생목록 삭제
	int deleteVideoGroup(VideoGroup videoGroup);

	List<String> selectRegistedVideoGroup(int videoNo);
	
}
