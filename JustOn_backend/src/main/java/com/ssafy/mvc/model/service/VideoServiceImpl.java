package com.ssafy.mvc.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.VideoDao;
import com.ssafy.mvc.model.dto.AddVideoDTO;
import com.ssafy.mvc.model.dto.Video;
import com.ssafy.mvc.model.dto.VideoGroup;

@Service
public class VideoServiceImpl implements VideoService {

	private final VideoDao videoDao;

	public VideoServiceImpl(VideoDao videoDao) {
		this.videoDao = videoDao;
	}
	
	@Override
	public boolean addVideo(AddVideoDTO addVideoDto) {
		int videoCnt = videoDao.insertVideo(addVideoDto.getVideo());
		int videoExCnt = videoDao.insertVideoEx(addVideoDto.getVideoExList(), addVideoDto.getVideo().getVideoNo());
		return videoCnt >= 1 && videoExCnt >= 1;
		
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
	public List<Video> titleList(String id) {
		return videoDao.selectVideoTitle(id);
	}

	@Override
	public boolean addVideoToList(String categoryName, int videoNo) {
		return videoDao.insertVideoToGroup(categoryName, videoNo) >= 1;
	}

	@Override
	public boolean removeVideoFromList(String categoryName, int videoNo) {
		return videoDao.deleteVideoFromGroup(categoryName, videoNo) >= 1;
	}

	@Override
	public Map<String, Object> showPlaylist(int videoNo, String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("list", videoDao.selectVideoGroup(id));
		map.put("registedList", videoDao.selectRegistedVideoGroup(videoNo));
		return map;
	}
	
	@Override
	public boolean addPlaylist(VideoGroup videoGroup) {
		return videoDao.insertVideoGroup(videoGroup) >= 1;
	}

	@Override
	public boolean removePlaylist(VideoGroup videoGroup) {
		return videoDao.deleteVideoGroup(videoGroup) >= 1;
	}

}
