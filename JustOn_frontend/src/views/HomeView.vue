<template>
  <div class="content-wrap relative" :class="{ dark: isDarkMode }">
    <!-- <div :class="{ hidden: isHidden }" class="black-box"></div> -->
    <div
      class="content-scroll width-[1200px] flex flex-col items-center gap-[80px]"
    >
      <div class="con-title flex flex-col items-center">
        <div class="relative w-[112px] h-[115px] mb-10 opacity-[0.9]">
          <!-- 전원 아이콘 -->
          <div
            :class="{ 'juston-gradient-1-2': isHidden }"
            @click="
              toggleDarkMode();
              toggleDarkMode2();
            "
            class="inline-block fixed z-1000 p-7 bg-white rounded-[50%] border-solid border-2 border-gray-100 shadow-xl mb-6 cursor-pointer juston-gradient-1-2"
          >
            <img src="../assets/on-mark.svg" alt="" />
          </div>
        </div>
        <span class="font-bold text-2xl block">날 위한 운동 Mate</span>
        <h2 class="text-[70px] font-[800] mb-10">JustOn</h2>
        <div
          class="rounded-[32px] overflow-hidden h-[300px] w-[1100px] bg-img-1 bg-[#ccc]"
        ></div>
      </div>

      <article class="">
        <div class="flex flex-col items-center mb-16 pt-10">
          <span class="text-2xl font-[800] mb-2">기획 의도</span>
          <p class="text-xl text-center">
            운동 중 모니터에 켜놓고 사용하는 Always-On 방식의 웹 서비스
          </p>
        </div>
        <div class="flex flex-col items-center pb-10">
          <span class="text-2xl font-[800] mb-2">타겟층</span>
          <p class="text-xl text-center">
            바쁜 생활 속, 유연한 시간 관리가 가능한 홈트레이닝으로 꾸준히
            운동하려는 20-30대 홈트인
          </p>
        </div>
      </article>

      <article class="flex flex-col justify-center text-center">
        <h3 class="art-title mb-6 text-3xl">
          이제는 홈트 시대! Just On의 기능과 장점을 소개합니다!
        </h3>
        <div class="art-txt">
          <h4 class="text-2xl font-[800] mb-2">
            1. 나만의 Youtube 운동영상/음악 추가 가능
          </h4>
          <p class="text-xl">
            정리해두려는 영상을 youtube 링크만으로 추가할 수 있어요.
          </p>
          <p class="text-xl mb-10">
            영상 추가 후에는 나만의 카테고리에 넣어서 카테고리별 재생 및 관리도
            가능합니다. <br />
            추가하신 영상과 곡들은 이후 메인에서 원클릭으로 바로 재생할 수
            있어요!
          </p>

          <div class="flex flex-col pb-24">
            <span class="block font-bold text-2xl"> --- 기본 UI --- </span>
            <img
              class="w-[1000px] mb-16"
              src="../assets/home-func1-img-1.png"
              alt=""
            />
            <span class="block font-bold text-2xl"
              >--- 수정 중일 때 UI ---</span
            >
            <img src="../assets/home-func1-img-2.png" alt="" />
          </div>
        </div>

        <div class="art-txt border-solid border-t border-gray-300 py-[80px]">
          <h4 class="text-2xl font-[800] mb-2">
            2. 운동 집중도 SUPER UP ! 초심플한 UI
          </h4>
          <p class="text-xl">
            콘텐츠와 함께 보이는 수많은 광고, 다른 콘텐츠들에 집중력 저하,
            피로감을 느끼진 않으셨나요?
          </p>
          <p class="text-xl mb-10">
            Just On은 심플한 UI/UX으로 사용자의 운동 집중도를 한층 더 UP
            해줍니다.
          </p>

          <div class="flex flex-col">
            <span class="block font-bold text-2xl">
              --- 메인화면 샘플 사진 ---
            </span>
            <img
              class="w-[1000px] mb-16"
              src="../assets/home-func2-img-1.png"
              alt=""
            />
            <span class="block font-bold text-2xl"
              >--- 로그인 화면 사진 ---</span
            >
            <img src="../assets/home-func2-img-2.png" alt="" />
          </div>
        </div>

        <div class="art-txt border-solid border-t border-gray-300 pt-[80px]">
          <h4 class="text-2xl font-[800] mb-2">
            3. 편리하고 쉬운, 최소한의 운동 일기 기록
          </h4>
          <p class="text-xl">
            꾸준히 운동하는 것도 쉽지 않는데, 기록까지?! Just On과 함께라면
            한방에 쉽게 가능해요!
          </p>
          <p class="text-xl mb-10">
            쉽게 꾸준히 기록을 남기실 수 있도록, '심플한 UI' + '최소한의 기록
            내용'으로 구성했습니다. <br />쌓여가는 기록을 보며, 내 몸의 변화와
            뿌듯함을 함께 느껴보세요!
          </p>

          <div class="flex flex-col">
            <span class="block font-bold text-2xl">
              --- 운동 다이어리 화면 사진---
            </span>
            <img
              class="w-[1000px] mb-16"
              src="../assets/home-func2-img-1.png"
              alt=""
            />
          </div>
        </div>

        <h4 class="text-5xl font-[900] py-[400px]">
          4 ~ 10 ?!?!? <br /><br />COMMING SOON 🤩
        </h4>
      </article>
    </div>
  </div>
  <!-- content-wrap 끝 -->
  <div></div>
</template>

<script setup>
import { isDarkMode, toggleDarkMode } from "@/composables/DarkMode";
import { onMounted, ref } from "vue";
import Swiper from "swiper/bundle";
// import styles bundle
import "swiper/css/bundle";
const isHidden = ref(false);
const toggleDarkMode2 = () => {
  setTimeout(() => {
    isHidden.value = !isHidden.value;
  }, 500); // 500ms = 0.5초
};

onMounted(() => {
  const swiper = new Swiper(".swiper", {
    direction: "vertical",
    loop: true,
    pagination: {
      el: ".swiper-pagination",
    },
    navigation: {
      nextEl: ".swiper-button-next",
      prevEl: ".swiper-button-prev",
    },
    scrollbar: {
      el: ".swiper-scrollbar",
    },
  });
});
</script>

<style scoped>
.bg-img-1 {
  background-image: url(../assets/home-img-1.jpg);
  background-position: 80% 10%;
  background-repeat: no-repeat;
  background-size: contain;
  background-attachment: fixed;
}
.black-box {
  content: "";
  width: 100%;
  height: 100%;
  background-color: var(--balck-to-trans);
  position: absolute;
  left: 0;
  top: 0;
  transition: opacity 0.5s;
}

article {
  width: inherit;
}

.dark .black-box {
  opacity: 0;
}
.art-txt span {
  margin-bottom: 30px;
}
</style>
