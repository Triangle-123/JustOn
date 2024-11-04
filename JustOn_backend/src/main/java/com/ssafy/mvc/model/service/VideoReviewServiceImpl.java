package com.ssafy.mvc.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.VideoReviewDao;
import com.ssafy.mvc.model.dto.ReviewSearch;
import com.ssafy.mvc.model.dto.VideoReview;
import com.ssafy.mvc.util.PageResult;

@Service
public class VideoReviewServiceImpl implements VideoReviewService {

	private final VideoReviewDao videoReviewDao;
	
	public VideoReviewServiceImpl(VideoReviewDao videoReviewDao) {
		this.videoReviewDao = videoReviewDao;
	}
	
	@Override
	public boolean regist(VideoReview videoReview) {
		return videoReviewDao.insertReview(videoReview) == 1;
	}

	@Override
	public Map<String, Object> reviewList(ReviewSearch reviewSearch) {
		List<VideoReview> list = videoReviewDao.selectReview(reviewSearch);
		
		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("pr", new PageResult(reviewSearch.getPage(), videoReviewDao.selectReviewCount(reviewSearch), reviewSearch.getListSize()));
		return result;
	}

	@Override
	public boolean modify(VideoReview videoReview) {
		return videoReviewDao.updateReview(videoReview) == 1;
		
	}

	@Override
	public boolean remove(int reviewNo) {
		return videoReviewDao.deleteReview(reviewNo) >= 1;
	}
	
	@Override
	public List<VideoReview> replyList(int parentNo) {
		return videoReviewDao.selectReply(parentNo);
	}
	
}
