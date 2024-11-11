package com.ssafy.mvc.model.dto;

import java.util.List;

public class AddVideoDTO {
	Video video;
	List<VideoEx> videoExList;
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public List<VideoEx> getVideoExList() {
		return videoExList;
	}
	public void setVideoExList(List<VideoEx> videoExList) {
		this.videoExList = videoExList;
	}
	public AddVideoDTO(Video video, List<VideoEx> videoExList) {
		super();
		this.video = video;
		this.videoExList = videoExList;
	}
	public AddVideoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "AddVideoDTO [video=" + video + ", videoExList=" + videoExList + "]";
	}
}
