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
    adress VARCHAR(100) NOT NULL,
    nickname VARCHAR(100) NOT NULL UNIQUE,
    birth DATE NOT NULL
);

INSERT INTO `user`
VALUES('ssafy', '1234', '김싸피', 'ssafy@ssafy.com', 'M', '대전 유성구', 'KimSSAFY', '2024-07-02');

SELECT * FROM user;

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
    title VARCHAR(300) NOT NULL,
    content TEXT NOT NULL,
    reg_date DATE DEFAULT (CURRENT_DATE()),
    user_id VARCHAR(100) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

INSERT INTO `diary` (title, content, reg_date, user_id)
VALUES ('제목입니다1','내용입니다1', CURDATE(), 'ssafy'),
	   ('제목입니다2','내용입니다2', CURDATE(), 'ssafy'),
       ('제목입니다3','내용입니다3', CURDATE(), 'ssafy');

SELECT * FROM diary;

# 운동일기 상의 운동기록
CREATE TABLE diary_ex (
	diary_ex_no INT AUTO_INCREMENT PRIMARY KEY,
    play_num INT NOT NULL,
    diary_no INT NOT NULL,
    video_no INT NOT NULL,
    FOREIGN KEY(diary_no) REFERENCES diary(diary_no) ON DELETE CASCADE
    # FOREIGN KEY(video_no) REFERENCES video(video_no)
);

INSERT INTO `diary_ex` (play_num, diary_no, video_no)
VALUES (3, 2, 1),
	   (3, 2, 2),
       (3, 2, 3);

SELECT * FROM diary_ex;

# 플레이리스트 그룹
CREATE TABLE music_group (
	playlist_no INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    playlist_name VARCHAR(30) NOT NULL, # PRIMARY KEY,
    group_desc VARCHAR(100),
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

# 플레이리스트에 들어갈 음악
CREATE TABLE music (
	music_id INT AUTO_INCREMENT PRIMARY KEY,
    src VARCHAR(255) NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    playlist_no INT NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY(playlist_no) REFERENCES music_group(playlist_no) ON DELETE CASCADE
);

# 영상 카테고리 정보
CREATE TABLE IF NOT EXISTS video_group(
    category_name VARCHAR(30),
    user_id VARCHAR(100) NOT NULL,
    group_desc VARCHAR(100),
    PRIMARY KEY(category_name),
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

# 영상 정보
CREATE TABLE IF NOT EXISTS video(
    video_no INT NOT NULL AUTO_INCREMENT,
    video_id VARCHAR(50) NOT NULL,
    src VARCHAR(200),
    title VARCHAR(50),
    view_cnt INT,
    user_id VARCHAR(100),
    PRIMARY KEY(video_no),
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

# 영상 리뷰 정보
CREATE TABLE IF NOT EXISTS video_review(
    review_no INT NOT NULL AUTO_INCREMENT,
    content VARCHAR(300) NOT NULL,
    writer VARCHAR(100) NOT NULL,
    reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
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
    part VARCHAR(30) NOT NULL,
    ex_date DATE,
    ex_weight INT, 
    ex_nums INT NOT NULL,
    PRIMARY KEY(ex_record_no),
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE
);



# 사용자가 등록한 운동영상에 포함된 운동 정보
CREATE TABLE IF NOT EXISTS video_ex_list(
    video_ex_no INT NOT NULL AUTO_INCREMENT, 
    part VARCHAR(30),
    ex_nums INT,
    ex_weight INT,
    video_no INT,
    user_id VARCHAR(100),
    PRIMARY KEY(video_ex_no),
    FOREIGN KEY(video_no) REFERENCES video(video_no) ON DELETE CASCADE,
    FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE
);


# 영상별 소속된 플레이리스트 정보
CREATE TABLE IF NOT EXISTS category_video(
  video_no INT NOT NULL AUTO_INCREMENT, 
  category_name VARCHAR(30),
  FOREIGN KEY(video_no) REFERENCES video(video_no) ON DELETE CASCADE,
  FOREIGN KEY(category_name) REFERENCES video_group(category_name) ON DELETE CASCADE
);


SELECT count(*)
FROM user
WHERE user_id = 'ssafy';

