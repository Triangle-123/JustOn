import { ref } from "vue";
import { defineStore } from "pinia";

export const useApiStore = defineStore("api", () => {
    const isApiLoaded = ref(false);

    return { isApiLoaded }
})