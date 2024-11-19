package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.Music;
import com.ssafy.mvc.model.dto.MusicGroup;


public interface MusicDao {

	// 음악 등록
	int insertMusic(Music music);

	// 음악 삭제
	int deleteMusic(int musicNo);

	// 음악 번호로 조회
	Music selectMusicByNo(int musicNo);

	// 음악 수정
	int updateMusic(Music music);

	// 음악 조회(그룹(카테고리)별)
	List<Music> selectMusicByGroup(MusicGroup musicGroup);

	// 재생목록 넣기 전 => 유저별 음악 제목, 번호 조회
	List<Music> selectMusicTitle(String id);

	// 재생목록에 음악 추가
	int insertMusicToGroup(String playlistName, int musicNo);

	// 재생목록에서 음악 삭제
	int deleteMusicFromGroup(String playlistName, int musicNo);

	// 재생목록 조회
	List<MusicGroup> selectMusicGroup(String id);

	// 재생목록 추가
	int insertMusicGroup(MusicGroup musicGroup);

	// 재생목록 삭제
	int deleteMusicGroup(MusicGroup musicGroup);

	// 재생목록에 해당 음악이 있는지 확인
	List<String> selectRegistedMusicGroup(int musicNo);

}
