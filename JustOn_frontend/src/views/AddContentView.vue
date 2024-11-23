<template>
  <div class="content-wrap">
    <!-- 콘텐츠 제목 -->
    <div
      class="con-title flex justify-between pb-2 mb-6 border-solid border-b-[1px] border-gray-300"
    >
      <div class="flex">
        <h2><i class="bi bi-arrow-right-circle-fill mr-3"></i>컨텐츠 등록</h2>
      </div>
    </div>
    <div class="content-scroll flex flex-col items-center">
      <!-- 영상 Section -->
      <div class="mb-20 max-w-screen-xl w-[100%]">
        <h3 class="mb-4">영상 추가/수정</h3>

        <div class="flex gap-[30px]">
          <!-- LEFT -->
          <div class="add-info flex flex-col gap-[20px]">
            <div class="addVideo flex flex-col items-start">
              <label class="label font-bold text-lg mb-2">URL 입력</label>
              <input
                placeholder="추가하실 운동 영상의 링크를 넣어주세요."
                class="w-[100%] input-style-h52"
                type="text"
                v-model.trim="URL"
              />
            </div>

            <div>
              <label class="label mr-4">영상 운동 강도</label>
              <select
                class="border-solid border-[1px] border-gray-300 p-3 rounded-[16px]"
                v-model="weight"
              >
                <option value="none">선택</option>
                <option value="300">강</option>
                <option value="200">중</option>
                <option value="100">약</option>
              </select>
            </div>

            <div class="">
              <label class="label block mb-2">운동 부위/종류 선택</label>
              <div
                class="border-solid border-[1px] border-gray-300 p-3 rounded-[16px]"
              >
                <span class="m-0" v-for="ex in exData">
                  <input
                    type="checkbox"
                    class="input_cbx"
                    :id="ex.part"
                    v-model="ex.selected"
                    style="display: none"
                  />
                  <label class="cbx" :for="ex.part">{{ ex.title }}</label>
                </span>
              </div>
            </div>
            <button
              class="h-[80px] btn-m-black"
              type="button"
              @click="insertVideo"
              v-if="!modifyState"
            >
              추가하기
            </button>
            <div class="flex justify-end" v-if="modifyState">
              <button
                class="flex-1 btn-m-black mr-3"
                type="button"
                @click="modifyVideo"
              >
                수정하기
              </button>
              <button
                class="flex-1 btn-m-black"
                type="button"
                @click="cancelModify"
              >
                취소
              </button>
            </div>
          </div>

          <!-- RIGHT -->
          <div class="playList">
            <VideoList
              @deleteVideo="deleteVideo"
              @modifyVideo="modifyVideoView"
              :count="count"
            />
          </div>
        </div>

        <!-- <img :src="youtube.thumb"/> -->
      </div>
      <!-- 음악 Section -->
      <div class="mb-20 max-w-screen-xl w-[100%]">
        <h3 class="mb-4">음악 추가/수정</h3>

        <div class="flex flex-col gap-[20px] w-[100%]">
          <!-- TOP -->
          <div class="flex gap-[14px]">
            <div class="addMusic flex items-center">
              <label class="label font-bold text-lg mb-2 whitespace-nowrap mr-4"
                >URL 입력</label
              >
              <input
                class="w-[100%] w-[500px] input-style-h52"
                type="text"
                v-model.trim="musicURL"
                placeholder="추가하실 음악 영상의 링크를 넣어주세요."
              />
            </div>

            <div>
              <button
                class="h-[80px] btn-m-black"
                type="button"
                @click="insertMusic"
                v-if="!musicModifyState"
              >
                추가하기
              </button>
              <div class="flex justify-end" v-if="musicModifyState">
                <button
                  class="flex-1 btn-m-black mr-3 p-3"
                  type="button"
                  @click="modifyMusic"
                >
                  수정하기
                </button>
                <button
                  class="flex-1 btn-m-black"
                  type="button"
                  @click="cancelMusicModify"
                >
                  취소
                </button>
              </div>
            </div>
          </div>

          <!-- BOTTOM -->
          <div class="misicPlayList">
            <MusicList
              @deleteVideo="deleteMusic"
              @modifyMusic="modifyMusicView"
              :count="musicCount"
            />
          </div>
        </div>

        <!-- <img :src="youtube.thumb"/> -->
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import oriAxios from "axios";
import axios from "@/axios/index";
import VideoList from "@/components/VideoList.vue";
import MusicList from "@/components/MusicList.vue";

