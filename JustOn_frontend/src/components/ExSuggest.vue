<template>
  <button
    :class="switchStore.isKicked ? 'kick' : ''"
    class="slot-btn absolute left-0 top-0 p-4 w-[100px] h-[100px] bg-[var(--juston-black)] border-dotted border-gray-300 border-[8px] rounded-[50%] text-xl font-bold text-white"
    @click="toggleSlot"
  >
    EXER<br />SLOT
  </button>
  <!-- <div class="" >
  </div> -->

  <div
    v-if="isSlotOpen"
    class="modal-overlay z-[1000] flex justify-center items-center bg-[rgba(0,0,0,0.6)] fixed left-0 top-0 w-full h-full"
  >
    <!--  -->
    <div class="flex justify-center items-center">
      <div
        class="slot-machine bottom-[150p] bg-[#f7f7f7] w-[400px] py-12 px-8 rounded-[16px]"
        v-click-outside="closeSlot"
      >
        <span class="bg-[var(--juston-black)] text-white px-6 pt-6 pb-2 mr-1"
          ><strong class="text-4xl font-[800] juston-gradient-text"
            >EXERCISE SLOT</strong
          ></span
        >
        <div>
          <span class="block mt-10 mb-0 text-xl font-bold"
            >나에게 부족한 운동은?</span
          >
          <div class="slot mb-10 mt-2 rounded-[50px]">
            <div
              class="items"
              :class="{ spinning: isSpinning, start: isStart, stop: isStop }"
              :style="slotStyle"
            >
              <p
                class="font-[700] text-4xl"
                v-for="(item, index) in visibleItems"
                :key="index"
              >
                {{ item }}
              </p>
            </div>
          </div>
        </div>

        <button
          class="start-btn bg-white text-2xl pt-2 font-[800] w-[100px] h-[100px] rounded-[50%]"
          @click="startSlotMachine"
          :disabled="isStart"
          v-if="!isStop"
        >
          <span class="juston-gradient-text font-[800]"> START </span>
        </button>
        <button
          class="bg-[#555] text-2xl pt-2 font-[800] w-[100px] h-[100px] rounded-[50%]"
          @click="returnSlotMachine"
          v-if="isStop"
        >
          <span class="text-white font-[600]"> RETRY </span>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import axios from "@/axios/index";
import { useSwitchStore } from "@/stores/switch";

const switchStore = useSwitchStore();
const isSlotOpen = ref(false);
const closeSlot = () => {
  isSlotOpen.value = false;
};
function toggleSlot() {
  isSlotOpen.value = !isSlotOpen.value;
  console.log("isSlotOpen.value");
  console.log(isSlotOpen.value);
}
// const items2 = ['🍎', '🍊', '🍇', '🍒', '🍋', '🍉']; // 슬롯 데이터
const items2 = ["🔴", "🟠", "🟡", "🟢", "🔵", "🟣", "🟤", "⚫", "⚪"];
const items = [
  "어깨🏋️‍♀️",
  "다리🦵",
  "배🚢",
  "🐔가슴",
  "등💡",
  "팔💪",
  "스트레칭🧘‍♀️",
  "유산소🏃‍♀️",
]; // 슬롯 데이터
const visibleItems = ref([...items, ...items]); // 무한 스크롤 효과를 위한 데이터 반복
const compareItems = [
  "shoulder",
  "leg",
  "abs",
  "chest",
  "back",
  "arm",
  "stretching",
  "cardio",
];
const suggestItems = ref([]); // 추천해줄 부위를 저장하는 데이터
const isSpinning = ref(true);
const isStart = ref(false);
const isStop = ref(false);
const position = ref(0); // 현재 위치
const selectedIndex = ref(0); // 선택된 아이템 인덱스
const slotItemHeight = 100; // 아이템의 높이(px)

const getExRecord = async () => {
  try {
    const response = await axios.get("api-ex");
    console.log(response.data);
    const min = ref(response.data[0].partValue);
    for (const ex of response.data) {
      if (ex.partValue < min.value) {
        suggestItems.value.length = 0;
        suggestItems.value.push(ex.part);
        min.value = ex.partValue;
      } else if (ex.partValue == min.value) {
        suggestItems.value.push(ex.part);
      }
    }
    console.log(suggestItems.value);
  } catch (error) {
    console.error(error);
  }
};

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
  selectedIndex.value = compareItems.indexOf(
    suggestItems.value[Math.floor(Math.random() * suggestItems.value.length)]
  );
  // console.log(selectedIndex.value);
  // console.log(visibleItems.value);
  // 선택된 값이 중앙에 위치하도록 `position` 조정
  position.value = selectedIndex.value * slotItemHeight;

  // 중앙 정렬
  setTimeout(() => {
    isStart.value = false;
    isStop.value = true;
  }, 500); // CSS transition 시간과 동일
}

const returnSlotMachine = () => {
  isStart.value = false;
  isStop.value = false;
  isSpinning.value = true;
};
</script>

<style scoped>
.opened-slot {
  display: block;
}
.slot-machine {
  text-align: center;
  background-color: rgba(255, 255, 255, 0.85);
  /* clip-path: polygon(3% 0%, 97% 0%, 100% 100%, 0% 100%); */
  border: 10px dotted var(--juston-gradient-1);
  animation: 0.3s cubic-bezier(0.39, 0.575, 0.565, 1) 0s 1 normal both running
    scale-up-center;
}
.slot {
  color: #222;
  height: 100px;
  overflow: hidden;
  border: 3px solid #ccc;
  position: relative;
  background: white;
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
.slot-btn {
  animation: bling 0.8s infinite;
  transition: all 0.2s ease-in-out;
  transform: scale(0.5);
  opacity: 0;
}

p {
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.start-btn {
  /* box-shadow: 0px 8px 0px 0px #777; */
  animation: click 1s ease infinite;
  transition: 0.5s all;
  background: var(--juston-black);
}

.kick {
  left: -50px;
  top: 0px;
  transform: scale(1);
  opacity: 1;
}
/* .control-btn:hover {
  box-shadow: 0px 0px 0px 0px #777;
  margin-top: 5px;
} */
@keyframes spin {
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(-100%);
  }
}
@keyframes bling {
  0% {
    border-color: gray;
  }
  80% {
    border-color: white;
  }
}
@keyframes click {
  0% {
  }
  50% {
    background-color: #555;
  }
}
@keyframes scale-up-center {
  0% {
    transform: scale(0.5);
  }
  80% {
    transform: scale(1.08);
  }
  85% {
    transform: scale(0.95);
  }
  90% {
    transform: scale(1.03);
  }
  95% {
    transform: scale(0.98);
  }
  100% {
    transform: scale(1);
  }
}
</style>
