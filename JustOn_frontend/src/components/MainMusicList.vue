<template>
  <div
    :class="isSpread ? 'toggle-width' : ''"
    class="content-wrap width-spread w-[500px] h-[750px] flex flex-col items-stretch"
  >
    <!-- title -->
    <div class="title flex items-end mb-4">
      <button
        @click="
          rotate = !rotate;
          toggleWidth();
        "
        class="width-btn flex"
      >
        <i
          :class="{ 'rotate-180': rotate }"
          class="text-3xl bi bi-arrow-right-circle mr-3"
        ></i>
      </button>
      <h2 class="font-[800] mr-2">Music</h2>
      <span class="mb-1 ]">한껏 에너지를 Up해줄 Music 큐!</span>
    </div>

    <!-- !toggle-width  -->
    <div class="toggle-width-part mt-auto">
      <!-- <span>
        {{ selectedMusic.title }}
      </span> -->
      <!-- 버튼들 -->
      <div
        class="btns flex flex-col justify-center items-center bg-[var(--juston-black)] rounded-[16px]"
      >
        <button class="rotate-90" @click="prevMusic">
          <i class="bi bi-skip-start-fill text-white"></i>
        </button>
        <button class="text-4xl mx-4" @click="togglePlayPause">
          <i
            class="text-white"
            :class="isPlaying ? 'bi bi-pause-fill' : 'bi bi-play-fill'"
          ></i>
        </button>
        <button class="rotate-90" @click="nextMusic">
          <i class="bi bi-skip-end-fill text-white"></i>
        </button>
      </div>
    </div>

    <!-- 플레이리스트 선택 -->
    <div
      class="playlist-select mb-4 flex justify-between h-[44px] items-center px-4 py-7 bg-[#f7f7f7] rounded-[16px]"
    >
      <!-- <p
        class="text-[14px] w-[160px] mr-4 bg-white px-5 py-1 rounded-[16px] flex-1"
      >
        <i class="bi bi-music-note-list mr-2"></i>{{ selectedMusicPlaylist }}
      </p> -->
      <select
        class="w-[100%] text-[14px] border-solid border-gray-200 border-2 rounded-[16px] px-4 py-1"
        @change="requestGetMusic"
        v-model="selectedMusicPlaylist"
      >
        <option disabled value="플레이리스트 선택">🎵 플레이리스트 선택</option>
        <option
          v-for="playlist in musicPlaylist"
          :value="playlist.playlistName"
          :key="playlist.playlistName"
        >
          {{ playlist.playlistName }}
        </option>
      </select>
    </div>
    <!-- Music Controller  -->
    <!-- 영상 박스, <div id="player"></div> 삭제 금지 -->
    <div id="player"></div>
    <!-- 뮤직 컨트롤러 -->
    <div class="music-controls py-4 px-4 mb-4">
      <div class="bg-white w-full py-1 rounded-[10px] mb-2">
        <div class="selected">
          <span class="">
            {{ selectedMusic.title }}
          </span>
        </div>
      </div>

      <div class="w-[95%]">
        <input
          class="music-range w-full h-[4px]"
          type="range"
          :value="currentTime"
          :max="duration"
          @input="changeTime"
          @mousedown="startUserInteraction"
          @mouseup="endUserInteraction"
        />
        <div class="flex justify-between text-xs">
          <span class="block text-white" v-if="currentTime">
            <span v-if="curHour"> {{ curHour }} :</span>
            <span v-if="curMin"> {{ curMin }} :</span>
            {{ curSec }}
          </span>
          <!-- <progress class="progressTag" :value="currentTime" :max="duration"></progress> -->
          <span class="block text-white" v-if="duration">
            <span v-if="totHour"> {{ totHour }} :</span>
            <span v-if="totMin">{{ totMin }} :</span>
            {{ totSec }}
          </span>
        </div>
      </div>
      <div class="control-bar w-full text-white text-center">
        <!-- 재생/멈춤, 화살표 btns  -->
        <div class="music-btns-wrap flex justify-center">
          <button @click="prevMusic">
            <i class="bi bi-skip-start-fill"></i>
          </button>
          <button class="text-4xl mx-4" @click="togglePlayPause">
            <i :class="isPlaying ? 'bi bi-pause-fill' : 'bi bi-play-fill'"></i>
          </button>
          <!-- <button class="text-4xl" @click="playVideo">
            <i class="bi bi-play-fill"></i>
          </button> -->
          <!-- <button @click="pauseVideo"><i class="bi bi-pause-fill"></i></button> -->
          <button @click="nextMusic">
            <i class="bi bi-skip-end-fill"></i>
          </button>
        </div>

        <!-- 볼륨 조절 -->
        <div class="w-full volume-adjust flex items-center justify-center">
          <button class="mr-2" @click="mute" v-if="!isMuted">
            <i class="bi bi-volume-up-fill"></i>
          </button>
          <button @click="unMute" v-if="isMuted">
            <i class="bi bi-volume-mute"></i>
          </button>
          <input
            class="volume-adjust-bar"
            type="range"
            min="0"
            max="100"
            v-model="volume"
            @input="changeVolume"
          />
        </div>
      </div>
    </div>

    <div
      class="list p-3 flex justify-between items-center relative border-solid border-gray-200 border-b rounded-[16px] hover:bg-[#f6f6f6] cursor-pointer"
      v-for="(music, index) in musicInPlaylist"
      @click="musicSelect(index)"
      :key="music.musicNo"
    >
      <span class="ellipsis max-width-[300px] inline-block"
        >💿 {{ music.title }}</span
      >
    </div>
  </div>
