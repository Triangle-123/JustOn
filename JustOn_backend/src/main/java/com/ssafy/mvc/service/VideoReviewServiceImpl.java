package com.ssafy.mvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.VideoReviewDao;
import com.ssafy.mvc.model.dto.VideoReview;

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
	public List<VideoReview> reviewList() {
		return videoReviewDao.selectReview();
	}

	@Override
	public boolean modify(VideoReview videoReview) {
		return videoReviewDao.updateReview(videoReview) == 1;
		
	}

	@Override
	public boolean remove(int reviewNo) {
		return videoReviewDao.deleteReview(reviewNo) == 1;
	}
	
	@Override
	public List<VideoReview> replyList(int parentNo) {
		return videoReviewDao.selectReply(parentNo);
	}
	
}
