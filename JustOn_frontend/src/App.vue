<template >
    <SideHeader />
    <RouterView />
    <!-- <MetronomeBtn v-if="userStore.user"/> -->
    <!-- <ExSuggest v-if="userStore.user"/> -->
    <!-- <UserCustom v-if="userStore.user"/> -->


    <!-- <DiaryList /> -->
    <!-- <VideoTest /> -->
    <!-- <RouterLink :to="{name:home}">home</RouterLink> -->
    
    <!-- <LoginTest /> -->
    <!-- <MainLoginedView /> -->
    
    <div
        v-for="(circle, index) in circles"
        :key="index"
        class="circle"
        :id="'circle'+index"
        :style="{
          width: circle.size + 'px',
          height: circle.size + 'px',
          transform: `translate(${circle.x}px, ${circle.y}px)`
        }"
      ></div>
</template>

<script setup>
import { onMounted, watch, ref, onUpdated } from "vue";
import SideHeader from "@/components/SideHeader.vue";
import MetronomeBtn from "./components/MetronomeBtn.vue";
import ExSuggest from "@/components/ExSuggest.vue"; 
import { useUserStore } from "./stores/user";
import UserCustom from "./components/userCustom.vue";
const userStore = useUserStore();
// import DiaryList from "./components/DiaryList.vue";
// import VideoTest from "@/components/VideoTest.vue";
// import Home from "./components/Home.vue";
// import MainLoginedView from "@/components/views/MainLoginedView.vue";

watch(() => userStore.user,() => {
  if(userStore.user.color) {
    console.log('user', userStore.user);
    const color1 = userStore.user.color.split(".")[0];
    const color2 = userStore.user.color.split(".")[1];
    const color3 = userStore.user.color.split(".")[2];
    
    document.documentElement.style.setProperty('--bg-gradient-color1', color1);
    document.documentElement.style.setProperty('--bg-gradient-color2', color2);
    document.documentElement.style.setProperty('--bg-gradient-color3', color3);
    document.documentElement.style.setProperty('--juston-gradient-1', `linear-gradient(to right, ${color1}, ${color2}, ${color3})`);
  }
})

// 스크롤바를 고려한 화면의 크기 제한
const screenWidth = document.documentElement.clientWidth;
const screenHeight = document.documentElement.clientHeight;

const isAnimated = ref(false);
// 원의 상태를 관리하는 reactive 데이터
const circles = ref([
      {
        size: 500,
        x: Math.random() * screenWidth,
        y: Math.random() * screenHeight,
        speedX: (Math.random() - 0.5) * 4,
        speedY: (Math.random() - 0.5) * 4,
      },
      {
        size: 500,
        x: Math.random() * screenWidth,
        y: Math.random() * screenHeight,
        speedX: (Math.random() - 0.5) * 4,
        speedY: (Math.random() - 0.5) * 4,
      },
      {
        size: 500,
        x: Math.random() * screenWidth,
        y: Math.random() * screenHeight,
        speedX: (Math.random() - 0.5) * 4,
        speedY: (Math.random() - 0.5) * 4,
      },
    ]);
  
  // 애니메이션 실행 함수
  const animate = () => {
    isAnimated.value = true;
      circles.value.forEach(circle => {
        // 원의 위치 업데이트
        circle.x += circle.speedX;
        circle.y += circle.speedY;


         // 원의 위치가 화면을 벗어나지 않도록 제한
         circle.x = Math.max(circle.size / 2, Math.min(circle.x, screenWidth - circle.size / 2));
        circle.y = Math.max(circle.size / 2, Math.min(circle.y, screenHeight - circle.size / 2));

        // 벽에 부딪히면 속도 반전
        if (circle.x === circle.size / 2 || circle.x === screenWidth - circle.size / 2) {
          circle.speedX = -circle.speedX;
        }
        if (circle.y === circle.size / 2 || circle.y === screenHeight - circle.size / 2) {
          circle.speedY = -circle.speedY;
        }
      });
      // 요청 애니메이션 프레임을 이용해 반복 실행
      requestAnimationFrame(animate);
    };

    // 컴포넌트가 마운트될 때 애니메이션 시작
    onUpdated(() => {
      if(!isAnimated.value) animate();
    });

</script>

<style>
  /* .user-bg {
    background: linear-gradient(
    90deg,
    var(--bg-gradient-color1),
    var(--bg-gradient-color2),
    var(--bg-gradient-color3)
  );
  } */
  .circle {
  position: absolute;
  border-radius: 50%;
  filter: blur(60px);
  z-index: -1 ;
}

#circle0 {
  background: var(--bg-gradient-color1);
}

#circle1 {
  background: var(--bg-gradient-color2);
}

#circle2 {
  background: var(--bg-gradient-color3);
}

#app {
  background-color: #f0f0f0;
  overflow: hidden;
  position: relative;
  z-index: 2;
  padding: 30px;
}

</style>
