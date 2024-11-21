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
          <div class="flex gap-3 justify-between items-end">
            <div class="flex-1">
              <div class="flex flex-col flex-1">
                <label class="block font-bold text-s text-gray-500" for="sel-ex"
                  >운동 영상 선택</label
                >
                <div
                  class="relative bg-[rgba(0,0,0,0)] h-[52px] border-solid border-gray-200 border-2 rounded-[16px] indent-4 cursor-pointer"
                  @click="openSchModal"
                >
                  <!-- 영상 검색 버튼 -->
                  <input class="leading-[52px] w-[100%] relative z-[0]" v-model="title"  placeholder="영상을 검색해주세요." disabled></input>
                  <button class="w-[52px] h-[52px] absolute right-0 top-0">
                    <i class="bi bi-search text-[#000]"></i>
                  </button>
                </div>
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
            v-if="!isUpdate"
            @click="registDiary"
            class="btn-m-black w-[300px]"
          >
            등록하기
          </button>
          <button
            v-if="isUpdate"
            @click="updateDiary"
            class="btn-m-black w-[300px]"
          >
            수정하기
          </button>
          <button @click="resetForm" class="btn-m-white ml-3 w-[200px]">
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
          <span class="text-center text-l">
            해당 날짜에 작성된 다이어리가 있어요.<br />
            기록을 불러옵니다.
          </span>
        </div>
      </form>

      <!-- 검색 및 영상 선택 모달창 " -->
      <div
        :class="{ hidden: !isSearch }"
        class="fixed left-0 top-0 w-[100%] h-[100%] bg-[rgba(0,0,0,0.3)] flex justify-center items-center"
      >
        <div class="con-title content-wrap pb-2 mb-6 w-[400px] h-[600px]">
          <div class="gap-2 items-end">
            <h2 class="mb-3">
              <i class="bi bi-chevron-right mr-2"></i>운동 영상 검색
            </h2>

            <!-- 검색 인풋 -->
            <input
              type="text"
              @input="handleSearch"
              placeholder="영상 제목을 검색해주세요"
              class="w-full border-2 border-gray-200 rounded-[16px] px-4 py-2 mb-4"
            />

            <!-- 검색 결과 리스트 -->
            <ul class="overflow-y-auto h-[300px] bg-[#ccc] max-h-[300px]">
              <li
                v-for="ex in filteredExercises"
                :key="ex.videoNo"
                @click="selectExercise(ex)"
                class="bg-[#f00] p-2 hover:bg-gray-100 cursor-pointer"
              >
                <!-- {{ ex.videoNo || "제목없음" }} -->
                {{ ex.title || "제목없음" }}
              </li>
            </ul>

            <!-- 닫기 버튼 -->
            <button @click="isSearch = false" class="w-full mt-4 btn-m-white">
              닫기
            </button>
            <!-- ------- -->
          </div>
        </div>
      </div>
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
import { ref, onMounted, nextTick, watch, reactive, computed } from "vue";
import FlatPickr from "vue-flatpickr-component";
import "flatpickr/dist/flatpickr.css";
import Multiselect from "vue-multiselect";
import axios from "@/axios/index";

const emit = defineEmits(["update-list"]);
const props = defineProps({
  modifyDiary: {
    type: Object,
    Required: true,
  },
  isModify: {
    type: Boolean,
  },
  reset: {
    type: Boolean,
  },
});

// 다이어리 내용 관련 변수
const date = ref("");
const content = ref("");
const diaryNo = ref("");
const isUpdate = ref(false);
isUpdate.value = props.isModify;
const userExercises = reactive([]); // 유저가 추가한 모든 영상
const filteredExercises = reactive([]);
const videoNo = ref("");
// 검색에 따라 필터링된 영상들
// ==========================//
// 다이어리 수정
// ==========================//
const modifyDiary = ref({});
modifyDiary.value = props.modifyDiary;
date.value = modifyDiary.value.regDate;
console.log("modifyDiary +++++++++++ ###",modifyDiary.value);
console.log("modifyDiary +++++++++++ ###",date.value);


// ---------로딩창 관련--------//
const isLoading = ref(false);

// =========================//
// 다이어리 추가 및 영상 검색 기능
// =========================//

// 다이어리 Ex 추가 관련 변수
const title = ref("");
const playNum = ref(1);
const diaryExNoCount = ref(1);
const addedVideoList = ref([]);

// 운동 영상 검색 관련 변수/함수
const isSearch = ref(false);
const openSchModal = () => {
  isSearch.value = true;
  // userVideoList();
  console.log("openSchModal -> userExercises.value");
  console.dir(userExercises.value);
  // filteredExercises.value = userExercises.value;
  console.log("openSchModal -> filteredExercises.value");
  // console.dir(filteredExercises.value);
};