const modifyState = ref(false);
const musicModifyState = ref(false); // 음악 상태

const URL = ref("");
const musicURL = ref(""); // 음악 url 담기

const youtube = ref({
  videoId: null,
  src: null,
  // author : null,
  thumb: null,
  title: null,
});

// 음악 youtube data 객체
const youtubeMusic = ref({
  musicId: null,
  src: null,
  // author : null,
  title: null,
  // thumb: null,
});

// =======================
// Video - S
// =======================
const weight = ref("none");
const exData = ref([
  {
    part: "shoulder",
    title: "어깨",
    selected: false,
    selCnt: 0,
  },
  {
    part: "leg",
    title: "다리",
    selected: false,
    selCnt: 0,
  },
  {
    part: "abs",
    title: "복부",
    selected: false,
    selCnt: 0,
  },
  {
    part: "chest",
    title: "가슴",
    selected: false,
    selCnt: 0,
  },
  {
    part: "back",
    title: "등",
    selected: false,
    selCnt: 0,
  },
  {
    part: "arm",
    title: "팔",
    selected: false,
    selCnt: 0,
  },
  {
    part: "stretching",
    title: "스트레칭",
    selected: false,
    selCnt: 0,
  },
  {
    part: "cardio",
    title: "유산소",
    selected: false,
    selCnt: 0,
  },
]);
const count = ref(0);

// 영상 추가 --------------------------------------------------------------------
const insertVideo = async () => {
  if (!URL.value) {
    alert("영상 URL을 입력해주세요.");
    return;
  }
  if (weight.value === "none") {
    alert("영상의 운동 강도를 선택해주세요.");
    return;
  }

  if (
    exData.value.findIndex((ex) => {
      return ex.selected;
    }) === -1
  ) {
    alert("영상의 운동 부위/종류를 선택해주세요.");
    return;
  }

  const noEmbed = "https://noembed.com/embed?url=";
  const fullURL = noEmbed + URL.value;
  // console.log(fullURL);
  const { data } = await oriAxios.get(fullURL);
  // console.dir(data);
  const { url, thumbnail_url, title } = data;
  console.log(url);
  const repUrl = url.replace("https://youtu.be/", "");
  let id = repUrl.replace("https://www.youtube.com/watch?v=", "");
  if (id.indexOf("embed/") !== -1) {
    id = id.split("embed/")[1];
  }
  id = id.split("?")[0];
  id = id.split("&")[0];
  console.log(id);
  youtube.value.videoId = id;
  youtube.value.src = "https://www.youtube.com/embed/" + id;
  // youtube.value.author = author_name;
  youtube.value.thumb = thumbnail_url;
  youtube.value.title = title;
  console.log(youtube.value);

  for (const ex of exData.value) {
    ex.selCnt = ex.selected ? 1 : 0;
    ex.exWeight = weight.value;
  }

  try {
    await axios.post("api-video", {
      video: youtube.value,
      videoExList: exData.value,
    });
    count.value++;
    URL.value = "";
    weight.value = "none";
    for (const ex of exData.value) {
      ex.selected = false;
    }
  } catch (error) {
    console.dir(error);
    const { response } = error;
    alert(response.data);
    URL.value = "";
    weight.value = "none";
    for (const ex of exData.value) {
      ex.selected = false;
    }
  }
};

// 영상 삭제 --------------------------------------------------------------------
const deleteVideo = () => {
  count.value--;
  cancelModify();
};
const videoNo = ref(-1);

