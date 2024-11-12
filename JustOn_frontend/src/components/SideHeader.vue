<template>
  <header>
    <div>
      <a class="logo" href=""
        >날 위한 운동 메이트 <img src="../assets/logo.svg" alt="" />
      </a>
      <i class="bi bi-arrow-right-circle"></i>
    </div>
    <div class="profile">
      <div class="profile-img">
        <img src="">
        <a href=""><i class="bi bi-gear"></i></a>
      </div>
      <div calss="profile-txt">
        <span>안녕하세요! <em>SSAFY</em>님 오늘은 "<em>OO운동</em>" 어떠세요?</span>
        <a class="border-solid border-2 border-[#eee]-300" href="">오늘의 추천 운동<i class="bi bi-chevron-right"></i></a>
      </div>
    </div>
  </header>
</template>

<script setup>

  
</script>

<style scoped>
header {
  width: 380px;
  height: 100%;
  border: 1px solid #eee;
  border-radius: 36px;
  padding: 32px 26px;
  box-shadow: 0 0 30px rgba(0,0,0,0.05);
  background-color: #fff;
  > div {
    display: flex;
    justify-content: space-between;
  }
}

</style>
