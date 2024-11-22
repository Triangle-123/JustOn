<template>
  <header :class="isSizeChange ? 'w-change w-change-css' : ''" class="w-[380px] flex flex-col content-wrap-np">
    <div class="top-logo pt-8 px-6 mb-11 flex justify-between">
      <a class="logo flex items-end cursor-pointer" @click="navigateTo('home')">
        <span class="mr-2 leading-[16px]">날 위한 운동 메이트</span>
        <img class="inline-block" src="../assets/logo.svg" alt="" />
      </a>
      <i @click="
        rotate = !rotate;
      sizeChange();
      " :class="{ 'rotate-180': rotate }" class="bi bi-arrow-left-circle cursor-pointer"></i>
    </div>

    <div class="profile mx-6 bg-[--juston-bg-w] p-5 rounded-2xl gap-3">
      <div class="profile-img relative">
        <div class="w-20 h-20 rounded-[50%] overflow-hidden flex items-center justify-center">
          <img class="object-cover max-width-auto" :src="profileStore.imagePreview" />
        </div>
        <!-- 유저 마이페이지 이동 -->
        <input class="hidden" type="file" @change="onFileChange" accept="image/*" href="" id="proImg-upload" />
        <label class="absolute bottom-0 right-0 cursor-pointer text-base" for="proImg-upload" v-if="userStore.user"><i
            class="bi bi-gear shadow-lg shadow-slate-800/10 bg-[#fff] block w-6 h-6 rounded-[50%] text-center leading-6"></i></label>

      </div>

      <!-- <div calss="profile-txt"> -->
      <div class="profile-txt mt-4">
        <p class="rounded-[50%] mb-1" v-if="userStore.user">
          <!-- 안녕하세요!<em>SSAFY</em>님 <br />오늘은 "<em>OO운동</em>" 어떠세요? -->
          <em>{{ userStore.user.nickname }}</em>님 안녕하세요!
          <br />
          {{ userStore.user.email }}
        </p>
        <p class="rounded-[50%] mb-1" v-if="!userStore.user">
          <!-- 안녕하세요!<em>SSAFY</em>님 <br />오늘은 "<em>OO운동</em>" 어떠세요? -->
          날 위한 운동 Mate
          <br />
          <em>Just On</em>으로 일단 시작! 
        </p>
         
        <!-- <a
          class="rounded-7 bg-[#fff] px-2 py-1 border-solid border-gray-300 border-[1px] rounded-2xl text-xs"
          href=""
          >오늘의 추천 운동<i class="bi bi-chevron-right"></i
        ></a> -->
      </div>
      
    </div>

    <!-- 로그인 회원가입 -->
    <div class="login-sign text-lg btn-box mt-4 mb-6 mx-6 flex gap-3 bg-[--juston-bg-w] rounded-2xl px-4 py-3"
      v-if="!userStore.user">
      <RouterLink class="font-bold flex-1 text-center" :to="{ name: 'login' }"><i
          class="bi bi-box-arrow-in-right mr-1.5"></i>로그인</RouterLink>
      <em class="w-[1px] bg-[#ccc]"></em>
      <RouterLink class="font-bold flex-1 text-center" :to="{ name: 'signup' }"><i
          class="bi bi-person-plus-fill mr-1.5"></i>회원가입</RouterLink>
    </div>

    <!-- 로그아웃 마이페이지 -->
    <div class="login-sign text-lg btn-box mt-4 mb-6 mx-6 flex gap-3 bg-[--juston-bg-w] rounded-2xl px-4 py-3"
      v-if="userStore.user">
      <a class="font-bold flex-1 text-center" @click="logout" href=""><i
          class="bi bi-box-arrow-right mr-1.5"></i>로그아웃</a>
      <em class="w-[1px] bg-[#ccc]"></em>
      <RouterLink class="font-bold flex-1 text-center" :to="{ name: 'mypage' }"><i
          class="bi bi-person-fill mr-1.5"></i>마이페이지</RouterLink>
    </div>

    <!-- BLACK 영역 -->
    <div class="menus py-10 px-6 flex flex-col flex-1 bg-[--juston-black] rounded-[36px] gap-8">
      <div class="txt-box text-[#fff] text-center flex flex-col items-center text-base">
        <img on-click="" class="w-[60px] h-[60px] mb-4 fill-white cursor-pointer" src="../assets/on-btn-w.svg" />
        <p class="juston-gradient-text font-bold">
          Just On ! 오늘도 힘차게 시작해볼까요!
        </p>
      </div>
      <!-- 메뉴 -->
      <nav class="big-menu w-[100%] text-lg flex flex-col flex-grow gap-3">
        <a v-for="(menu, index) in menus" :key="index"
          class="menu-item bg-[#fff] p-4 rounded-[16px] block hover-effect cursor-pointer" @click="
            // setActive(index);
            navigateTo(menu.routerName);
          " :class="{ 'juston-gradient-1-2': activeIndex == index }">
          <i v-show="activeIndex == index" class="bi bi-arrow-right-circle mr-3"></i>{{ menu.menuName }}
        </a>
      </nav>

      <nav class="small-menu hidden w-[100%] text-lg flex justify-center items-center flex-col flex-grow gap-3">
        <a v-for="(menu, index) in sMenus" :key="index" class="menu-item rounded-[16px] block" href="#none"
          @click="setActive(index)">
          <i :class="menu.iconClass" class="mr-3"></i>
        </a>
      </nav>
      <!-- CopyRight -->
      <span class="copyright text-white text-end flex-self-end">ⓒ HONG & YEON</span>
    </div>
  </header>