const modifyVideoView = async (no) => {
  const { data } = await axios.get("api-video/" + no);
  console.dir(data);
  URL.value = data.video.src;

  modifyState.value = true;
  for (const ex of exData.value) {
    ex.selected = false;
  }

  for (const index in data.videoEx) {
    const exDataIndex = exData.value.findIndex(
      (item) => item.part === data.videoEx[index].part
    );

    if (data.videoEx[index].selCnt !== 0) {
      // console.log(data.videoEx[index].part);
      // console.log(exData.value.indexOf(data.videoEx[index].part));
      exData.value[exDataIndex].selCnt = 1;
      exData.value[exDataIndex].selected = true;
    }
  }
  // for (const index in data.videoEx) {
  //   if (data.videoEx[index].selCnt !== 0) {
  //     console.log(data.videoEx[index].part);
  //     console.log(exData.value.indexOf(data.videoEx[index].part));
  //     exData.value[exData.value.indexOf(data.videoEx[index].part)].selCnt = 1; // ***
  //     exData.value[
  //       exData.value.indexOf(data.videoEx[index].part)
  //     ].selected = true;
  //   }
  // }
  weight.value = data.videoEx[0].exWeight;
  videoNo.value = no;
};

const modifyVideo = async () => {
  if (!URL.value) {
    alert("영상 URL을 입력해주세요.");
    return;
  }
  if (weight.value === "none") {
    alert("영상의 운동 강도를 선택해주세요.");
    return;
  }

  if (
    exData.value.findIndex((ex) => {
      return ex.selected;
    }) === -1
  ) {
    alert("영상의 운동 부위/종류를 선택해주세요.");
    return;
  }

  const noEmbed = "https://noembed.com/embed?url=";
  const fullURL = noEmbed + URL.value;
  // console.log(fullURL);
  const { data } = await oriAxios.get(fullURL);
  // console.dir(data);
  const { url, thumbnail_url, title } = data;
  console.log(url);
  const repUrl = url.replace("https://youtu.be/", "");
  let id = repUrl.replace("https://www.youtube.com/watch?v=", "");
  if (id.indexOf("embed/") !== -1) {
    id = id.split("embed/")[1];
  }
  id = id.split("?")[0];
  id = id.split("&")[0];
  console.log(id);
  youtube.value.videoId = id;
  youtube.value.src = "https://www.youtube.com/embed/" + id;
  // youtube.value.author = author_name;
  youtube.value.thumb = thumbnail_url;
  youtube.value.title = title;
  console.log(youtube.value);

  for (const ex of exData.value) {
    ex.selCnt = ex.selected ? 1 : 0;
    ex.exWeight = weight.value;
  }

  try {
    await axios.put("api-video/" + videoNo.value, {
      video: youtube.value,
      videoExList: exData.value,
    });
    alert("영상 수정을 완료했습니다.");
    cancelModify();
    weight.value = "none";
    count.value++;
  } catch (error) {
    const { response } = error;
    alert(response.data);
  }
};

const cancelModify = () => {
  modifyState.value = false;
  URL.value = "";
  for (const ex of exData.value) {
    ex.selected = false;
  }
  weight.value = "none";
};

// =======================
// Music - S
// =======================
//  INSERT Music - S

const musicCount = ref(0);

// 음악 추가 --------------------------------------------------------------------

const insertMusic = async () => {
  if (!musicURL.value) {
    alert("음악 영상 URL을 입력해주세요.");
    return;
  }

  const noEmbed = "https://noembed.com/embed?url=";
  const fullURL = noEmbed + musicURL.value;
  // console.log(fullURL);
  const { data } = await oriAxios.get(fullURL);
  // console.dir(data);
  const { url, thumbnail_url, title } = data;
  // console.log(url);
  const repUrl = url.replace("https://youtu.be/", "");
  let id = repUrl.replace("https://www.youtube.com/watch?v=", "");
  if (id.indexOf("embed/") !== -1) {
    id = id.split("embed/")[1];
  }
  id = id.split("?")[0];
  id = id.split("&")[0];
  // console.log(id);
  youtubeMusic.value.musicId = id;
  youtubeMusic.value.src = "https://www.youtube.com/embed/" + id;
  // youtube.value.author = author_name;
  // youtubeMusic.value.thumb = thumbnail_url;
  youtubeMusic.value.title = title;
  // console.log(youtube.value);

  try {
    await axios.post("api-music", youtubeMusic.value);
    musicCount.value++;
    musicURL.value = "";
  } catch (error) {
    // console.dir(error);
    const { response } = error;
    alert(response.data);
    musicURL.value = "";
  }
};

