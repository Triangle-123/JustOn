<template>
  <div class="content-wrap relative">
    <!-- 콘텐츠 제목 -->
    <div
      class="con-title flex justify-between pb-2 mb-6 border-solid border-b-[1px] border-gray-300"
    >
      <div class="flex gap-2 items-end">
        <h2><i class="bi bi-chevron-right mr-2"></i>다이어리 쓰기</h2>
      </div>
    </div>

    <!-- 작성 영역 시작 -->

    <!-- 내용 작성 -->
    <div class="content">
      <form
        @keydown.enter.prevent
        @submit.prevent
        class="flex flex-col gap-4 mb-3"
      >
        <!-- 날짜 선택 -->
        <fieldset>
          <label for="date" class="font-bold text-lg mr-6">날짜 선택</label>
          <flat-pickr
            id="date"
            v-model="date"
            :config="config"
            placeholder="날짜를 선택해주세요 :)"
            class="date-input mr-2 w-[70%] h-[52px] border-solid border-2 border-gray-200 rounded-[16px] px-4 py-2"
          ></flat-pickr>
        </fieldset>

        <!-- 내용 작성 -->
        <fieldset>
          <label class="block font-bold text-lg mr-6 mb-1" for="content"
            >운동 소감</label
          >
          <textarea
            class="content w-[100%] border-solid border-2 border-gray-200 rounded-[16px] px-4 py-2"
            name="content"
            id="content"
            v-model="content"
            placeholder="오늘의 운동 소감은?"
          ></textarea>
        </fieldset>

        <!-- 운동 영상 선택/추가 -->
        <fieldset class="">
          <legend class="relative block font-bold text-lg mr-6 mb-1">
            운동 내용 추가
          </legend>
          <!-- 추가 form -->
          <div class="flex gap-2 justify-between items-end">
            <div class="flex-1">
              <div class="flex flex-col flex-1">
                <label class="block font-bold text-s text-gray-500" for="sel-ex"
                  >운동 영상 선택</label
                >
                <div
                  class="relative h-[52px] border-solid border-gray-200 border-2 rounded-[16px] pt-2 indent-4"
                >
                  <multiselect
                    class="relative left-0 top-0 w-inherit leading-[32px] z-[1000] bg-white border-b-solid border-b-gray-200 border-2 rounded-[16px] text-gray-500"
                    v-model="title"
                    :options="filteredExercises"
                    :searchable="true"
                    :clearable="true"
                    label="title"
                    placeholder="입력하실 영상 제목을 입력해주세요 :)"
                    @search="handleSearch"
                  />
                </div>
                <!-- <input @keydown.enter="addList" placeholder="오늘은 어떤 영상으로 운동 하셨나요?"
                  class="video-sel w-[100%] h-[52px] border-solid border-2 border-gray-200 rounded-[16px] px-4 py-2"
                  name=""
                  id="sel-ex" v-model="title">
                </input>
                <button
                  type="button"
                  @click="openSch"
                  class="sch-btn btn-m-white absolute right-0 top-0"
                >
                  <i class="bi bi-search"></i>
                </button> -->
              </div>
            </div>
            <div>
              <label
                class="block font-bold text-s text-gray-500 mr-2"
                for="playNum"
                >진행 횟수</label
              >
              <div
                class="w-[130px] h-[52px] flex justify-center border-solid border-2 border-gray-200 rounded-[16px] px-4 py-2"
              >
                <button type="button" @click="minus">
                  <i class="bi bi-dash-lg"></i>
                </button>
                <input
                  type="text"
                  class="content w-[50px] text-center"
                  name=""
                  id="playNum"
                  v-model="playNum"
                />
                <button type="button" @click="plus">
                  <i class="bi bi-plus-lg"></i>
                </button>
              </div>
            </div>
            <button @click="addList" class="btn-m-white">추가</button>
          </div>
        </fieldset>

        <!-- 추가된 List -->
        <div class="">
          <ul class="my-scrollbar h-[298px] bg-[#eee] rounded-[16px] px-4 py-4">
            <li
              class="text-center text-lg text-gray-400"
              v-if="addedVideoList.length === 0"
            >
              추가된 영상이 없습니다.
            </li>
            <li
              class="flex justify-between p-2"
              v-for="(video, index) in addedVideoList"
              :key="index"
            >
              <span class="ellipsis w-[380px] mr-10">{{ video.title }}</span>
              <span class="w-[50px]">{{ video.playNum }} 회</span>
              <button @click="deleteList(index)">
                <i class="bi bi-x-lg"></i>
              </button>
            </li>
          </ul>
        </div>

        <div class="flex justify-center">
          <button
            v-if="!isExist"
            @click="registDiary"
            class="btn-m-black w-[300px]"
          >
            등록하기
          </button>
          <button
            v-if="isExist"
            @click="updateDiary"
            class="btn-m-black w-[300px]"
          >
            수정하기
          </button>
          <button @click="resetForm2" class="btn-m-white ml-3 w-[200px]">
            초기화
          </button>
        </div>
        <!-- 대기창 -->
        <div
          v-if="isLoading"
          class="flex flex-col justify-center items-center w-[100%] h-[90%] absolute left-0 bottom-0 bg-white z-[1000] rounded-[36px]"
        >
          <div class="flex flex-col justify-center items-center mb-2">
            <div class="loader mb-1"></div>
            <div class="font-bold">Just On</div>
          </div>
          <span class="text-center">
            해당 날짜에 작성된 다이어리가 있어요.<br />
            기록을 불러옵니다.
          </span>
        </div>
      </form>

      <!-- 검색 및 영상 선택 모달창 -->
      <!-- <div
        :class="{ hidden: !isSearch }"
        class="fixed left-0 top-0 w-[100%] h-[100%] bg-[rgba(0,0,0,0.3)] flex justify-center items-center"
      >
        <div class="con-title content-wrap pb-2 mb-6 w-[400px] h-[300px]">
          <div class="gap-2 items-end">
            <h2 class="mb-3">
              <i class="bi bi-chevron-right mr-2"></i>운동 영상 검색
            </h2>
            <multiselect
              class="border-solid border-gray-300 border-2 rounded-[16px] pl-4 text-gray-300"
              v-model="selectedExercise"
              :options="filteredExercises"
              :searchable="true"
              :clearable="true"
              label="title"
              placeholder="입력하실 영상 제목을 입력해주세요 :)"
              @search="handleSearch"
            />
          </div>
        </div>
      </div> -->
      <!-- 모달 End -->

      <!-- 대기창 -->
      <!-- <div
        class="flex flex-col justify-center items-center w-[100%] h-[100%] absolute left-0 top-0 bg-white z-[1000] rounded-[36px]"
      >
        <div class="flex flex-col justify-center items-center mb-2">
          <div class="loader mb-1"></div>
          <div class="">Just On</div>
        </div>
        <span class="text-center">
          해당 날짜에 작성된 다이어리가 있어요.<br />
          기록을 불러옵니다.
        </span>
      </div> -->
    </div>
  </div>
