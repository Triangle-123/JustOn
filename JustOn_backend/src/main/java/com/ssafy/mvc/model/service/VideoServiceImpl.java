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
		int insertVideo = videoDao.insertVideo(addVideoDto.getVideo());
		if(insertVideo == 0) return false;
		int insertEx = videoDao.insertVideoEx(addVideoDto);
		return insertVideo >= 1 && insertEx >= 1;
		
	}

	@Override
	public boolean removeVideo(int videoNo) {
		return videoDao.deleteVideo(videoNo) >= 1;
	}

	@Override
	public Map<String, Object> getVideoInfo(int videoNo) {
		Map<String, Object> result = new HashMap<>();
		result.put("video", videoDao.selectVideoByNo(videoNo));
		List<String> column = videoDao.selectVideoExColumnName();
		result.put("videoEx", videoDao.selectVideoEx(column, videoNo));
		return result;
	}
	
	@Override
	public boolean modifyVideo(int videoNo, AddVideoDTO addVideoDto) {
		addVideoDto.getVideo().setVideoNo(videoNo);
		int updateVideo = videoDao.updateVideo(addVideoDto.getVideo());
		if(updateVideo == 0) return false;
		int deleteEx = videoDao.deleteVideoEx(videoNo);
		int insertEx = videoDao.insertVideoEx(addVideoDto);
		return updateVideo >= 1 && deleteEx >= 1 && insertEx >= 1;
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