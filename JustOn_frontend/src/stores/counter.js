import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useCounterStore = defineStore("counter", () => {
  const count = ref(0);
  const doubleCount = computed(() => count.value * 2);
  function increment() {
    count.value++;
  }
  const resetForm = () => {
    diaryNo.value = "";
    content.value = "";
    addedVideoList.value = [];
    isUpdate.value = false;
  };

  return { count, doubleCount, increment, resetForm };
});
