import { ref } from "vue";
import { defineStore } from "pinia";

export const useSwitchStore = defineStore("switch", () => {
    const isOff = ref(false);

    const switching = () => {
        isOff.value = !isOff.value;
    }

    const isKicked = ref(false);

    return {isOff, switching, isKicked}
})