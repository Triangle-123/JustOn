import { ref } from "vue";
import { defineStore } from "pinia";

export const useSwitchStore = defineStore("switch", () => {
    const isOff = ref(true);

    const switching = () => {
        isOff.value = !isOff.value;
    }

    return {isOff, switching}
})