package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.AddVideoDTO;
import com.ssafy.mvc.model.dto.Video;
import com.ssafy.mvc.model.dto.VideoEx;
import com.ssafy.mvc.model.dto.VideoGroup;

public interface VideoDao {
	
	// 영상 등록
	int insertVideo(Video video);
	
	// 영상 삭제
	int deleteVideo(int videoNo);
	
	// 영상 번호로 조회
	Video selectVideoByNo(int videoNo);
	
	// 영상 수정
	int updateVideo(Video video);

	// 영상 번호 조회(그룹(카테고리)별)
	List<Integer> selectVideoByGroup(String categoryName, String id);
	
	// 재생목록 넣기 전 => 유저별 영상 제목, 번호 조회
	List<Video> selectVideoTitle(String id);
	
	// 재생목록에 영상 추가
	int insertVideoToGroup(String categoryName, int videoNo, String id);
	
	// 재생목록에서 영상 삭제
	int deleteVideoFromGroup(String categoryName, int videoNo);
	
	// 재생목록 조회
	List<VideoGroup> selectVideoGroup(String id);
	
	// 재생목록 추가
	int insertVideoGroup(VideoGroup videoGroup);

	// 재생목록 삭제
	int deleteVideoGroup(VideoGroup videoGroup);

	// 재생목록에 해당 영상이 있는지 확인
	List<String> selectRegistedVideoGroup(int videoNo);
	
	// 영상에 대한 운동정보 등록
	int insertVideoEx(AddVideoDTO addVideoDto);
	
	List<String> selectVideoExColumnName();
	
	List<VideoEx> selectVideoEx(List<String> column, int videoNo);
	
	// 영상의 운동 정보 삭제
	int deleteVideoEx(int videoNo);
	
}