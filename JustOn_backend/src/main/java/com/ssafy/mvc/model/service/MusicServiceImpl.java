package com.ssafy.mvc.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.MusicDao;
import com.ssafy.mvc.model.dto.Music;
import com.ssafy.mvc.model.dto.MusicGroup;

@Service
public class MusicServiceImpl implements MusicService{

	private final MusicDao musicDao;
	
	public MusicServiceImpl(MusicDao musicDao) {
		this.musicDao = musicDao;
	}
	
	@Override
	public boolean addMusic(Music music) {
		return musicDao.insertMusic(music) >= 1;
	}

	@Override
	public boolean removeMusic(int musicNo) {
		return musicDao.deleteMusic(musicNo) >= 1;
	}

	@Override
	public Music getMusicInfo(int musicNo) {
		return musicDao.selectMusicByNo(musicNo);
	}

	@Override
	public boolean modifyMusic(int musicNo, Music music) {
		music.setMusicNo(musicNo);
		return musicDao.updateMusic(music) >= 1;
	}

	@Override
	public List<Music> musicList(String playlistName, String id) {
		List<Integer> musicNums = musicDao.selectMusicByGroup(playlistName, id);
		List<Music> list = new ArrayList<>();
		for(int musicNo : musicNums) {
			list.add(musicDao.selectMusicByNo(musicNo));
		}
		return list;
	}

	@Override
	public List<Music> titleList(String id) {
		return musicDao.selectMusicTitle(id);
	}

	@Override
	public boolean addMusicToList(String playlistName, int musicNo, String id) {
		return musicDao.insertMusicToGroup(playlistName, musicNo, id) >= 1;
	}

	@Override
	public boolean removeMusicFromList(String playlistName, int musicNo) {
		return musicDao.deleteMusicFromGroup(playlistName, musicNo) >= 1;
	}

	@Override
	public List<MusicGroup> showPlaylist(String id) {
		return musicDao.selectMusicGroup(id);
	}
	
	@Override
	public Map<String, Object> showRegistedPlaylist(int musicNo, String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("list", musicDao.selectMusicGroup(id));
		map.put("registedList", musicDao.selectRegistedMusicGroup(musicNo));
		return map;
	}

	@Override
	public boolean addPlaylist(MusicGroup musicGroup) {
		return musicDao.insertMusicGroup(musicGroup) >= 1;
	}

	@Override
	public boolean removePlaylist(MusicGroup musicGroup) {
		return musicDao.deleteMusicGroup(musicGroup) >= 1;
	}


}