</template>

<script setup>
import FlatPickr from "vue-flatpickr-component";
import "flatpickr/dist/flatpickr.css";

import { ref, onMounted, nextTick, watch } from "vue";
import axios from "axios";

import Multiselect from "vue-multiselect";

const emit = defineEmits(["update-list"]);

// 로딩 관련 변수
const isLoading = ref(false);

// 다이어리 내용 관련 변수
const date = ref("");
const content = ref("");
const diaryNo = ref("");
const isExist = ref(false);

// 다이어리 Ex 추가 관련 변수
const title = ref("");
const playNum = ref(1);
const diaryExNoCount = ref(1);
const addedVideoList = ref([]);

// 운동 영상 검색 관련 변수
const isSearch = ref(false);
const exercises = [
  { title: "팔 굽혀 펴기" },
  { title: "스쿼트" },
  { title: "윗몸 일으키기" },
  { title: "자전거 타기" },
  { title: "등산" },
];

// 운동 영상 검색 관련 함수
const openSch = () => {
  isSearch.value = true;
};

// 검색된 운동 리스트
const filteredExercises = ref(exercises);

// 선택된 운동
const selectedExercise = ref(null);

// 사용자가 입력할 때마다 호출되는 검색 함수
const handleSearch = (query) => {
  // 쿼리와 일치하는 제목을 필터링
  filteredExercises.value = exercises.filter((exercise) =>
    exercise.title.toLowerCase().includes(query.toLowerCase())
  );
};

// 사용자가 등록한 운동 영상 리스트 중 검색해서 받아오기
async function schVideoList() {
  if (!date.value) return null;

  try {
    const { data } = await axios.get(
      `http://localhost:8080/api-diary/diary/list/${date.value}`
    );

    // 응답 데이터 유효성 검사
    if (data && typeof data === "object") {
      console.log("API Response:", data);
      return data;
    } else {
      console.warn("Invalid response data format:", response.data);
      return null;
    }
  } catch (error) {
    console.error("데이터 불러오는 중 오류", error);
    return null;
  }
  // else {
  //   alert("날짜를 선택해주세요.");
  // }
}

// 운동 영상 삭제
const deleteList = (index) => {
  addedVideoList.value.splice(index, 1);
};

// 진행 횟수 추가/감소
const plus = () => {
  playNum.value++;
};

const minus = () => {
  if (playNum.value > 0) {
    playNum.value--;
  }
};

// 운동 영상 추가하는 메서드
const addList = () => {
  if (title.value && playNum.value > 0) {
    addedVideoList.value.push({
      diaryExNo: diaryExNoCount.value++,
      title: title.value,
      playNum: playNum.value,
    });
    // 필드 초기화
    title.value = "";
    playNum.value = 1;
  } else {
    alert("영상 제목과 진행 횟수를 입력해주세요.");
  }
};

