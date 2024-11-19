package com.ssafy.mvc.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.mvc.model.dto.Music;
import com.ssafy.mvc.model.dto.MusicGroup;

public interface MusicService {
	
	// 음악 등록
	boolean addMusic(Music music);

	// 음악 삭제
	boolean removeMusic(int musicNo);

	Music getMusicInfo(int musicNo);

	boolean modifyMusic(int musicNo, Music music);

	// 음악 조회(그룹(카테고리)별)
	List<Music> musicList(MusicGroup musicGroup);

	// 재생목록 넣기 전 => 유저별 음악 제목 조회
	List<Music> titleList(String id);

	// 재생목록에 음악 추가
	boolean addMusicToList(String playlistName, int musicNo);

	// 재생목록에서 음악 삭제
	boolean removeMusicFromList(String playlistName, int musicNo);

	Map<String, Object> showPlaylist(int musicNo, String id);

	// 재생목록 추가
	boolean addPlaylist(MusicGroup musicGroup);

	// 재생목록 삭제
	boolean removePlaylist(MusicGroup musicGroup);
}
