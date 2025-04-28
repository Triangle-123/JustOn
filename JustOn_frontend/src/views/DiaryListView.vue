<template>
  <div class="grid-1fr-1fr">
    <div class="diaryList content-wrap h-[100%]">
      <div class="content-box">
        <!-- 콘텐츠 제목 -->
        <div
          class="con-title flex justify-between pb-2 mb-6 border-solid border-b-[1px] border-gray-300"
        >
          <div class="flex gap-2 items-end">
            <h2>
              <i class="bi bi-arrow-right-circle-fill mr-3"></i>운동 다이어리
            </h2>
            <span class="mb-1"
              >매일매일 간편하게 기록하는 나만의 운동 일기</span
            >
          </div>
        </div>

        <div class="content flex flex-col">
          <!-- 날짜 조회 -->
          <div class="date-form-box mb-5 flex justify-between">
            <div class="flex items-center">
              <span class="font-bold text-lg mr-6">날짜 조회</span>
              <flat-pickr
                v-model="date"
                placeholder="조회할 다이어리 날짜 선택"
                class="date-input mr-2 w-[50%] h-[52px] border-solid border-2 border-gray-200 rounded-[16px] px-4 py-2"
              ></flat-pickr>
              <button @click="selectDiaryByRegDate" class="btn-m-white">
                조회하기
              </button>
            </div>

            <button @click="showRegisterForm" class="btn-m-black" type="button">
              <i class="bi bi-pencil-square"></i> 기록하기
            </button>
          </div>

          <!-- 다이어리 리스트 -->
          <article class="diary-list-warp mb-8">
            <table class="border-collapse w-[100%]">
              <tbody id="diary-list">
                <tr class="text-lg border-solid border-b-[1px] border-gray-200">
                  <th class="w-[25%] p-3 font-bold">날짜</th>
                  <th class="w-[75%] p-3 font-bold">내용</th>
                </tr>
                <!-- 리스트 내용 반복 -->
                <tr
                  class="hover:bg-[#f6f6f6] cursor-pointer"
                  v-for="diary in diaryList"
                  :key="diary.diaryNo"
                  @click="diaryDetailShow(diary)"
                >
                  <td class="p-3 w-[25%] text-center">
                    <span class="diary-date">{{ diary.regDate }}</span>
                  </td>
                  <td class="p-3 w-[75%]">
                    <span class="diary-title block ellipsis w-[450px]">{{
                      diary.content
                    }}</span>
                  </td>
                </tr>
                <tr v-if="diaryList.length === 0">
                  <td class="p-4 text-center text-gray-400" colspan="2">
                    작성된 다이어리가 없습니다.
                  </td>
                </tr>
              </tbody>
            </table>
          </article>

          <!-- 페이징 영역 : 임시, 페이징 적용 필요 -->
          <div
            v-show="diaryList.length !== 0"
            class="paging-controls paging flex justify-center gap-5"
          >
            <button
              v-if="pr.prev"
              @click="changePage(pr.page - 1)"
              class="btn-prev"
            >
              <i class="bi bi-chevron-left"></i>
            </button>

            <button
              v-for="page in pageNumbers"
              :key="page"
              @click="changePage(page)"
              :class="[
                'btn-page',
                { active: page === pr.page },
                { 'font-bold': page == curPage },
              ]"
            >
              {{ page }}
            </button>

            <button
              v-if="pr.next"
              @click="changePage(pr.page + 1)"
              class="btn-next"
            >
              <i class="bi bi-chevron-right"></i>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 등록/수정  -->
    <DiaryRegistForm
      v-if="isShowRegisterForm"
      @update-list="updateList"
      :modifyDiary="modifyDiary"
      :isModify="isModify"
      ref="diaryRegistFormRef"
      :reset="reset"
      @mounted="onChildMounted"
    />
    <!-- 상세 -->
    <DiaryDetail
      v-if="isDiaryDetailShow"
      @closeDetail="closeDetail"
      @openModifyDiary="openModifyDiary"
      @update-list="updateList"
      :diaryForDetail="selectedDiary"
    />
  </div>
</template>

<script setup>
import { onMounted, ref, computed, onBeforeMount } from "vue";
import axios from "@/axios/index";
import DiaryRegistForm from "@/components/DiaryRegistForm.vue";
import DiaryDetail from "@/components/DiaryDetail.vue";

