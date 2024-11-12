<template>
  <div class="content-wrap h-[100%]">
    <!-- 콘텐츠 제목 -->
    <div
      class="con-title flex justify-between pb-2 mb-6 border-solid border-b-[1px] border-gray-300"
    >
      <div class="flex gap-2 items-end">
        <h2>운동 다이어리</h2>
        <span class="mb-1">매일매일 간편하게 기록하는 나만의 운동 일기</span>
      </div>
    </div>

    <div class="content flex flex-col">
      <!-- 날짜 조회 -->
      <div class="date-form-box mb-5 flex justify-between">
        <div class="flex items-center">
          <span class="font-bold text-lg mr-6">날짜 조회</span>
          <flat-pickr
            v-model="date"
            :config="config"
            placeholder="조회할 다이어리 날짜 선택"
            class="date-input mr-2 w-[50%] h-[52px] border-solid border-2 border-gray-200 rounded-[16px] px-4 py-2"
          ></flat-pickr>
          <button @click="selectDiaryByRegDate" class="btn-m-white">
            조회하기
          </button>
        </div>

        <button @click="showRegisterForm" class="btn-m-black">
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
      <div class="paging flex justify-center gap-5">
        <button><i class="bi bi-chevron-left"></i></button>
        <button>1</button>
        <button>2</button>
        <button>3</button>
        <button>4</button>
        <button>5</button>
        <button><i class="bi bi-chevron-right"></i></button>
      </div>
    </div>

    <!-- 
    diarySearch : private int page,	private int listSize;

    params.put("diarySearch", diarySearch);
		params.put("userId", userId);
    result.put("list", diaryDao.selectAllDiary(params));
		result.put("pr", new PageResult(
					diarySearch.getPage(),
					diaryDao.selectAllDiaryCount(userId),
					diarySearch.getListSize()
			)

      ### PageResult 
      private int page; // 현재 요청된 페이지 번호
      private int beginPage; // 목록 하단의 페이지 시작번호
      private int endPage; // 목록 하단의 페이지 종료번호
      private int lastPage; // 목록 하단의 페이지 마지막 페이지
      private boolean prev; // 이전 버튼 표시 여부 판단
      private boolean next; // 다음 버튼 표시 여부 판단
		);
    -->
  </div>

  <DiaryRegistForm v-if="isShowRegisterForm" @update-list="updateList" />
  <DiaryDetail
    @closeDetail="closeDetail"
    v-if="selectedDiary"
    :diary="selectedDiary"
  />
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import DiaryRegistForm from "./DiaryRegistForm.vue";
import DiaryDetail from "./DiaryDetail.vue";

import FlatPickr from "vue-flatpickr-component";
import "flatpickr/dist/flatpickr.css";

// const diaryList = ref([
//   {
//     title: "다이어리 제목입니다1",
//     content: "다이어리 내용입니다1",
//     reg_date: "2024-11-01",
//     user_id: "ssafy",
//   },
//   {
//     title: "다이어리 제목입니다2",
//     content: "다이어리 내용입니다2",
//     reg_date: "2024-11-01",
//     user_id: "ssafy",
//   },
//   {
//     title: "다이어리 제목입니다3",
//     content: "다이어리 내용입니다3",
//     reg_date: "2024-11-01",
//     user_id: "ssafy",
//   },
//   {
//     title: "다이어리 제목입니다4",
//     content: "다이어리 내용입니다4",
//     reg_date: "2024-11-01",
//     user_id: "ssafy",
//   },
//   {
//     title: "다이어리 제목입니다5",
//     content: "다이어리 내용입니다5",
//     reg_date: "2024-11-01",
//     user_id: "ssafy",
//   },
//   {
//     title: "다이어리 제목입니다6",
//     content: "다이어리 내용입니다6",
//     reg_date: "2024-11-01",
//     user_id: "ssafy",
//   },
//   {
//     title: "다이어리 제목입니다7",
//     content: "다이어리 내용입니다7",
//     reg_date: "2024-11-01",
//     user_id: "ssafy",
//   },
//   {
//     title: "다이어리 제목입니다8",
//     content: "다이어리 내용입니다8",
//     reg_date: "2024-11-01",
//     user_id: "ssafy",
//   },
//   {
//     title: "다이어리 제목입니다9",
//     content: "다이어리 내용입니다9",
//     reg_date: "2024-11-01",
//     user_id: "ssafy",
//   },
//   {
//     title: "다이어리 제목입니다10",
//     content: "다이어리 내용입니다10",
//     reg_date: "2024-11-01",
//     user_id: "ssafy",
//   },
// ]);

const diaryList = ref([]);

async function getUserDiaryList() {
  const { data } = await axios.get("http://localhost:8080/api-diary/diary");
  // diaryList.value = data.list;
  diaryList.value = data.list.sort(
    (a, b) => new Date(b.regDate) - new Date(a.regDate)
  );
  console.dir(diaryList.value);
}
getUserDiaryList();

const updateList = () => {
  getUserDiaryList();
};

// 페이징 구현
const curPage = ref(1);

// 날짜로 조회하기
const date = ref("");
async function selectDiaryByRegDate() {
  if (date.value) {
    try {
      console.log("실행됨");
      const { data } = await axios.get(
        "http://localhost:8080/api-diary/diary/list/" + date.value
      );
      diaryList.value = data;
    } catch (error) {
      console.error("데이터 불러오는 중 오류", error);
    }
  } else {
    getUserDiaryList();
  }
}

// 다이어리 상세
const selectedDiary = ref({});
const diaryDetailShow = (diary) => {
  selectedDiary.value = diary;
  isShowRegisterForm.value = false;
};

// 다이어리 기록 창
const isShowRegisterForm = ref(false);
const showRegisterForm = () => {
  isShowRegisterForm.value = !isShowRegisterForm.value;
  selectedDiary.value = null;
};

// closeDetail
const closeDetail = () => {
  selectedDiary.value = null;
};
</script>

<style scoped></style>
