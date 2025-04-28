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
	List<Music> musicList(String playlistName, String id);

	// 재생목록 넣기 전 => 유저별 음악 제목 조회
	List<Music> titleList(String id);

	// 재생목록에 음악 추가
	boolean addMusicToList(String playlistName, int musicNo, String id);

	// 재생목록에서 음악 삭제
	boolean removeMusicFromList(String playlistName, int musicNo);

	// 재생목록 조회
	List<MusicGroup> showPlaylist(String id);
	
	// 재생목록 조회(현재 비디오가 추가되어 있는지 확인)
	Map<String, Object> showRegistedPlaylist(int musicNo, String id);

	// 재생목록 추가
	boolean addPlaylist(MusicGroup musicGroup);

	// 재생목록 삭제
	boolean removePlaylist(MusicGroup musicGroup);
}
