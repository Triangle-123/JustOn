<template>
  <header class="flex flex-col content-wrap-np">
    <div class="top-logo pt-8 px-6 mb-11 flex justify-between">
      <a class="logo" href=""
        ><span class="mr-2">날 위한 운동 메이트</span>
        <img class="inline-block" src="../assets/logo.svg" alt="" />
      </a>
      <i
        @click="[(rotate = !rotate), sizeChange]"
        :class="{ 'rotate-180': rotate }"
        class="bi bi-arrow-left-circle cursor-pointer"
      ></i>
    </div>

    <div class="profile mx-6 bg-[--juston-bg-w] p-5 rounded-2xl gap-3">
      <div class="profile-img relative">
        <div class="w-20 h-20 rounded-[50%]">
          <img class="" src="../assets/juston-default-profile.png" />
        </div>
        <a class="absolute bottom-0 right-0" href=""
          ><i
            class="bi bi-gear shadow-lg shadow-slate-800/10 bg-[#fff] block w-6 h-6 rounded-[50%] text-center leading-6"
          ></i
        ></a>
      </div>

      <div calss="profile-txt">
        <p class="rounded-[50%] mb-1">
          안녕하세요!<em>SSAFY</em>님 <br />오늘은 "<em>OO운동</em>" 어떠세요?
        </p>
        <a
          class="rounded-7 bg-[#fff] px-2 py-1 border-solid border-gray-300 border-[1px] rounded-2xl text-xs"
          href=""
          >오늘의 추천 운동<i class="bi bi-chevron-right"></i
        ></a>
      </div>
    </div>

    <!-- 로그인 회원가입 -->
    <div
      class="text-lg btn-box mt-4 mb-6 mx-6 flex gap-3 bg-[--juston-bg-w] rounded-2xl px-4 py-3"
    >
      <a class="font-bold flex-1 text-center" href=""
        ><i class="bi bi-chat-right-heart mr-1.5"></i>로그인</a
      >
      <em class="w-[1px] bg-[#ccc]"></em>
      <a class="font-bold flex-1 text-center" href=""
        ><i class="bi bi-person-plus-fill mr-1.5"></i>회원가입</a
      >
    </div>

    <!-- BLACK 영역 -->
    <div
      class="py-10 px-6 flex flex-col flex-1 bg-[--juston-black] rounded-[36px] gap-8"
    >
      <div class="text-[#fff] text-center flex flex-col items-center text-base">
        <img
          on-click=""
          class="w-[60px] h-[60px] mb-4 fill-white cursor-pointer"
          src="../assets/on-btn-w.svg"
        />
        <p class="juston-gradient-text font-bold">
          Just On ! 오늘도 힘차게 시작해볼까요!
        </p>
      </div>
      <!-- 메뉴 -->
      <nav class="w-[100%] text-lg flex flex-col flex-grow gap-3">
        <a
          v-for="(menu, index) in menus"
          :key="index"
          class="menu-item bg-[#fff] p-4 rounded-[16px] block hover-effect"
          href="#none"
          @click="setActive(index)"
          :class="{ 'juston-gradient-1': activeIndex === index }"
        >
          <i
            v-show="activeIndex === index"
            class="bi bi-arrow-right-circle mr-3"
          ></i
          >{{ menu }}
        </a>
      </nav>
      <!-- CopyRight -->
      <span class="text-white text-end flex-self-end">ⓒ HONG & YEON</span>
    </div>
  </header>
</template>

<script setup>
import { ref } from "vue";

const rotate = ref(false);
const menus = ["Home", "컨텐츠 등록", "운동 다이어리", "PlayList 등록"];
const activeIndex = ref(0);
const setActive = (index) => {
  activeIndex.value = index;
};
const width = ref();
const sizeChange = () => {};
</script>

<style scoped>
header {
  width: 380px;
  height: 100%;

  > div {
    display: flex;
  }
}

em {
  font-weight: 800;
}
</style>
