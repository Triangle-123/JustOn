<template>
  <SideHeader />
  <RouterView/>
  <!-- <DiaryList /> -->
  <!-- <VideoTest /> -->
  <!-- <RouterLink :to="{name:home}">home</RouterLink> -->

  <!-- <LoginTest /> -->

</template>

<script setup>

import SideHeader from "@/components/SideHeader.vue";
// import DiaryList from "./components/DiaryList.vue";
// import VideoTest from "@/components/VideoTest.vue";
// import Home from "./components/Home.vue";
import { useUserStore } from "@/stores/user";
import {ref} from 'vue';
const init = ref(false);
const userStore = useUserStore();

const userInfo = async () => {
  await userStore.getUser();
  init.value = true;
}
userInfo();
</script>

<style></style>
