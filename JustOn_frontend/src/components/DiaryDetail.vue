<template>
  <div class="content-wrap flex flex-col h-[100%]">
    <!-- 콘텐츠 제목 -->
    <div
      class="con-title flex justify-between pb-2 mb-6 border-solid border-b-[1px] border-gray-300"
    >
      <div class="flex gap-2 items-end">
        <h2><i class="bi bi-chevron-right mr-2"></i>나의 운동 기록</h2>
      </div>
    </div>

    <div class="content flex flex-col justify-between h-[100%]">
      <!-- 다이어리 내용 -->
      <div>
        <h3 class="font-bold text-xl mr-6 mb-5">
          <span class="bg-[var(--juston-black)] text-white px-2 py-1 mr-1"
            ><strong class="font-[900] juston-gradient-text"
              >DATE &nbsp;{{ diaryForDetail.regDate }}</strong
            ></span
          >
        </h3>

        <div class="mb-8">
          <h4 class="font-bold text-lg mb-2">운동 소감</h4>
          <p
            class="w-[100%] h-[200px] border-solid border-2 border-gray-200 rounded-[16px] px-4 py-2 mb-6"
          >
            <span class="text-gray-300" v-if="diaryForDetail.content === ''"
              >입력된 소감이 없습니다.</span
            >
            {{ diaryForDetail.content }}
          </p>
        </div>

        <div class="border-solid border-b-[1px] border-gray-200 mb-5">
          <h4 class="font-bold text-lg mb-2">진행한 운동 영상</h4>
          <table class="border-collapse w-[100%] h-[260px]">
            <thead>
              <tr
                class="text-l border-solid border-b-[1px] border-t-[1px] border-gray-200"
              >
                <th class="w-[75%] font-bold p-2">진행한 영상</th>
                <th class="w-[25%] font-bold">진행 횟수</th>
              </tr>
            </thead>
            <tbody>
              <tr
                class="border-solid border-r-[1px] border-gray-200 p-1"
                v-for="ex in diaryForDetail.diaryExList"
              >
                <td>{{ ex.title }}</td>
                <td>{{ ex.playNum }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div class="flex justify-center">
        <button @click="openModifyDiary" class="btn-m-black w-[100px] mr-3">
          수정
        </button>
        <button @click="deleteDiary" class="btn-m-white flex items-center">
          <i class="bi bi-trash3 text-[18px] mr-1"></i>삭제
        </button>
      </div>
      <!-- 내용시작 -->
      <!-- 날짜 -->
    </div>
  </div>
</template>

<script setup>
import { watch } from "vue";
import axios from "@/axios/index";
const emit = defineEmits(["closeDetail", "openModifyDiary", "updateList"]);

const props = defineProps({
  diaryForDetail: {
    type: Object,
    Required: true,
  },
});
const diary = props.diaryForDetail;

async function deleteDiary() {
  if (diary.diaryNo) {
    try {
      if (confirm("삭제하시겠습니까?")) {
        await axios.delete(
          "api-diary/diary/" + diary.diaryNo
        );
        console.log("삭제완료");
        emit("closeDetail");
        emit("updateList");
      } else {
        return null;
      }
    } catch (error) {
      console.error("삭제 오류", error);
    }
  } else {
  }
}

const openModifyDiary = () => {
  emit("openModifyDiary", diary);
};

// 다이어리 변화 감지
// watch(
//   diary,
//   (newValue) => {
//     diary.value = newValue;
//     console.log("반영완료");
//   },
//   { immediate: true }
// );
</script>

<style scoped></style>