// 사용자가 등록한 운동 영상 리스트 모두 받아옴
async function userVideoList() {
  try {
    const { data } = await axios.get("api-video");
    for (const d of data) {
      filteredExercises.push(d);
      userExercises.push(d);
    }

    // filteredExercises.value = data;
    console.log("userExercises.value");
    // console.dir(userExercises.value);
    // console.dir(userExercises.value[0].title);
  } catch (error) {
    console.error("userExercises 데이터 불러오는 중 오류", error);
    return null;
  }
}
onMounted(() => {
  userVideoList(); // 컴포넌트가 마운트될 때 데이터 로딩
});

// watch(
//   () => filteredExercises.value,
//   (newfilteredExercises) => {
//     console.log("watch 발동");
//     filteredExercises.value = newfilteredExercises;
//   }
// );


// 사용자가 입력할 때마다 호출되는 검색 함수 multiselect @search="handleSearch"
// const handleSearch = (query) => {

//   // 제목을 기준으로 대소문자 구분 없이 필터링
// filteredExercises.value = userExercises.value.filter((exercise) =>
//   exercise.title.toLowerCase().includes(query.toLowerCase())
// );

//   // 쿼리와 일치하는 제목을 필터링
// filteredExercises.value = userExercises
//   .filter((exercise) => exercise.toLowerCase().includes(query.toLowerCase()))
//   .map((exercise) => ({
//     title: exercise.title,
//     videoNo: exercise.videoNo,
//   }));

//   console.log("Filtered Object:", filteredExercises.value[0]);
//   // filteredExercises.value = userExercises.filter((exercise) => {
//   //   title = exercise.title.toLowerCase().includes(query.toLowerCase()),
//   //   videoNo = exercise.videoNo,
//   //   // return {
//   //   //   title: exercise.title.toLowerCase().includes(query.toLowerCase()),
//   //   //   videoNo: exercise.videoNo,
//   //   // };
//   // });
// };

const searchQuery = ref("");
// const selectedExercise = ref("");

const handleSearch = (query) => {
  filteredExercises.splice(0, filteredExercises.length);
  // userVideoList();
  console.log("handleSearch -> userExercises.value");
  console.dir(userExercises);
  console.log("이벤트", query);
  const Ex = userExercises.filter((exercise) =>
    exercise.title.toLowerCase().includes(query.target.value.toLowerCase())
  );
  console.log("handleSearch -> Ex.value");
  console.dir(Ex);
  for (const e of Ex) {
    filteredExercises.push(e);
  }

  // filteredExercises.value = userExercises.value;
  // filteredExercises.value = userExercises.value.filter((exercise) =>
  //   exercise.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  // );

  // filteredExercises.value = userExercises.value.filter((exercise) =>
  //   exercise.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  // );

  // filteredExercises = computed(() =>
  //   userExercises.value.filter((exercise) =>
  //     exercise.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  //   )
  // );
};

const selectExercise = (exercise) => {
  title.value = exercise.title; // 전체 객체 할당
  // searchQuery.value = ""; // 검색어 초기화
  isSearch.value = false; // 모달 닫기
  videoNo.value = exercise.videoNo;
};

// ----------- 운동 영상 추가 ------------- //
const addList = () => {
  if (title.value && playNum.value > 0) {
    addedVideoList.value.push({
      diaryExNo: diaryExNoCount.value++,
      title: title.value,
      videoNo: videoNo.value, // 선택한 영상 번호
      playNum: playNum.value,
    });
    // 필드 초기화
    title.value = "";
    playNum.value = 1;
  } else {
    alert("영상 제목과 진행 횟수를 입력해주세요.");
  }
};

