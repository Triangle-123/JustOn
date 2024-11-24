<template>
  <div id="videoPlayer" class="w-[100%] h-[100%] bg-[#eee] rounded-[16px] overflow-hidden">
    <!-- <iframe
      width="100%"
      height="100%"
      :src="video.src"
      frameborder="0"
      allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
      referrerpolicy="strict-origin-when-cross-origin"
      allowfullscreen
    ></iframe> -->
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';
import { useSwitchStore } from '@/stores/switch';
import { useApiStore } from '@/stores/api';

const switchStore = useSwitchStore();
const apiStore = useApiStore();

const isPlayerLoaded = ref(false);
const props = defineProps(['video']);
const emit = defineEmits(['nextVideo']);
const player = ref();

const initializePlayer = () => {
  return new Promise((resolve) => {
    console.log("video api before-init");
    if (apiStore.isApiLoaded) {
      console.log(props.video.title, props.video.videoId)
      player.value = new YT.Player("videoPlayer", {
        height: "100%",
        width: "100%",
        videoId: props.video.videoId,
        events: {
          onReady: () => {
            console.log("video init");
            isPlayerLoaded.value = true;
            clearTimeout(play.value);
            resolve();
          },
          onStateChange: onPlayerStateChange,
        },
      });
    }
  });
};

const onPlayerStateChange = () => {
  const playerState = player.value.getPlayerState();
  if (playerState == 0) {
    emit('nextVideo');
  }
}

const cueVideo = () => {
  return new Promise((resolve) => {
    player.value.cueVideoById({
      videoId: props.video.videoId,
      startSeconds: 0,
      endSeconds: 0,
      suggestedQuality: "large",
    });

    resolve();
  })
};

const loadVideo = () => {
  return new Promise((resolve) => {
    player.value.loadVideoById({
      videoId: props.video.videoId,
      startSeconds: 0,
      endSeconds: 0,
      suggestedQuality: "large",
    });

    resolve();
  })
};
const play = ref();
watch(() => props.video, () => {
  if (isPlayerLoaded.value) {
    console.log("hahahah")
    loadVideo();
  }
})

onMounted(() => {
  if (apiStore.isApiLoaded) {
    initializePlayer().then(() => {
      cueVideo();
    });
  }
  else console.log("not init");
})

watch(() => apiStore.isApiLoaded, () => {
  if (apiStore.isApiLoaded) {
    initializePlayer().then(() => {
      cueVideo();
    });
  }
  else console.log("not init");
})

watch(() => switchStore.isOff, () => {
  if (switchStore.isOff) {
    if (player.value.getPlayerState() == 1) player.value.pauseVideo();
  }
  else {
    if (player.value.getPlayerState() == 2) player.value.playVideo();
  }
})
</script>

<style scoped></style>
