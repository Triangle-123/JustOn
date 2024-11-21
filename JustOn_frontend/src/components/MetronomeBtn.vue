<template>
  <button
    class="metronome-btn bg-[var(--juston-black)] fixed right-10 bottom-10 w-[100px] h-[100px] rounded-[50%] text-white text-4xl firework-button"
    :class="{ 'met-active': isPlaying }"
    @click="toggleAudio"
  >
    <i class="bi bi-stopwatch-fill"></i>
  </button>
  <!-- <div class="firework-particles" v-if="isPlaying"></div> -->
</template>

<script setup>
import { ref } from "vue";
const soundUrl = import.meta.env.VITE_SOUND_URL;
const audioFiles = [
  { src: soundUrl + "/racestart.mp3", duration: 5, repeatCount: 1 },
  { src: soundUrl + "/metronome.mp3", duration: 1, repeatCount: 10 },
  { src: soundUrl + "/countdown-10.mp3", duration: 11.5, repeatCount: 1 },
  { src: soundUrl + "/wow.mp3", duration: 1, repeatCount: 1 },
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
  isPlaying.value = true;
  audioElements.value = audioFiles.map((file) => {
    const audio = new Audio(file.src);
    audio.volume = 1;
    audio.preload = "auto"; // 미리 로드
    return audio;
  });
  playNextAudio(0); // 첫 번째 오디오부터 재생 시작 ///////////////////////////

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
    stopAllAudio(); // 모든 오디오 재생 종료
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
  }
}

function stopAllAudio() {
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
    audio.removeEventListener("ended", () => {});
  });
}
</script>
<style>
.met-active {
  background-color: #f00;
}
</style>
