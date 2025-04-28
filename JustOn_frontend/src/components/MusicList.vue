<template>
  <div class="container overflow-hidden">
    <div class="list-wrap my-scrollbar" v-if="musicList !== ''">
      <!-- <div
        @click="modifyMusic(music.musicNo)"
        class="list p-3 flex justify-between items-center relative border-solid border-gray-200 border-b rounded-[16px] hover:bg-[#f6f6f6] cursor-pointer"
        v-for="music in musicList"
      > -->
      <div
        class="list p-3 flex justify-between items-center relative border-solid border-gray-200 border-b rounded-[16px] hover:bg-[#f6f6f6]"
        v-for="music in musicList"
      >
        <span class="ellipsis max-width-[600px] inline-block"
          >💿 {{ music.title }}</span
        >

        <div class="flex items-center">
          <button class="add-btn mr-3" @click="selectMusic(music)">
            PlayList설정
          </button>
          <!-- <div>
            <i
              @click="openMenu(music)"
              class="three-dot cursor-pointer bi bi-three-dots-vertical"
            ></i>
            <div
              class="menu-box absolute right-[50px] top-0 z-50"
              v-if="music.menuView"
              v-click-outside="closeMenu"
            >
              <div @click="modifyMusic(music.musicNo)">
                <p>수정</p>
              </div>
              <hr />
              <div @click="deleteMusic(music.musicNo)">
                <p>삭제</p>
              </div>
            </div>
          </div> -->
          <i
            class="bi bi-x-circle cursor-pointer fs-3"
            @click="deleteMusic(music.musicNo)"
          ></i>
        </div>
      </div>
      <!-- 재생목록 선택 -->
    </div>

    <div class="" v-for="music in musicList">
      <MusicPlaylist
        v-if="music.selected"
        @deletePlaylist="playlistAddCount--"
        :count="playlistAddCount"
        :musicNo="music.musicNo"
        @open-add-playlist-view="doOpenAddPlaylist"
        @close-playlist-view="doClosePlayList(music)"
      />
    </div>

    <div class="text-center text-lg my-6" v-if="musicList === ''">
      등록된 음악이 없습니다.
    </div>
  </div>

  <MusicAddPlaylist
    @success-add="playlistAddCount++"
    @close-add-playlist-view="doCloseAddPlaylist"
    v-if="addListView"
  />
</template>

<script setup>
import { ref, watch } from "vue";
import axios from "@/axios/index";
import MusicPlaylist from "./MusicPlaylist.vue";
import MusicAddPlaylist from "./MusicAddPlaylist.vue";
import Swal from "sweetalert2";
const addListView = ref(false);
const listView = ref(false);
const playlistAddCount = ref(0);

const props = defineProps(["count"]);
const emit = defineEmits(["deleteMusic", "modifyMusic"]);

const doOpenAddPlaylist = () => {
  addListView.value = true;
};

const doCloseAddPlaylist = () => {
  addListView.value = false;
};

const doClosePlayList = (music) => {
  music.selected = false;
};

const musicList = ref([]); //http://192.168.210.75:8081/
const requestMusicList = async () => {
  const { data } = await axios.get("api-music");
  // console.dir(data);
  musicList.value = data;
  for (const music of musicList.value) {
    music.selected = false;
    music.menuView = false;
  }
};

watch(
  () => props.count,
  () => {
    requestMusicList();
  }
);
requestMusicList();

const selectMusic = (selectedMusic) => {
  if (selectedMusic.selected) {
    selectedMusic.selected = false;
  } else {
    for (const music of musicList.value) {
      music.selected = false;
    }
    selectedMusic.selected = true;
  }
};
const openMenu = (selectedMusic) => {
  if (selectedMusic.menuView) {
    selectedMusic.menuView = false;
  } else {
    for (const music of musicList.value) {
      music.menuView = false;
    }
    selectedMusic.menuView = true;
  }
};
const closeMenu = () => {
  for (const music of musicList.value) {
    music.menuView = false;
  }
};
const deleteMusic = async (musicNo) => {
  try {
    if (
      confirm(
        "해당 음악 삭제 시 재생목록 내에서도 지워집니다.\n삭제하시겠습니까?"
      )
    ) {
      await axios.delete("api-music/" + musicNo);
      emit("deleteMusic");
    }
  } catch (error) {
    Swal.fire({
      icon : "error",
      text : "해당 음악 삭제에 실패하였습니다."
    })
  }
};

const modifyMusic = (musicNo) => {
  emit("modifyMusic", musicNo);
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
