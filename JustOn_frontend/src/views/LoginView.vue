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
      <form class="">
        <label for="id">아이디</label>
        <input
          id="id"
          class="input-style-h60 block mb-3 w-[100%]"
          type="text"
        />
        <label for="pw">비밀번호</label>
        <input id="pw" class="input-style-h60 w-[100%]" type="password" />
        <div class="btn-box flex justify-center">
          <a href="">회원가입</a>
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
console.log("###s");
console.log(import.meta.env.VITE_IMAGE_URL);
const imgUrl = import.meta.env.VITE_IMAGE_URL;
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
