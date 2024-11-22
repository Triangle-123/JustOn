<template>
  <div class="container overflow-hidden">
    <div class="list-wrap my-scrollbar" v-if="videoList !== ''">
      <div
        @click="modifyVideo(video.videoNo)"
        class="list p-3 flex justify-between min-width-[400px] items-center relative border-solid border-gray-200 border-b rounded-[16px] hover:bg-[#f6f6f6] cursor-pointer"
        v-for="video in videoList"
      >
        <span class="ellipsis inline-block max-width-[300px]">{{
          video.title
        }}</span>

        <div class="flex items-center">
          <button class="add-btn mr-3" @click="selectVideo(video)">
            재생목록설정
          </button>
          <div>
            <i
              @click="openMenu(video)"
              class="three-dot cursor-pointer bi bi-three-dots-vertical"
            ></i>
            <div
              class="menu-box absolute right-[50px] top-0 z-50"
              v-if="video.menuView"
              v-click-outside="closeMenu"
            >
              <div @click="modifyVideo(video.videoNo)">
                <p>수정</p>
              </div>
              <hr />
              <div @click="deleteVideo(video.videoNo)">
                <p>삭제</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 재생목록 선택 -->
    <div class="" v-for="video in videoList">
      <VideoPlaylist
        v-if="video.selected"
        @deletePlaylist="playlistAddCount--"
        :count="playlistAddCount"
        :videoNo="video.videoNo"
        @open-add-playlist-view="doOpenAddPlaylist"
        @close-playlist-view="doClosePlayList(video)"
      />
    </div>

    <div class="text-center text-lg my-6" v-if="videoList === ''">
      등록된 영상이 없습니다.
    </div>
  </div>

  <VideoAddPlaylist
    class=""
    @success-add="playlistAddCount++"
    @close-add-playlist-view="doCloseAddPlaylist"
    v-if="addListView"
  />
</template>

<script setup>
import { ref, watch } from "vue";
import axios from "@/axios/index";
import VideoPlaylist from "./VideoPlaylist.vue";
import VideoAddPlaylist from "./VideoAddPlaylist.vue";

const addListView = ref(false);
const listView = ref(false);
const playlistAddCount = ref(0);

const props = defineProps(["count"]);
const emit = defineEmits(["deleteVideo", "modifyVideo"]);

const doOpenAddPlaylist = () => {
  addListView.value = true;
};

const doCloseAddPlaylist = () => {
  addListView.value = false;
};

const doClosePlayList = (video) => {
  video.selected = false;
};

const videoList = ref([]); //http://192.168.210.75:8081/
const requestVideoList = async () => {
  const { data } = await axios.get("api-video");
  // console.dir(data);
  videoList.value = data;
  for (const video of videoList.value) {
    video.selected = false;
    video.menuView = false;
  }
};

watch(
  () => props.count,
  () => {
    requestVideoList();
  }
);
requestVideoList();

const selectVideo = (selectedVideo) => {
  if (selectedVideo.selected) {
    selectedVideo.selected = false;
  } else {
    for (const video of videoList.value) {
      video.selected = false;
    }
    selectedVideo.selected = true;
  }
};
const openMenu = (selectedVideo) => {
  if (selectedVideo.menuView) {
    selectedVideo.menuView = false;
  } else {
    for (const video of videoList.value) {
      video.menuView = false;
    }
    selectedVideo.menuView = true;
  }
};
const closeMenu = () => {
  for (const video of videoList.value) {
    video.menuView = false;
  }
};
const deleteVideo = async (videoNo) => {
  try {
    if (
      confirm("영상 삭제 시 재생목록 내에서도 지워집니다.\n삭제하시겠습니까?")
    ) {
      await axios.delete("api-video/" + videoNo);
      emit("deleteVideo");
    }
  } catch (error) {
    alert("영상 삭제에 실패하였습니다.");
  }
};

const modifyVideo = (videoNo) => {
  emit("modifyVideo", videoNo);
};
</script>

<style scoped>
.container {
  /* display: flex; */
}

.list {
  position: relative;
}

.add-btn {
  cursor: pointer;
  border: 1px solid #ccc;
  border-radius: 16px;
  padding: 4px 12px;
  font-weight: 700;
  /* margin-right: 1vw; */
}

span {
  margin-right: 20px;
}

.menu {
  cursor: pointer;
}

.menu-box {
  display: inline-block;
  background-color: #fff;
  border: 1px solid #eee;
  border-radius: 8px;
  > hr {
    margin: auto;
  }
  > div {
    width: 5vw;
    height: 3vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    > p {
      font-weight: bold;
      margin: auto;
    }
  }
  :hover {
    background-color: #eee;
    transition: 0.2s;
  }
}
.three-dot {
  display: inline-block;
  text-align: center;
  font-size: 20px;
  width: 30px;
  height: 30px;
  background-color: #eee;
  border-radius: 30px;
}
.list-wrap {
  /* background-color: #eee; */
  overflow-y: scroll;
  height: 100%;
}
.content {
  height: 100%;
  overflow-y: scroll;
}
</style>
