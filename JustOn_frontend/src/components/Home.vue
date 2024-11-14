<template>
  <div class="content-wrap">
    <div
      class="content-scroll width-[1200px] flex flex-col items-center gap-[80px]"
    >
      <div class="con-title flex flex-col items-center">
        <div>
          <div
            class="inline-block p-7 rounded-[50%] border-solid border-2 border-gray-100 shadow-xl mb-6"
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
        <div class="flex flex-col items-center mb-6 pt-10">
          <span class="text-xl font-[800] mb-2">기획 의도</span>
          <p class="text-lg text-center">
            20-30대 홈트인들을 겨냥해 만든 운동 전 모니터에 띄워놓고,
            <br />운동 중 켜놓고 사용하는 Always-On 방식의 서비스
          </p>
        </div>
        <div class="flex flex-col items-center pb-10">
          <span class="text-xl font-[800] mb-2">타겟층</span>
          <p class="text-lg text-center">20-30대 홈트인</p>
        </div>
      </article>

      <article class="flex flex-col justify-center text-center">
        <h3 class="art-title">
          이제는 홈트 시대! Just On의 기능을 소개합니다!
        </h3>
        <div class="art-txt">
          <h4 class="text-xl font-[800] mb-2">
            기능1. 나만의 Youtube 운동영상/음악 추가 가능
          </h4>
          <p>원하는 영상을 youtube 링크 추가만으로 사용할 수 있어요.</p>
          <p>
            영상 추가 후에는 나만의 카테고리에 넣어서 카테고리별 재생 및 관리도
            가능합니다.
          </p>
          <div class="swiper-container">
            <swiper
              :slidesPerView="3"
              :spaceBetween="30"
              :loop="true"
              :autoplay="{ delay: 3000 }"
              :pagination="{ clickable: true }"
              :navigation="true"
            >
              <swiper-slide>Slide 1</swiper-slide>
              <swiper-slide>Slide 2</swiper-slide>
              <swiper-slide>Slide 3</swiper-slide>
              <swiper-slide>Slide 4</swiper-slide>
              <swiper-slide>Slide 5</swiper-slide>
            </swiper>
          </div>
          <div class="flex flex-col">
            <span class="block">초기UI</span>
            <img
              class="w-[1000px]"
              src="../assets/home-func1-img-1.png"
              alt=""
            />
            <span class="block">수정UI</span>
            <img src="../assets/home-func1-img-2.png" alt="" />
          </div>
        </div>
        <div class="art-img">
          <img src="" alt="" />
        </div>
      </article>
    </div>
  </div>
  <div></div>
</template>

<script setup>
// Swiper
// import { Swiper, SwiperSlide } from "swiper/vue";
// Swiper 스타일 임포트
</script>

<style scoped>
.bg-img-1 {
  background-image: url(../assets/home-img-1.jpg);
  background-position: 80% 10%;
  background-repeat: no-repeat;
  background-size: contain;
  background-attachment: fixed;
}

article {
  width: inherit;
}
</style>