</template>

<!-- <script src="https://www.youtube.com/iframe_api"></script> -->
<script setup>
import { onMounted, onBeforeMount, ref, watch } from "vue";
import axios from "@/axios/index";
const musicPlaylist = ref([]);
const musicInPlaylist = ref([]);
const selectedMusic = ref({});
const selectedIndex = ref(0);
const isMuted = ref(false);
const currentTime = ref(0);
const duration = ref(0);
const selectedMusicPlaylist = ref("플레이리스트 선택");

const curHour = ref();
const curMin = ref();
const curSec = ref();
const totHour = ref();
const totMin = ref();
const totSec = ref();

const isSpread = ref(false);
const rotate = ref(false);

const toggleWidth = () => {
  console.log("togglewidth");
  isSpread.value = !isSpread.value;
};

const requestGetPlaylist = async () => {
  const response = await axios.get("api-music/playlist");
  // console.log(response);
  musicPlaylist.value = response.data;
  requestGetMusic(musicPlaylist.value[0]);
};

const requestGetMusic = async (event) => {
  const playlistName = ref("");
  if (!event.target) {
    playlistName.value = event.playlistName;
  } else playlistName.value = event.target.value;
  const response = await axios.get(`api-music/${playlistName.value}/music`);
  musicInPlaylist.value = response.data;
  selectedMusic.value = musicInPlaylist.value[0];
  selectedIndex.value = 0;

  console.log("load");
  console.log(YT.Player);
  loadVideo();
  player.value.stopVideo();
};

const player = ref();
const volume = ref(100);
const isApiLoaded = ref(false);

const getTime = () => {
  if (!isUserInteracting.value) {
    currentTime.value = player.value.getCurrentTime();
    curHour.value = undefined;
    curMin.value = undefined;
    curSec.value = undefined;
    if (currentTime.value >= 60 * 60) {
      curHour.value = Math.floor(currentTime.value / (60 * 60));
      curMin.value = Math.floor(
        (currentTime.value - curHour.value * 60 * 60) / 60
      );
      curSec.value = String(
        Math.floor(
          currentTime.value - curHour.value * 60 * 60 - curMin.value * 60
        )
      ).padStart(2, "0");
      curMin.value = String(curMin.value).padStart(2, "0");
      if (totHour.value) {
        if (totHour.value >= 10) {
          curHour.value = String(curHour.value).padStart(2, "0");
        }
      }
    } else if (currentTime.value >= 60) {
      if (totHour.value) {
        if (totHour.value >= 10) {
          curHour.value = "00";
        } else curHour.value = "0";
      }
      curMin.value = Math.floor(currentTime.value / 60);
      curSec.value = String(
        Math.floor(currentTime.value - curMin.value * 60)
      ).padStart(2, "0");
      if (totMin.value) {
        if (totMin.value >= 10 || totMin.value.length == 2) {
          curMin.value = String(curMin.value).padStart(2, "0");
        }
      }
    } else {
      if (totHour.value) {
        if (totHour.value >= 10) {
          curHour.value = "00";
        } else curHour.value = "0";
      }
      if (totMin.value) {
        if (totMin.value >= 10 || totMin.value.length == 2) {
          curMin.value = "00";
        }
      } else curMin.value = "0";
      curSec.value = String(Math.floor(currentTime.value)).padStart(2, "0");
    }
    if (duration.value > 0) {
      if (Math.floor(currentTime.value) >= Math.floor(duration.value)) {
        nextMusic();
      }
    }
  }
};