// 음악 삭제 --------------------------------------------------------------------
const deleteMusic = () => {
  musicCount.value--;
  cancelMusicModify();
};
const musicNo = ref(-1);

// 음악 영상 수정 데이터 전송--------------------------------------------------------------
const modifyMusic = async () => {
  if (!musicURL.value) {
    alert("음악 영상 URL을 입력해주세요.");
    return;
  }

  const noEmbed = "https://noembed.com/embed?url=";
  const fullURL = noEmbed + musicURL.value;
  // console.log(fullURL);
  const { data } = await oriAxios.get(fullURL);
  // console.dir(data);
  const { url, thumbnail_url, title } = data;
  // console.log(url);
  const repUrl = url.replace("https://youtu.be/", "");
  let id = repUrl.replace("https://www.youtube.com/watch?v=", "");
  if (id.indexOf("embed/") !== -1) {
    id = id.split("embed/")[1];
  }
  id = id.split("?")[0];
  id = id.split("&")[0];
  // console.log(id);
  youtubeMusic.value.musicId = id;
  youtubeMusic.value.src = "https://www.youtube.com/embed/" + id;
  // youtube.value.author = author_name;
  // youtube.value.thumb = thumbnail_url;
  youtubeMusic.value.title = title;
  // console.log(youtube.value);

  try {
    await axios.put("api-music/" + musicNo.value, youtubeMusic.value);
    alert("음악 영상 수정을 완료했습니다.");
    cancelMusicModify();
    musicCount.value++;
  } catch (error) {
    const { response } = error;
    alert(response.data);
  }
};

// 음악 영상 수정 시 기존 데이터 세팅--------------------------------------------------------------
const modifyMusicView = async (no) => {
  const { data } = await axios.get("api-music/" + no);
  console.dir(data);
  musicURL.value = data.src;
  musicModifyState.value = true;

  musicNo.value = no;
};

// 음악 영상 Form 초기화  --------------------------------------------------------------
const cancelMusicModify = () => {
  musicModifyState.value = false;
  musicURL.value = "";
};
</script>

<style scoped>
.content-box {
  grid-template-columns: 1fr;
  grid-template-rows: repeat(2, auto);
  row-gap: 24px;
}
.addVideo {
  display: flex;
  > div {
    margin-left: 10px;
    /* background-color: #ccc; */
    /* border: 0; */
    /* border-radius: 20px; */
    /* height: 30px; */
    /* width: 50vh; */
    display: flex;
    > input {
      /* background: none; */
      /* border: 0; */
      /* width: 80%; */
    }
  }
}
.addMusic {
  display: flex;
  > div {
    margin-left: 10px;
    /* background-color: #ccc; */
    /* border: 0; */
    /* border-radius: 20px; */
    /* height: 30px; */
    /* width: 50vh; */
    display: flex;
    > input {
      /* background: none; */
      /* border: 0; */
      /* width: 80%; */
    }
  }
}
button {
  /* margin-left: 10vw; */
  /* margin-top: 20px; */
  border-radius: 20px;
  border: 1px solid #ccc;
  font-size: 0.9rem;
  font-weight: bold;
  width: 100px;
  /* height: 35px; */
  color: white;
  /* background-color: black; */
  cursor: pointer;
}
.playList {
  flex: 2;
  /* min-width: 800px; */
  border: 1px solid #ccc;
  border-radius: 20px;
  display: flex;
  /* overflow-y: scroll; */
  height: 410px;
  padding: 10px 20px;
}
.add-info {
  flex: 1;
}
.misicPlayList {
  width: 100%;
  border: 1px solid #ccc;
  border-radius: 20px;
  display: flex;
  /* overflow-y: scroll; */
  height: 220px;
  padding: 10px 20px;
}
.cbx {
  /* margin-right: 1.2vw; */
  border: 2px solid var(--juston-black);
  display: inline-block;
  padding: 4px 16px;
  margin: 4px;
  text-align: center;
  border-radius: 50px;
  font-weight: bold;
  cursor: pointer;
  transition: 80ms;
}
.input_cbx:checked + .cbx {
  background-color: var(--juston-black);
  color: white;
  transition: 80ms;
}
.label {
  /* margin-right: 0.8vw; */
}
select {
  margin-right: 3vw;
}
</style>
