<template>
  <div class="flex gap-5">
    <div class="video">
      <MainVideoList />
    </div>
    <div class="music">
      <MainMusicList />
    </div>
  </div>
</template>

<script setup>
import MainVideoList from "@/components/MainVideoList.vue";
import MainMusicList from "@/components/MainMusicList.vue";
</script>

<style scoped>
.flex {
  > .video {
    flex-grow: 8;
  }
  > .music {
    /* flex-grow: 2; */
  }
}
</style>