// 다이어리 등록
const registDiary = async () => {
  if (!date.value) {
    alert("날짜를 입력해주세요.");
    return null;
  }
  if (!content.value) {
    alert("운동소감을 입력해주세요.");
    return null;
  }
  const diary = {
    regDate: date.value,
    content: content.value,
    diaryExList: addedVideoList.value,
    userId: "ssafy",
  };
  try {
    const response = await axios.post(
      "http://localhost:8080/api-diary/diary",
      diary
    );
    emit("update-list");
    alert(date.value + ", 오늘 기록도 완료 :)");
    console.log("등록 성공", response.data);
    if (response.status === 200) {
      date.value = "";
      content.value = "";
      addedVideoList.value = [];
    }
  } catch (error) {
    console.error("등록 실패", error);
  }
};

// 다이어리 수정
const updateDiary = async () => {
  const diary = {
    diaryNo: diaryNo.value,
    regDate: date.value,
    content: content.value,
    diaryExList: addedVideoList.value,
    userId: "ssafy",
  };
  if (!diary.content.value) {
    alert("운동소감을 입력해주세요.");
    return null;
  }
  try {
    const response = await axios.put(
      `http://localhost:8080/api-diary/diary/${diary.diaryNo}`,
      diary
    );
    emit("update-list");
    alert(date.value + ", 수정 완료 :)");
    console.log("수정 성공", response.data);
  } catch (error) {
    console.error("수정 실패", error);
  }
};

// 날짜 선택 시, 해당 다이어리가 있는지 확인 -> isExist 값이 true 일 경우 수정이 보여지도록, 수정 버튼 클릭시에는 수정 메서드 진행
async function selectDiaryByRegDate() {
  if (!date.value) return null;

  try {
    const { data } = await axios.get(
      `http://localhost:8080/api-diary/diary/list/${date.value}`
    );

    // 응답 데이터 유효성 검사
    if (data && typeof data === "object") {
      console.log("API Response:", data);
      return data;
    } else {
      console.warn("Invalid response data format:", response.data);
      return null;
    }
  } catch (error) {
    console.error("데이터 불러오는 중 오류", error);
    return null;
  }
  // else {
  //   alert("날짜를 선택해주세요.");
  // }
}

watch(date, async (newDate) => {
  if (newDate) {
    console.log("조회 실행되었습니다.");
    const data = await selectDiaryByRegDate();
    if (data) {
      isLoading.value = true;

      console.log("Retrieved diary data:", data);
      console.log("data-type : " + typeof data);

      diaryNo.value = data[0].diaryNo ?? "";
      content.value = data[0].content ?? "";
      // diaryExList 배열 처리
      if (Array.isArray(data.diaryExList)) {
        addedVideoList.value = data.diaryExList.map((item) => ({
          diaryExNo: item.diaryExNo,
          title: item.title,
          playNum: item.playNum,
        }));
      } else {
        addedVideoList.value = [];
        isExist.value = false;
        console.warn("diaryExList is not an array:", data.diaryExList);
      }

      isExist.value = true;
      // 3초 후 로딩 상태 false로 변경
      setTimeout(() => {
        isLoading.value = false;
      }, 2500);
      // console.log("data.diaryNo : " + data.diaryNo);
      // console.log("data.content : " + data.content);
      // console.log("data.diaryExList : " + data.diaryExList);
      // diaryNo.value = data.diaryNo;
      // content.value = data.content;
      // addedVideoList.value = data.diaryExList;
    } else {
      resetForm();
    }
  }
});

// 폼 초기화 함수
const resetForm = () => {
  diaryNo.value = "";
  content.value = "";
  addedVideoList.value = [];
  isExist.value = false;
};

// 폼 초기화 함수 - 내용만 초기화
const resetForm2 = () => {
  content.value = "";
  addedVideoList.value = [];
};

// 스크롤 바
import { OverlayScrollbars } from "overlayscrollbars";
import "overlayscrollbars/styles/overlayscrollbars.css";

const options = {
  className: "os-theme-dark",
  resize: "both",
  scrollbars: {
    visibility: "visible",
    autoHide: "never",
    // autoHideDelay: 800,
  },
};

const scrollbarInstance = ref(null);
onMounted(async () => {
  await nextTick();
  const elements = document.querySelectorAll(".my-scrollbar");
  if (elements.length >= 0) {
    scrollbarInstance.value = OverlayScrollbars(elements, options);
  }
});
</script>

<style scoped>
.sch-btn.btn-m-white {
  border: 0;
  background-color: rgba(0, 0, 0, 0);
}
.video-sel {
}
/* HTML: <div class="loader"></div> */
.loader {
  width: 20px;
  aspect-ratio: 1;
  border-radius: 50%;
  background: #000;
  box-shadow: 0 0 0 0 #0004;
  animation: l1 1s infinite;
}
@keyframes l1 {
  100% {
    box-shadow: 0 0 0 30px #0000;
  }
}
</style>