</template>

<script setup>
import { watch, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { useProfileStore } from "@/stores/profile";

const userStore = useUserStore();
const profileStore = useProfileStore();
const route = useRoute();
const isSizeChange = ref(false);
const rotate = ref(false);
const menus = [
  {
    menuName: "Home",
    routerName: "home",
  },
  {
    menuName: "컨텐츠 등록",
    routerName: "addContent",
  },
  {
    menuName: "운동 다이어리",
    routerName: "diaryList",
  },
];
const sMenus = [
  { iconClass: "bi bi-house-heart", text: "홈" },
  { iconClass: "bi bi-patch-plus-fill", text: "컨텐츠 등록" },
  { iconClass: "bi bi-journal-plus", text: "운동 다이어리" },
  { iconClass: "", text: "PlayList 등록" },
];
const router = useRouter();
const navigateTo = (routeName) => {
  console.log(routeName);
  // if (routeName === 'home' && userStore.user) {
  //   router.push({ name: 'loggedinHome' });
  // }
  // else 
  router.push({ name: routeName });
};

const activeIndex = ref(0);
const setActive = (index) => {
  activeIndex.value = index;
};
const width = ref();
const sizeChange = () => {
  console.log("w-ch");
  isSizeChange.value = !isSizeChange.value;
};
const logout = () => {
  userStore.logout();
  router.push({ name: 'home' });
};

console.log(route);

watch(() => route.name, () => {
  for (const index in menus) {
    if (menus[index].routerName === route.name) {
      setActive(index);
      return;
    }
  }
  setActive(0);
});


// ========================
// 프로필 이미지 파일 선택 및 업로드
// ========================
const file = ref(null);
// 이미지 파일 선택 시 실행
const onFileChange = (event) => {
  const selectedFile = event.target.files[0];
  // console.log(event);

  if (selectedFile) {
    // 미리보기 이미지 업데이트
    // imagePreview.value = URL.createObjectURL(selectedFile);

    file.value = selectedFile; // 파일 상태 저장
    // console.log("##file.value : ", file.value);
    profileStore.uploadImage(file.value);
  }
};

watch(() => userStore.user, () => {
  profileStore.getUserImage();
}, { immediate: true });


</script>

<script>
let window_scrolling;

window.addEventListener("scroll", () => {
  if (!window_scrolling) {
    console.log("start wheeling!");
    document.documentElement.classList.remove("scroll-hidden");
  }

  // 일정시간 뒤에 스크롤 동작 멈춤을 감지
  clearTimeout(window_scrolling);
  window_scrolling = setTimeout(() => {
    console.log("stop wheeling!");
    window_scrolling = undefined;
    document.documentElement.classList.add("scroll-hidden");
  }, 400);
});
</script>

<style scoped>
header {
  width: 380px;
  height: 100%;
  overflow: hidden;
  transition: all 0.8s;

  >div {
    display: flex;
  }
}

.w-change {
  width: 70px;
  overflow: hidden;
}

.w-change .top-logo {
  flex-direction: column;
  justify-content: center;
}

.w-change .top-logo a {
  order: 1;
  flex-direction: column;
  align-items: center;
  gap: 30px;
}

.w-change .top-logo a span {
  margin-right: 0;
  vertical-align: center;
  text-align: center;
}

.w-change .top-logo a img {
  max-width: 70px;
  height: 30px;
  transform: rotate(90deg);
}

.w-change .top-logo>i {
  order: 0;
  margin-bottom: 10px;
}

.w-change .profile {
  display: none;
}

.w-change .login-sign {
  display: none;
}

.w-change .menus {
  justify-content: center;
}

.w-change .menus .big-menu {
  display: none;
}

.w-change .menus .small-menu {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.w-change .small-menu i {
  margin-right: 0;
  color: white;
}

.small-menu a:hover {}

.w-change .menus .txt-box>p {
  display: none;
}

.w-change .copyright {
  writing-mode: vertical-rl;
}

em {
  font-weight: 800;
}
</style>
