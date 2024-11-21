<template>
  <div class="flex justify-center items-center">
    <div class="content-wrap w-[500px]">
      <div
        class="flex justify-between items-center pb-5 mb-10 border-solid border-b border-gray-200"
      >
        <img class="w-[26px]" src="../assets/on-mark.svg" alt="" />
        <a class="logo flex items-end" href="">
          <span class="mr-2 leading-[16px]">날 위한 운동 메이트</span>
          <img class="inline-block" src="../assets/logo.svg" alt="" />
        </a>
      </div>
      <h3 class="text-4xl text-center mb-6">LOGIN</h3>
      <form @submit.prevent="login">
        <label for="id">아이디</label>
        <input
          id="id"
          class="input-style-h60 block mb-3 w-[100%]"
          type="text"
          v-model="userName"
        />
        <label for="pw">비밀번호</label>
        <input
          id="pw"
          class="input-style-h60 w-[100%]"
          type="password"
          v-model="password"
        />
        <div class="btn-box flex justify-center">
          <RouterLink :to="{name: 'signup'}">회원가입</RouterLink>
          <a href="">비밀번호 찾기</a>
          <a class="border-none" href="">아이디 찾기</a>
        </div>
        <button class="btn-b-black w-[100%] mb-4">로그인</button>
        <div class="social-login">
          <span class="block text-center mb-2">간편 로그인</span>
          <div class="flex gap-4 justify-center">
            <button class="kakao flex justify-center items-center bg-[#FFD43B]">
              <img :src="`${imgUrl}/kakao-icon.svg`" alt="카카오 아이콘" />
            </button>
            <button
              class="google flex justify-center items-center bg-[#F1F3F5]"
            >
              <img :src="`${imgUrl}/google-icon.svg`" alt="구글 아이콘" />
            </button>
            <button class="naver flex justify-center items-center bg-[#03C75A]">
              <img :src="`${imgUrl}/naver-icon.svg`" alt="네이버 아이콘" />
            </button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "@/axios/index.js";
import { useUserStore } from "@/stores/user";
import { RouterLink, useRouter } from "vue-router";
const userName = ref("");
const password = ref("");
const userStore = useUserStore();
const router = useRouter();

const imgUrl = import.meta.env.VITE_IMAGE_URL;

const login = async () => {
  try {
    const response = await axios.post("api-user/login", {
      userName: userName.value,
      password: password.value,
    });
  
    // console.dir(response);
    if (response.status === 200) {
      localStorage.setItem("jwt", response.headers.authorization.split(" ")[1]);
      const token = localStorage.getItem("jwt");
      if (token) {
        try {
          // 사용자 정보를 서버에서 가져오기
          const resInfo = await axios.get("api-user/userInfo");
  
          console.dir(resInfo);
          if (resInfo.status === 200) {
            const userData = resInfo.data;
            userStore.setUser(userData); // 사용자 정보 저장
            router.push({name: 'loggedinHome'});
          } else {
            userStore.logout(); // 인증 실패 시 로그아웃 처리
          }
        } catch (error) {
          console.error(error);
          userStore.logout();
        }
      }
    }
  } catch (error) {
    alert("로그인에 실패하였습니다. 다시 시도해 주세요.");
  }
  // console.log(localStorage.getItem('jwt'));
};
</script>

<style scoped>
.btn-box a {
  padding: 0 12px;
  margin: 24px 0;
  border-right: 1px solid #ccc;
  line-height: 1;
}
.btn-box a:last-child {
  border-right: none;
}
.social-login button {
  width: 60px;
  height: 60px;
  border-radius: 16px;
}
</style>
