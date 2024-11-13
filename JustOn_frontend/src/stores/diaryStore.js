import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useDiaryStore = defineStore("diary", () => {
  const diaryNo = ref("");
  const content = ref("");
  const addedVideoList = ref([]);
  const isUpdate = ref(false);

  return {
    diaryNo,
    content,
    addedVideoList,
    isUpdate,
    resetFormAll,
    resetForm,
  };
});
