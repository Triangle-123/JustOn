package com.ssafy.mvc.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.mvc.model.dto.ReviewSearch;
import com.ssafy.mvc.model.dto.VideoReview;

public interface VideoReviewService {
	boolean regist(VideoReview videoReview);
	Map<String, Object> reviewList(ReviewSearch reviewSearch);
	boolean modify(VideoReview videoReview);
	boolean remove(int reviewNo);
	List<VideoReview> replyList(int parentNo);
}
