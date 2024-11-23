<template>
  <div class="content-wrap h-[100%]">
    <!-- 상단 제목, 재생목록 -->
    <div class="flex justify-between pb-5">
      <!-- title -->
      <div class="flex flex-col items-end">
        <h2 class="font-[800] mr-2">Just Start!</h2>
        <span class="mb-1">바로 시작하는 오늘의 운동</span>
      </div>
      <!-- 재생 목록 선택 select -->
      <div
        class="h-[44px] flex justify-center items-center px-4 py-7 bg-[#f7f7f7] rounded-[16px]"
      >
        <p class="text-[14px] w-[260px] mr-4 bg-white px-5 py-1 rounded-[16px]">
          <i class="bi bi-collection-play mr-2"></i>
          {{ selectedVideoPlaylist }}
        </p>
        <select
          class="w-[230px] text-[14px] border-solid border-gray-200 border-2 rounded-[16px] px-4 py-1"
          @change="requestGetVideo"
          v-model="selectedVideoPlaylist"
        >
          <option disabled value="재생 목록 선택을 선택해주세요.">
            재생 목록 선택
          </option>
          <option
            v-for="playlist in videoPlaylist"
            :value="playlist.categoryName"
          >
            {{ playlist.categoryName }}
          </option>
        </select>
      </div>
    </div>

    <div class="video-box flex flex-1 w-[100%] max-h-[calc(100vh-248px)]">
      <div class="flex w-[100%] gap-8">
        <!-- 영상 박스 -->
        <div class="main-video flex-1">
          <MainVideoListDetail :video="selectedVideo" />
        </div>
        <!-- 재생목록 영상 리스트 -->
        <div
          class="video-list w-[200px] h-[100%] border-2 border-solid border-gray-200 mb-4 p-4 rounded-[16px] overflow-hidden"
        >
          <p class="font-bold text-xl pb-4 mb-4">재생 목록</p>
          <!-- ★ -->
          <div class="main-video-list-wrap h-[100%]">
            <div
              class="main-video-list con-title flex flex-col justify-start max-h-[100%] pb-[30px] content-scroll my-scrollbar border-solid"
            >
              <div class="" v-show="videoInPlaylist.length === 0">
                <p class="indent-2">재생목록에 추가된 영상이 없습니다.</p>
              </div>
              <div
                class="mb-6"
                v-for="video in videoInPlaylist"
                :key="video.videoId"
              >
                <img
                  class="rounded-[16px]"
                  :src="video.thumb"
                  @click="selectedVideo = video"
                />
              </div>
            </div>
          </div>
        </div>
        <!--  -->
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "@/axios/index";
import MainVideoListDetail from "./MainVideoListDetail.vue";
const videoPlaylist = ref([]);
const videoInPlaylist = ref([]);
const selectedVideo = ref({});
const selectedVideoPlaylist = ref("재생 목록 선택을 선택해주세요.");
// const selectedPlaylist = ref("");
const requestGetPlaylist = async () => {
  const response = await axios.get("api-video/playlist");
  // console.log(response);
  videoPlaylist.value = response.data;
  requestGetVideo(videoPlaylist.value[0]);
};
requestGetPlaylist();

const requestGetVideo = async (event) => {
  const categoryName = ref("");
  if (!event.target) {
    categoryName.value = event.categoryName;
  } else categoryName.value = event.target.value;
  const response = await axios.get(`api-video/${categoryName.value}/videos`);
  videoInPlaylist.value = response.data;
  selectedVideo.value = videoInPlaylist.value[0];
};
</script>

<style scoped>
.content-wrap {
  display: flex;
  flex-direction: column;
}
h2 {
  align-self: flex-start;
}
select {
  /* align-self: flex-end; */
  border: 1px solid #ccc;
  border-radius: 16px;
  padding: 4px 12px;
  font-weight: 700;
}

.con-title {
  /* flex-grow: 1; */
  /* flex-direction: column; */
}
img {
  cursor: pointer;
  /* margin: 1vh 0; */
}
.detail {
  /* flex-grow: 7; */
}
</style>
