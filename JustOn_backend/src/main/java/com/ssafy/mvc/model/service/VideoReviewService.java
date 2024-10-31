package com.ssafy.mvc.model.service;

import java.util.List;

import com.ssafy.mvc.model.dto.VideoReview;

public interface VideoReviewService {
	boolean regist(VideoReview videoReview);
	List<VideoReview> reviewList();
	boolean modify(VideoReview videoReview);
	boolean remove(int reviewNo);
	List<VideoReview> replyList(int parentNo);
}
