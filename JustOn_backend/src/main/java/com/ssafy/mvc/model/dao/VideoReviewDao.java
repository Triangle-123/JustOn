package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.VideoReview;

public interface VideoReviewDao {
	// 댓글/답글 등록
	void insertReview(VideoReview videoReview);
	
	// 댓글 가져오기
	List<VideoReview> selectReview();
	
	// 댓글 수정
	void updateReview(int reviewNo);
	
	// 댓글 삭제
	void deleteReview(int reviewNo);
	
	// 답글 가져오기
	List<VideoReview> selectReply(int parentNo);
}