import FlatPickr from "vue-flatpickr-component";
import "flatpickr/dist/flatpickr.css";

const diaryList = ref([]);
const list = ref([]);
const pr = ref({
  page: 1,
  beginPage: 1,
  endPage: 5,
  lastPage: 10,
  prev: false,
  next: true,
});

async function getUserDiaryList() {
  const { data } = await axios.get(`api-diary/diary`);

  if (data.list !== undefined) {
    if (data.list.length > 0) {
      diaryList.value = data.list.sort(
        (a, b) => new Date(b.regDate) - new Date(a.regDate)
      );
    } else if (data.list.length === 1) {
      diaryList.value = data.list;
    }
    pr.value = data.pr;
  } else {
    diaryList.value = [];
  }
}
getUserDiaryList();

const updateList = () => {
  getUserDiaryList();
};

// =======================
// 페이징
// =======================
const curPage = ref(1);

// 페이징을 위한 페이지 번호 배열 계산
const pageNumbers = computed(() => {
  const pages = [];
  for (let i = pr.value.beginPage; i <= pr.value.endPage; i++) {
    pages.push(i);
  }
  return pages;
});

// 페이지 변경 함수
const changePage = async (page) => {
  // 페이지 변경 시 새로운 데이터를 불러옴
  try {
    const { data } = await axios.get(`api-diary/diary?page=${page}`);

    if (data.list !== undefined) {
      if (data.list.length > 1) {
        diaryList.value = data.list.sort(
          (a, b) => new Date(b.regDate) - new Date(a.regDate)
        );
      } else if (data.list.length === 1) {
        diaryList.value = data.list;
      }
      pr.value = data.pr;
    } else {
      diaryList.value = [];
    }
    curPage.value = page;
  } catch (error) {
    console.error("페이지 불러오기 실패", error);
  }
};

// 날짜로 조회하기
const date = ref("");
async function selectDiaryByRegDate() {
  if (date.value) {
    pr.value = {};
    try {
      console.log("실행됨");
      const { data } = await axios.get("api-diary/diary/list/" + date.value);
      diaryList.value.length = 0;
      console.log("날짜 조회 다이어리 : ", diaryList.value);
      if (data) {
        diaryList.value.push(data);
      }
      console.log("날짜 조회 다이어리 : ", diaryList.value);
      console.log("날짜 조회 다이어리 내용 : ", diaryList.value.content);
      console.log(diaryList.value);
    } catch (error) {
      console.error("데이터 불러오는 중 오류", error);
    }
  } else {
    getUserDiaryList();
  }
}

// =======================
// 다이어리 상세
// =======================
const selectedDiary = ref({});
const isDiaryDetailShow = ref(false);

const diaryDetailShow = async (diary) => {
  formState.value = "";
  selectedDiary.value = diary;
  modifyDiary.value = diary;
  isDiaryDetailShow.value = true;
  isShowRegisterForm.value = false;
  reset.value = false;
};

// 다이어리 기록 창 (기록하기 클릭 시)
const isShowRegisterForm = ref(false);
const isModify = ref(false);
const reset = ref(false);
// Detail => RegistForm
const formState = ref();
const showRegisterForm = () => {
  if (!formState.value) {
    // 초기값일 때, 일반 기본 설정. regist 상태 변화
    isDiaryDetailShow.value = false; // 상세 꺼지고
    isShowRegisterForm.value = true; // 등록 보이고
    isModify.value = false; // 등록 상태
    formState.value = "regist";
  } else if (formState.value === "regist") {
    // regist 상태일 때
    isShowRegisterForm.value = false;
    formState.value = "";
  }
  // reset.value = true;
  // this.$refs.diaryRegistFormRef.handleAction();
};

// closeDetail
const closeDetail = () => {
  selectedDiary.value = {};
  isDiaryDetailShow.value = false;
};

// openModifyDiary
const modifyDiary = ref({});
const openModifyDiary = (diary) => {
  modifyDiary.value = diary;
  isModify.value = true;
  isShowRegisterForm.value = true;
  isDiaryDetailShow.value = false;
};
</script>

<style scoped>
.content {
  height: 100%;
}
</style>
