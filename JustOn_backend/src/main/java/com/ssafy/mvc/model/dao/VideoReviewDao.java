package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.ReviewSearch;
import com.ssafy.mvc.model.dto.VideoReview;

public interface VideoReviewDao {
	// 댓글/답글 등록
	int insertReview(VideoReview videoReview);
	
	// 댓글 가져오기
	List<VideoReview> selectReview(ReviewSearch reviewSearch);
	
	// 댓글 수정
	int updateReview(VideoReview videoReview);
	
	// 댓글 삭제
	int deleteReview(int reviewNo);
	
	// 답글 가져오기
	List<VideoReview> selectReply(int parentNo);
	
	int selectReviewCount(ReviewSearch reviewSearch);
}
