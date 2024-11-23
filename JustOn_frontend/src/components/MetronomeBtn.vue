<template>
  <button
    class="metronome-btn bg-[var(--juston-black)] fixed right-10 bottom-10 w-[100px] h-[100px] rounded-[50%] text-white text-4xl firework-button"
    :class="{ 'met-active': isPlaying }" @click="toggleAudio">
    <i class="bi bi-stopwatch-fill"></i>
    <div class="timer" v-if="isPlaying">
      <div class="mask"></div>
    </div>
  </button>
  <!-- <div class="firework-particles" v-if="isPlaying"></div> -->
</template>

<script setup>
import { ref } from "vue";
const soundUrl = import.meta.env.VITE_SOUND_URL;
const audioFiles = [
  // { src: soundUrl + "/racestart.mp3", duration: 5, repeatCount: 1 },
  { src: soundUrl + "/metronome.mp3", duration: 1, repeatCount: 10 },
  { src: soundUrl + "/countdown-10.mp3", duration: 11.5, repeatCount: 1 },
  { src: soundUrl + "/wow.mp3", duration: 1, repeatCount: 1 },
  // { src: soundUrl + "/metronome.mp3", duration: 1, repeatCount: 1 },
  // { src: soundUrl + "/wow.mp3", duration: 1, repeatCount: 1 },
];
const audioElements = ref([]);
const isPlaying = ref(false);
const timeouts = ref([]);

function toggleAudio() {
  if (!isPlaying.value) {
    playAudioSequence();
  } else {
    stopAllAudio();
    isPlaying.value = false;
  }
}

function playAudioSequence() {
  console.log("start");
  isPlaying.value = true;
  audioElements.value = audioFiles.map((file) => {
    const audio = new Audio(file.src);
    audio.volume = 1;
    audio.preload = "auto"; // 미리 로드
    return audio;
  });
  // playNextAudio(0); // 첫 번째 오디오부터 재생 시작 ///////////////////////////

  audioElements.value.forEach((audio, index) => {
    const { duration, repeatCount } = audioFiles[index];

    let currentRepeat = 0;
    audio.addEventListener("ended", () => {
      currentRepeat++;
      if (currentRepeat < repeatCount) {
        audio.currentTime = 0;
        audio.play();
      } else {
        checkIfAllAudioFinished(index);
      }
    });

    const timeout = setTimeout(() => {
      audio.play();
    }, calculateDelay(index));

    timeouts.value.push(timeout);
  });
}
// /////////////////////////
function playNextAudio(index) {
  if (index >= audioElements.value.length) {
    // stopAllAudio(); // 모든 오디오 재생 종료
    return;
  }

  const audio = audioElements.value[index];
  const { repeatCount } = audioFiles[index];
  let currentRepeat = 0;

  // 오디오 종료 이벤트 처리
  audio.addEventListener("ended", () => {
    currentRepeat++;
    if (currentRepeat < repeatCount) {
      audio.currentTime = 0;
      audio.play();
    } else {
      playNextAudio(index + 1); // 다음 오디오 재생
    }
  });

  audio.play(); // 현재 오디오 재생 시작
}
// /////////////////////////////////

function calculateDelay(index) {
  let delay = 0;
  for (let i = 0; i < index; i++) {
    const { duration, repeatCount } = audioFiles[i];
    delay += duration * repeatCount * 1000;
  }
  return delay;
}

function checkIfAllAudioFinished(currentIndex) {
  // 마지막 오디오의 마지막 반복이 끝났는지 확인
  if (currentIndex === audioFiles.length - 1) {
    stopAllAudio();
    playAudioSequence();
    // setTimeout(playAudioSequence, 1000);
  }
}

function stopAllAudio() {
  console.log("stop");
  isPlaying.value = false;

  // 타임아웃 모두 제거
  timeouts.value.forEach(clearTimeout);
  timeouts.value = [];

  // 모든 오디오 정지 및 초기화
  audioElements.value.forEach((audio) => {
    audio.pause();
    audio.currentTime = 0;
  });

  // 이벤트 리스너 제거
  audioElements.value.forEach((audio) => {
    audio.removeEventListener("ended", () => { });
  });
}
</script>
<style>
.met-active {
  background: var(--juston-gradient-1);
}

.met-active > i {
  /* color: black; */
  /* inline 속성은 transform이 적용되지 않는다.. */
  display: inline-block; 
  /* animation: rotate 0.5s linear infinite;  */
  animation: buzz 0.15s linear infinite;
  /* animation: combine 1s linear infinite; */
  position: relative;
  left: 0;
  top: 0;
  z-index: 1;
}

.timer {
    background: -webkit-linear-gradient(left, #b196f7 50%, black 50%);
    /* Foreground color, Background colour */
    border-radius: 100%;
    height: 100px;
    /* Height and width */
    top: 0;
    position: absolute;
    width: 100px;
    /* Height and width */
    animation-name: time;
    animation-duration: 22.5s;
    animation-timing-function: linear;
    animation-iteration-count: infinite;
}
.mask {
    border-radius: 100% 0 0 100% / 50% 0 0 50%;
    height: 100%;
    left: 0;
    position: absolute;
    top: 0;
    width: 50%;
   
    animation-name: mask;
    animation-duration: 22.5s;
    animation-timing-function: linear;
    animation-iteration-count: infinite;
    /* Animation time and number of steps (halved) */
    transform-origin: 100% 50%;
}
@keyframes buzz {
  50% {
    transform: translateX(3px) rotate(2deg);
  }
  100% {
    transform: translateX(-3px) rotate(-2deg);
  }
}

@keyframes time {
    50% {
        -webkit-transform: rotate(180deg);
    }
    100% {
        -webkit-transform: rotate(360deg);
    }
}
@keyframes mask {
    0% {
        background: black;
        /* Background colour */
        -webkit-transform: rotate(0deg);
    }
    50% {
        background: black;
        /* Background colour */
        -webkit-transform: rotate(-180deg);
    }
    50.01% {
        background: #b196f7;
        /* Foreground colour */
        -webkit-transform: rotate(0deg);
    }
    100% {
        background: #b196f7;  
        /* Foreground colour */
        -webkit-transform: rotate(-180deg);
    }
}
</style>
