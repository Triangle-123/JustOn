<template>
    <div class="fixed right-10 bottom-[305px]">
        <button class="p-4 w-[100px] h-[100px] bg-[var(--juston-black)] rounded-[50%] text-xl font-bold text-white"
            @click="isCustomOpen = !isCustomOpen">
            배경색 변경
    </button>
    </div>

    <div v-if="isCustomOpen"
        class="modal-overlay z-[1000] flex justify-center items-center bg-[rgba(0,0,0,0.6)] fixed left-0 top-0 w-full h-full">
        <!--  -->
        <div class="flex justify-center items-center">
            <div class="bottom-[150p] bg-[#f7f7f7] w-[400px] py-12 px-8 rounded-[16px] flex justify-center items-center flex-col"
                v-click-outside="closeModal">
                <span class="px-6 pb-2 mr-1 self-center inline-block"><strong class="text-4xl font-[800]">배경색
                        변경</strong></span>
                <div class="flex flex-row justify-around">
                    <div class="mt-3 mb-0 mx-3">
                        <span class="block text-xl font-bold">색상 1</span>
                        <input type="color" v-model="color1" @change="applyGradient" />
                    </div>
                    <div class="mt-3 mb-0 mx-3">
                        <span class="block text-xl font-bold">색상 2</span>
                        <input type="color" v-model="color2" @change="applyGradient" />
                    </div>
                    <div class="mt-3 mb-0 mx-3">
                        <span class="block text-xl font-bold">색상 3</span>
                        <input type="color" v-model="color3" @change="applyGradient" />
                    </div>
                </div>

                <div class="flex flex-row">
                    <div class="flex flex-col justify-center items-center px-3 border-solid border-r border-gray-500">
                        <span>현재 색상</span>
                        <div class="w-[100px] h-[80px] before-bg"></div>
                    </div>
                    <div class="flex flex-col justify-center items-center px-3">
                        <span>변경 후 색상</span>
                        <div class="w-[100px] h-[80px] after-bg"></div>
                    </div>
                </div>

                <div class="flex flex-row mt-6">
                    <button class="mx-6" @click="changeColor">변경</button>
                    <button class="mx-6" @click="closeModal">취소</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from '@/axios/index';
import { useUserStore } from '@/stores/user';

const userStore = useUserStore();
const isCustomOpen = ref(false);
const color1 = ref('#ffd7dd');
const color2 = ref('#e6cde8');
const color3 = ref('#c29bd8');
const closeModal = () => {
    isCustomOpen.value = false;
}

const setCurGradient = () => {
    if(userStore.user.color) {
        const color = userStore.user.color;
        color1.value = color.split(".")[0];
        color2.value = color.split(".")[1];
        color3.value = color.split(".")[2];
    
        document.documentElement.style.setProperty('--bf-gradient-color1', color1.value);
        document.documentElement.style.setProperty('--bf-gradient-color2', color2.value);
        document.documentElement.style.setProperty('--bf-gradient-color3', color3.value);
        document.documentElement.style.setProperty('--after-gradient-color1', color1.value);
        document.documentElement.style.setProperty('--after-gradient-color2', color2.value);
        document.documentElement.style.setProperty('--after-gradient-color3', color3.value);
    }
}

setCurGradient();

// 그라데이션 적용 함수
const applyGradient = () => {
    document.documentElement.style.setProperty('--after-gradient-color1', color1.value);
    document.documentElement.style.setProperty('--after-gradient-color2', color2.value);
    document.documentElement.style.setProperty('--after-gradient-color3', color3.value);
};

const changeColor = async () => {
    try {
        await axios.put('api-user/color', {
            color : color1.value + "." + color2.value + "." + color3.value
        });
        document.documentElement.style.setProperty('--juston-gradient-1', `linear-gradient(to right, ${color1.value}, ${color2.value}, ${color3.value})`);
        closeModal();
    } catch (error) {
        console.error(error);
    }
}
</script>

<style>
:root {
    --bg-gradient-color1: #ffd7dd;
    --bg-gradient-color2: #e6cde8;
    --bg-gradient-color3: #c29bd8;

    --bf-gradient-color1: #ffd7dd;
    --bf-gradient-color2: #e6cde8;
    --bf-gradient-color3: #c29bd8;

    --after-gradient-color1: #ffd7dd;
    --after-gradient-color2: #e6cde8;
    --after-gradient-color3: #c29bd8;
}

.before-bg {
    background: linear-gradient(90deg,
            var(--bf-gradient-color1),
            var(--bf-gradient-color2),
            var(--bf-gradient-color3));
}

.after-bg {
    background: linear-gradient(90deg,
            var(--after-gradient-color1),
            var(--after-gradient-color2),
            var(--after-gradient-color3));
}
</style>