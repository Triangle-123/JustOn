DROP DATABASE IF EXISTS just_on;
CREATE DATABASE IF NOT EXISTS just_on;

USE just_on;

# 유저 정보 테이블
CREATE TABLE `user` (
	user_id VARCHAR(100) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(100) NOT NULL,
    gender CHAR(1) CHECK(gender IN ('F', 'M')),
    post_code INT NOT NULL,
    address VARCHAR(100) NOT NULL,
    extra_address VARCHAR(100),
    detail_address VARCHAR(100) NOT NULL,
    nickname VARCHAR(100) NOT NULL,
    birth DATE NOT NULL,
    role VARCHAR(30) NOT NULL,
    color VARCHAR(100)
);

# 유저 선호운동 정보
CREATE TABLE user_like_ex (
	ex_id VARCHAR(6) PRIMARY KEY,
    part VARCHAR(30) NOT NULL,
    user_id VARCHAR(100) NOT NULL, 
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

# 유저 프로필 이미지
CREATE TABLE user_profile_img ( 
	file_no INT AUTO_INCREMENT PRIMARY KEY,
    file_path VARCHAR(255) NOT NULL,
    ori_name VARCHAR(255) NOT NULL,
    system_name VARCHAR(255) NOT NULL UNIQUE,
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id VARCHAR(100) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

# 사용자별 운동 일기
CREATE TABLE diary (
	diary_no INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    reg_date DATE NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    UNIQUE(user_id, reg_date)
);

# 운동일기 상의 운동기록
CREATE TABLE diary_ex (
	diary_ex_no INT AUTO_INCREMENT PRIMARY KEY,
    play_num INT NOT NULL,
    diary_no INT NOT NULL,
    video_no INT NOT NULL,
    FOREIGN KEY(diary_no) REFERENCES diary(diary_no) ON DELETE CASCADE
    # FOREIGN KEY(video_no) REFERENCES video(video_no)
);


SELECT * FROM diary_ex;

# 플레이리스트 그룹
CREATE TABLE music_group (
    playlist_name VARCHAR(100) NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    group_desc VARCHAR(100),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    PRIMARY KEY(playlist_name, user_id)
);

# 플레이리스트에 들어갈 음악
CREATE TABLE music (
	music_no INT AUTO_INCREMENT PRIMARY KEY,
    music_id VARCHAR(50) NOT NULL,
    src VARCHAR(255) NOT NULL,
    title VARCHAR(200) NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

CREATE TABLE playlist_music(
	playlist_name VARCHAR(100) NOT NULL,
    music_no INT NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    FOREIGN KEY(playlist_name) REFERENCES music_group(playlist_name) ON DELETE CASCADE,
    FOREIGN KEY(music_no) REFERENCES music(music_no) ON DELETE CASCADE,
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    PRIMARY KEY(playlist_name, music_no)
);

# 영상 카테고리 정보
CREATE TABLE IF NOT EXISTS video_group(
    category_name VARCHAR(100) NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    group_desc VARCHAR(100),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    PRIMARY KEY(category_name, user_id)
);


# 영상 정보
CREATE TABLE IF NOT EXISTS video(
    video_no INT NOT NULL AUTO_INCREMENT,
    video_id VARCHAR(50) NOT NULL,
    src VARCHAR(200) NOT NULL,
    title VARCHAR(200) NOT NULL,
    view_cnt INT DEFAULT 0,
    user_id VARCHAR(100) NOT NULL,
    thumb VARCHAR(200) NOT NULL,
    PRIMARY KEY(video_no),
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

# 영상 리뷰 정보
CREATE TABLE IF NOT EXISTS video_review(
    review_no INT NOT NULL AUTO_INCREMENT,
    content VARCHAR(300) NOT NULL,
    writer VARCHAR(100) NOT NULL,
    reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    parent_no INT,
    video_no INT,
    user_id VARCHAR(100),
    PRIMARY KEY(review_no),
    FOREIGN KEY(video_no) REFERENCES video(video_no) ON DELETE CASCADE,
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

# 운동 기록 (계산용) 
CREATE TABLE IF NOT EXISTS ex_record(
    ex_record_no INT NOT NULL AUTO_INCREMENT,
    user_id VARCHAR(100) NOT NULL,
    ex_date DATE NOT NULL,
    shoulder INT NOT NULL,
    leg INT NOT NULL,
    abs INT NOT NULL,
    chest INT NOT NULL,
    back INT NOT NULL,
    arm INT NOT NULL,
    stretching INT NOT NULL,
    cardio INT NOT NULL,
    PRIMARY KEY(ex_record_no),
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    UNIQUE(user_id, ex_date),
    FOREIGN KEY(user_id, ex_date) REFERENCES diary(user_id, reg_date) ON DELETE CASCADE
);



# 사용자가 등록한 운동영상에 포함된 운동 정보
CREATE TABLE IF NOT EXISTS video_ex_list(
    video_ex_no INT NOT NULL AUTO_INCREMENT, 
    shoulder INT,
    leg INT,
    abs INT,
    chest INT,
    back INT,
    arm INT,
    stretching INT,
    cardio INT,
    ex_weight INT,
    video_no INT,
    user_id VARCHAR(100),
    PRIMARY KEY(video_ex_no),
    FOREIGN KEY(video_no) REFERENCES video(video_no) ON DELETE CASCADE,
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE
);


# 영상별 소속된 플레이리스트 정보
CREATE TABLE IF NOT EXISTS category_video(
    video_no INT NOT NULL, 
    category_name VARCHAR(100) NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    FOREIGN KEY(video_no) REFERENCES video(video_no) ON DELETE CASCADE,
    FOREIGN KEY(category_name) REFERENCES video_group(category_name) ON DELETE CASCADE,
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    PRIMARY KEY(video_no, category_name)
);

-- INSERT INTO `user`
-- VALUES('ssafy', '1234', '김싸피', 'ssafy@ssafy.com', 'M', '대전 유성구', 'KimSSAFY', '2024-07-02');

-- INSERT INTO `diary` (content, reg_date, user_id)
-- VALUES ('내용입니다1', '2024-11-11', 'ssafy'),
-- 	   ('내용입니다2', '2024-11-12', 'ssafy'),
--        ('내용입니다3', '2024-11-13', 'ssafy');

SELECT count(*)
FROM user
WHERE user_id = 'ssafy';

SELECT * FROM diary;

SELECT * FROM user;

SELECT * FROM video;

SELECT * FROM video_review;

SELECT * FROM video_group;

SELECT * FROM category_video;	

SELECT * FROM video_ex_list;

SELECT * FROM music;

SELECT * FROM diary_ex;

SELECT * FROM ex_record;