const isUserInteracting = ref(false); // 사용자 조작 플래그
// 사용자가 슬라이더를 조작할 때
const changeTime = (event) => {
  currentTime.value = event.target.value; // 조작한 값을 현재 시간에 반영
  curHour.value = undefined;
  curMin.value = undefined;
  curSec.value = undefined;
  if (currentTime.value >= 60 * 60) {
    curHour.value = Math.floor(currentTime.value / (60 * 60));
    curMin.value = Math.floor(
      (currentTime.value - curHour.value * 60 * 60) / 60
    );
    curSec.value = String(
      Math.floor(
        currentTime.value - curHour.value * 60 * 60 - curMin.value * 60
      )
    ).padStart(2, "0");
    curMin.value = String(curMin.value).padStart(2, "0");
    if (totHour.value) {
      if (totHour.value >= 10) {
        curHour.value = String(curHour.value).padStart(2, "0");
      }
    }
  } else if (currentTime.value >= 60) {
    if (totHour.value) {
      if (totHour.value >= 10) {
        curHour.value = "00";
      } else curHour.value = "0";
    }
    curMin.value = Math.floor(currentTime.value / 60);
    curSec.value = String(
      Math.floor(currentTime.value - curMin.value * 60)
    ).padStart(2, "0");
    if (totMin.value) {
      if (totMin.value >= 10 || totMin.value.length == 2) {
        curMin.value = String(curMin.value).padStart(2, "0");
      }
    }
  } else {
    if (totHour.value) {
      if (totHour.value >= 10) {
        curHour.value = "00";
      } else curHour.value = "0";
    }
    if (totMin.value) {
      if (totMin.value >= 10 || totMin.value.length == 2) {
        curMin.value = "00";
      }
    } else curMin.value = "0";
    curSec.value = String(Math.floor(currentTime.value)).padStart(2, "0");
  }
};

// 조작 시작
const startUserInteraction = () => {
  isUserInteracting.value = true;
};

// 조작 종료
const endUserInteraction = () => {
  player.value.seekTo(currentTime.value);
  player.value.playVideo();
  isUserInteracting.value = false;
};

const timer = ref();
const isPlaying = ref(false);
const playVideo = () => {
  player.value.playVideo();
  clearInterval(timer.value);
  setTimeout(() => {
    totHour.value = undefined;
    totMin.value = undefined;
    totSec.value = undefined;
    duration.value = player.value.getDuration();
    if (duration.value >= 60 * 60) {
      totHour.value = Math.floor(duration.value / (60 * 60));
      totMin.value = Math.floor(
        (duration.value - totHour.value * 60 * 60) / 60
      );
      totSec.value = String(
        Math.floor(duration.value - totHour.value * 60 * 60 - totMin.value * 60)
      ).padStart(2, "0");
      totMin.value = String(totMin.value).padStart(2, "0");
    } else if (duration.value >= 60) {
      totMin.value = Math.floor(duration.value / 60);
      totSec.value = String(
        Math.floor(duration.value - totMin.value * 60)
      ).padStart(2, "0");
    } else {
      totSec.value = String(Math.floor(duration.value)).padStart(2, "0");
    }
    // console.log(duration.value, totHour.value, totMin.value, totSec.value);
  }, 1000);
  timer.value = setInterval(getTime, 1000);
};
const pauseVideo = () => {
  player.value.pauseVideo();
};
const togglePlayPause = () => {
  if (isPlaying.value) {
    pauseVideo();
  } else {
    playVideo();
  }
  isPlaying.value = !isPlaying.value; // 상태 토글
};
const changeVolume = () => {
  player.value.setVolume(volume.value);
};
const mute = () => {
  player.value.mute();
  isMuted.value = true;
};
const unMute = () => {
  player.value.unMute();
  isMuted.value = false;
};

const loadVideo = () => {
  player.value.loadVideoById({
    videoId: selectedMusic.value.musicId,
    startSeconds: 0,
    endSeconds: 0,
    suggestedQuality: "large",
  });
};

const musicSelect = (index) => {
  selectedMusic.value = musicInPlaylist.value[index];
  selectedIndex.value = index;
  loadVideo();
  playVideo();
};

const prevMusic = () => {
  if (selectedIndex.value == 0) {
    musicSelect(musicInPlaylist.value.length - 1);
  } else musicSelect(selectedIndex.value - 1);
};

const nextMusic = () => {
  if (selectedIndex.value == musicInPlaylist.value.length - 1) {
    musicSelect(0);
  } else musicSelect(selectedIndex.value + 1);
};

