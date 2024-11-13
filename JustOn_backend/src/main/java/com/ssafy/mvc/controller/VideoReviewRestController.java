package com.ssafy.mvc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.model.dto.ReviewSearch;
import com.ssafy.mvc.model.dto.VideoReview;
import com.ssafy.mvc.model.service.VideoReviewService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/review")
@CrossOrigin
public class VideoReviewRestController {
	
	private final VideoReviewService ReviewService;
	
	public VideoReviewRestController(VideoReviewService ReviewService) {
		this.ReviewService = ReviewService;
	}
	
	@PostMapping
	public ResponseEntity<String> regist(@RequestBody VideoReview videoReview, HttpSession session) {
		try {
//			User user = (User)session.getAttribute("user");
//			videoreview.setUserId(user.getId());
			if(ReviewService.regist(videoReview)) {
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.internalServerError().build();			
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping
	public ResponseEntity<?> reviewList(ReviewSearch reviewSearch) {
		System.out.println(reviewSearch.getOffset() + " " + reviewSearch.getListSize());
		Map<String, Object> result = ReviewService.reviewList(reviewSearch);
		try {
			if(result.get("list") != null) {
				return ResponseEntity.ok(result);
			} 
			return ResponseEntity.noContent().build();
		} catch(Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/{no}")
	public ResponseEntity<String> modify(@PathVariable("no") int no, @RequestBody VideoReview videoReview) {
		videoReview.setReviewNo(no);
		try {
			if(ReviewService.modify(videoReview)) {
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.internalServerError().build();			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}	
	}
	
	@DeleteMapping("/{no}")
	public ResponseEntity<String> remove(@PathVariable("no") int no) {
		try {
			if(ReviewService.remove(no)) {
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.internalServerError().build();			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}	
	}
	
	@GetMapping("/{no}")
	public ResponseEntity<?> replyList(@PathVariable("no") int no) {
		List<VideoReview> list = ReviewService.replyList(no);
		try {
			if(list != null) {
				return ResponseEntity.ok(list);
			}
			return ResponseEntity.noContent().build();			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
}