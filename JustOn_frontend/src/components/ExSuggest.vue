<template>
    <div class="slot-machine">
      <div class="slot">
        <div
          class="items"
          :class="{ spinning: isSpinning, start : isStart, stop : isStop }"
          :style="slotStyle"
        >
          <p v-for="(item, index) in visibleItems" :key="index">{{ item }}</p>
        </div>
      </div>
      <button @click="startSlotMachine" :disabled="isStart" v-if="!isStop">Start</button>
      <button @click="returnSlotMachine" v-if="isStop">retry</button>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue';
  import axios from '@/axios/index';
  
//   const items = ['🍎', '🍊', '🍇', '🍒', '🍋', '🍉']; // 슬롯 데이터
  const items = ['어깨', '다리', '복부', '가슴', '등', '팔', '스트레칭', '유산소']; // 슬롯 데이터
  const visibleItems = ref([...items, ...items]); // 무한 스크롤 효과를 위한 데이터 반복
  const compareItems = ['shoulder', 'leg', 'abs', 'chest', 'back', 'arm', 'stretching', 'cardio'];
  const suggestItems = ref([]); // 추천해줄 부위를 저장하는 데이터
  const isSpinning = ref(true);
  const isStart = ref(false);
  const isStop = ref(false);
  const position = ref(0); // 현재 위치
  const selectedIndex = ref(0); // 선택된 아이템 인덱스
  const slotItemHeight = 100; // 아이템의 높이(px)
  
  const getExRecord = async () => {
    try {
        const response = await axios.get('api-ex');
        console.log(response.data);
        const min = ref(response.data[0].partValue);
        for(const ex of response.data) {
            if(ex.partValue < min.value) {
                suggestItems.value.length = 0;
                suggestItems.value.push(ex.part);
                min.value = ex.partValue;
            } else if(ex.partValue == min.value) {
                suggestItems.value.push(ex.part);
            }
        }
        console.log(suggestItems.value);
    } catch (error) {
        console.error(error);
    }
    
  }

  getExRecord();

  // CSS 스타일 계산
  const slotStyle = computed(() => ({
    transform: `translateY(-${position.value}px)`,
  }));
  
  function startSlotMachine() {
    if (isStart.value) return;
  
    isSpinning.value = false;
    isStart.value = true;
  
    // 슬롯 머신 동작
    spinSlotMachine();
  
    // 일정 시간 후 종료
    const duration = 2000; // 2초
    setTimeout(() => {
      stopSlotMachine();
    }, duration);
  }
  
  function spinSlotMachine() {
    const intervalTime = 100; // 애니메이션 간격
    const totalHeight = slotItemHeight * items.length; // 한 사이클의 높이
  
    
    // 슬롯머신 애니메이션
    const interval = setInterval(() => {
      position.value = (position.value + slotItemHeight) % totalHeight;
    }, intervalTime);
  
    // 애니메이션 종료 시점에 인터벌 중지
    setTimeout(() => {
      clearInterval(interval);
    }, 2000); // 반드시 애니메이션 총 지속 시간과 동일해야 함
  }
  
  function stopSlotMachine() {
    // isSpinning.value = false;
  
    // 랜덤한 값 선택
    selectedIndex.value = compareItems.indexOf(suggestItems.value[Math.floor(Math.random() * suggestItems.value.length)]);
    // console.log(selectedIndex.value);
    // console.log(visibleItems.value);
    // 선택된 값이 중앙에 위치하도록 `position` 조정
    position.value = selectedIndex.value * slotItemHeight;
  
    // 부드럽게 중앙 정렬
    setTimeout(() => {
      isStart.value = false;
      isStop.value = true;
    }, 500); // CSS transition 시간과 동일
  }

  const returnSlotMachine = () => {
    isStart.value = false;
    isStop.value = false;
    isSpinning.value = true;
  }
  </script>
  

  <style scoped>
  .slot-machine {
    text-align: center;
    margin-top: 50px;
  }
  .slot {
    height: 100px;
    overflow: hidden;
    border: 2px solid #333;
    margin-bottom: 20px;
    position: relative;
  }
  .items {
    display: flex;
    flex-direction: column;
    position: relative;
    transition: transform 0.5s ease-in-out; /* 애니메이션 종료 시 부드러운 효과 */
  }
  .items.spinning {
    animation: spin 5s linear infinite; /* 슬롯머신 회전 애니메이션 */
  }
  .items.start {
    animation: spin 1s linear infinite;
  }
  .items.stop {
    animation: none; 
  }
  p {
    height: 100px;
    line-height: 100px;
    font-size: 2rem;
    text-align: center;
  }
  @keyframes spin {
    0% {
      transform: translateY(0);
    }
    100% {
      transform: translateY(-100%);
    }
  }
  </style>
  