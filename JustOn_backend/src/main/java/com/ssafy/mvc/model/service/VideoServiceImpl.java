package com.ssafy.mvc.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.VideoDao;
import com.ssafy.mvc.model.dto.Video;
import com.ssafy.mvc.model.dto.VideoGroup;

@Service
public class VideoServiceImpl implements VideoService {

	private final VideoDao videoDao;

	public VideoServiceImpl(VideoDao videoDao) {
		this.videoDao = videoDao;
	}
	
	@Override
	public boolean addVideo(Video video) {
		return videoDao.insertVideo(video) >= 1;
	}

	@Override
	public boolean removeVideo(int no) {
		return videoDao.deleteVideo(no) >= 1;
	}

	@Override
	public List<Video> videoList(VideoGroup videoGroup) {
		return videoDao.selectVideoByGroup(videoGroup);
	}

	@Override
	public List<String> titleList(String id) {
		return videoDao.selectVideoTitle(id);
	}

	@Override
	public boolean addVideoToList(Video video) {
		return videoDao.insertVideoToGroup(video) >= 1;
	}

	@Override
	public boolean removeVideoFromList(Map<String, Integer> map) {
		return videoDao.deleteVideoFromGroup(map) >= 1;
	}

	@Override
	public boolean addPlaylist(VideoGroup videoGroup) {
		return videoDao.insertVideoGroup(videoGroup) >= 1;
	}

	@Override
	public boolean removePlaylist(int no) {
		return videoDao.deleteVideoGroup(no) >= 1;
	}

}