// 모달 방식 관련
const openSch = () => {
  isSearch.value = true;
};
const selectedExercise = ref(null); // 선택된 운동

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
  };
  try {
    const response = await axios.post("api-diary/diary", diary);
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

//============================
// 다이어리 수정 
// ===========================
const updateDiary = async () => {
  console.log("updateDiary => addedVideoList.value");
  console.log(addedVideoList.value);
  const diary = {
    diaryNo: diaryNo.value,
    regDate: date.value,
    content: content.value,
    diaryExList: addedVideoList.value,
  };
  if (!diary.content) {
    alert("운동소감을 입력해주세요.");
    return null;
  }
  try {
    const response = await axios.put(`api-diary/diary/${diary.diaryNo}`, diary);
    emit("update-list");
    alert(date.value + ", 수정 완료 :)");
    console.log("수정 성공", response.data);
  } catch (error) {
    console.error("수정 실패", error);
  }
};

// 날짜 선택 시, 해당 다이어리가 있는지 확인
// -> isUpdate 값이 true 일 경우 수정이 보여지도록, 수정 버튼 클릭시에는 수정 메서드 진행
async function selectDiaryByRegDate() {
  if (!date.value) return null;

  try {
    const { data } = await axios.get(`api-diary/diary/list/${date.value}`);

    // 응답 데이터 유효성 검사
    if (data && typeof data === "object") {
      console.log("API Response:", data);
      return data;
    } else {
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


//============================
// 다이어리 수정 시 불러오는 Data
// ===========================
// userVideoList();
watch(date, async (newDate) => {
  if (newDate) {
    console.log("조회 실행되었습니다.");
    const data = await selectDiaryByRegDate();
    if (data) {
      console.log("###");
      console.log(data);
      // isLoading.value = true;
      // console.log("Retrieved diary data:", data);
      // console.log("data-type : " + typeof data);

      // 현재 유저아이디, 다이어리No. 에 대해 등록된 운동 video_no, play_num 받아오기
      // 다이어리 리스트의 addedVideoList를 다시 받아와서 
      // video_no 받아와서 그걸로 다시 영상들 타이틀 조회
      const diaryExList = data.diaryExList;
      console.log("#####", diaryExList);

      // 영상 정보에 대한 데이터
      if(diaryExList !== null){
        for (const ex of diaryExList) {
          const videoNo = ex.videoNo;
          const response = await axios.get(`api-video/${videoNo}`);
          const data2 = response.data.video;
          console.log("watch data => data2 ");
          console.log(data2);
  
          ex.title = data2.title;
          console.log("watch data => ex.title");
          console.log(ex.title);
          addedVideoList.value.push(ex);
        }
      }

      diaryNo.value = data.diaryNo ?? "";
      content.value = data.content ?? "";

      // // diaryExList 배열 처리
      // if (Array.isArray(data.diaryExList)) {
      //   addedVideoList.value = data.diaryExList.map((item) => ({
      //     diaryExNo: item.diaryExNo,
      //     title: item.title,
      //     videoNo: item.videoNo,
      //     playNum: item.playNum,
      //   }));
      // } else {
      //   addedVideoList.value = [];
      //   isUpdate.value = false;
      //   console.warn("diaryExList is not an array:", data.diaryExList);
      // }
      isUpdate.value = true;
      // 3초 후 로딩 상태 false로 변경
      // setTimeout(() => {
      //   isLoading.value = false;
      // }, 2800);
    } else if (!data && isUpdate) {
      resetForm();
      isUpdate.value = false;
    }
  }
});

// 폼 초기화 함수

// 폼 초기화 함수 - 내용만 초기화

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

watch(
  () => modifyDiary.value,
  async (newModifyDiary) => {
    // userVideoList();
    console.dir("newModifyDiary.diaryNo : " + newModifyDiary.diaryNo);
    if (newModifyDiary) {
      console.log("newModifyDiary 실행되었습니다.");
      console.log(newModifyDiary);
      diaryNo.value = newModifyDiary.diaryNo;
      date.value = newModifyDiary.regDate;
      content.value = newModifyDiary.content;
      // const { data } = await axios.get(`api-diary/diary/exlist/${diaryNo.value}`);
      // newModifyDiary.diaryExList = data;  

     // 등록된 영상정보 세팅
      if(newModifyDiary.diaryExList !== null){
        for (const ex of newModifyDiary.diaryExList) {
          const videoNo = ex.videoNo;
          const response = await axios.get(`api-video/${videoNo}`);
          const data2 = response.data.video;
          console.log("watch data => data2 ");
          console.log(data2);
  
          ex.title = data2.title;
          console.log("watch data => ex.title");
          console.log(ex.title);
          addedVideoList.value.push(ex);
        }
      }

      // // diaryExList 배열 처리
      // if (Array.isArray(newModifyDiary.diaryExList)) {
      //   addedVideoList.value = newModifyDiary.diaryExList.map((item) => ({
      //     diaryExNo: item.diaryExNo,
      //     title: item.title,
      //     playNum: item.playNum,
      //   }));
      // } else {
      //   addedVideoList.value = [];
      //   console.warn("diaryExList is not an array:", date.diaryExList);
      // }

      isUpdate.value = true;
    }
  },
  { immediate: true, deep: true }
);

const resetFormAll = () => {
  date.value = "";
  diaryNo.value = "";
  content.value = "";
  addedVideoList.value = [];
  isUpdate.value = false;
};

const resetForm = () => {
  content.value = "";
  addedVideoList.value = [];
};

if (props.reset) {
  resetFormAll();
}
watch(
  () => props.reset,
  (newReset) => {
    console.log("reset###");
    if (newReset) {
      resetFormAll();
    }
  }
  // { immediate: true }
);
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
.multiselect__content-wrapper {
  width: 400px !important; /* 원하는 너비로 설정 */
}
.multiselect__content {
  width: 400px !important;
}
</style>