const loadYouTubeAPI = async () => {
  return new Promise((resolve) => {
    if (typeof YT === "undefined" || typeof YT.Player === "undefined") {
      const script = document.createElement("script");
      script.src = "https://www.youtube.com/iframe_api";
      script.async = true;
      script.defer = true;
      document.body.appendChild(script);
      console.log("api");
      // API 로드 후 초기화 함수 연결

      window.onYouTubeIframeAPIReady = async () => {
        isApiLoaded.value = true;
        resolve();
      };
    } else {
      isApiLoaded.value = true;
      resolve();
      return;
    }
  });
};

const initializePlayer = () => {
  return new Promise((resolve) => {
    console.log("before-init");
    console.log(isApiLoaded.value);
    if (isApiLoaded.value) {
      player.value = new YT.Player("player", {
        height: "0",
        width: "0",
        videoId: "",
        events: {
          onReady: (event) => {
            console.log("init");
            event.target.setVolume(volume.value);
            resolve();
          },
        },
      });
    }
  });
};

watch(
  () => isApiLoaded.value,
  () => {
    console.log("YT load");
    console.log("YT : ", YT);
    console.log("player : ", YT.Player);
  }
);
onBeforeMount(() => {
  loadYouTubeAPI()
    .then(() => initializePlayer())
    .then(() => requestGetPlaylist())
    .catch((error) => {
      console.log(error);
    });
});
</script>

<style scoped>
span {
  max-width: 300px;
}

.selected {
  width: 100%;
  /* max-width: 200px; */
  overflow: hidden;
  /* position: relative; */

  > span {
    max-width: 200px;
    display: inline-block;
    white-space: nowrap;
    animation: flowText 8s linear infinite;
  }
}

/* .selected::after {
    content: "";
    position: absolute;
    right: 0;
    height: 100%;
    pointer-events: none;
    background-image: linear-gradient(to left, transparent, white);
} */

@keyframes flowText {
  0% {
    transform: translateX(calc(100%));
  }

  100% {
    transform: translateX(calc(-200% - 400px));
  }
}

.music-controls {
  background-color: var(--juston-black);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 16px;

  > .control-bar {
    > button,
    input {
      margin: 0 0.2vw;
    }
  }
}
.music-range {
  -webkit-appearance: none;
  width: 100%;
  height: 4px;
  background: linear-gradient(
    to right,
    #ff6600 var(--progress, 0%),
    #e0e0e0 0%
  );
  border-radius: 5px;
  outline: none;
}

.music-range::-webkit-slider-thumb {
  -webkit-appearance: none;
  width: 10px;
  height: 10px;
  background: var(--juston-gradient-3);
  border-radius: 50%;
  cursor: pointer;
}

.music-range::-moz-range-thumb,
.music-range::-ms-thumb {
  width: 14px;
  height: 14px;
  background: #ff6600;
  border-radius: 50%;
  cursor: pointer;
}

.volume-adjust-bar::-webkit-slider-thumb {
  width: 4px;
  height: 4px;
}

.volume-adjust-bar {
  -webkit-appearance: none;
  width: 20%;
  height: 2px;
  background: linear-gradient(
    to right,
    #ff6600 var(--progress, 0%),
    #e0e0e0 0%
  );
  border-radius: 5px;
  outline: none;
}

.volume-adjust-bar::-webkit-slider-thumb {
  -webkit-appearance: none;
  width: 10px;
  height: 10px;
  background: #fff;
  border-radius: 50%;
  cursor: pointer;
}

.volume-adjust-bar::-moz-range-thumb,
.volume-adjust-bar::-ms-thumb {
  width: 14px;
  height: 14px;
  background: #ff6600;
  border-radius: 50%;
  cursor: pointer;
}

/* **************************
 너비 변화에 따른 내부 요소 변화
 *************************** */
.toggle-width {
  width: 70px;
  display: flex;
  justify-content: center;
  align-items: center;

  .title {
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 40px;
  }
  .title i {
    margin-right: 0;
  }
  .title h2,
  .title span {
    transform: rotate(90deg);
  }

  .title span {
    padding-top: 10px;
    margin-top: 140px;
    text-align: start;
    width: 300px;
  }

  select,
  .playlist-select,
  .music-controls,
  .list {
    display: none;
  }

  .toggle-width-part {
    display: block;
    width: 40px;
    text-align: center;
    overflow: hidden;

    span {
      transform: rotate(90deg);
    }
    .btns {
      padding: 40px 0;
    }
  }
}

/* 기본 스타일 */
.content-wrap {
  /* width: 500px; */
  height: 100%;
  transition: all 0.8s ease; /* 부드러운 애니메이션 */
  transform-origin: top right; /* 오른쪽 상단 기준 */
}
.toggle-width-part {
  display: none;
}
</style>
