<template>
    <div>
        <form @submit.prevent="login">
            <label>아이디</label>
            <input type="text" v-model="userName" />
            <label>비밀번호</label>
            <input type="password" v-model="password" />
            <button v-if="!userStore.isLoggedin">로그인</button>
            <button type="button" @click="userStore.logout" v-if="userStore.isLoggedin">로그아웃</button> 
        </form>
    </div>
    <RouterLink :to="{name : 'addContent'}">컨텐츠 등록</RouterLink>
    <button @click="push">컨텐츠 등록</button>
</template>

<script setup>
import { ref } from 'vue';
import axios from '@/axios/index.js';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';
const userName = ref("");
const password = ref("");
const userStore = useUserStore();
const router = useRouter();

const login = async () => {
    const response = await axios.post("api-user/login", {
        userName: userName.value,
        password: password.value,
    })

    // console.dir(response);
    if (response.status === 200) {
        localStorage.setItem('jwt', response.headers.authorization.split(' ')[1]);
        const token = localStorage.getItem('jwt');
        if (token) {
            try {
                // 사용자 정보를 서버에서 가져오기
                const resInfo = await axios.get('api-user/userInfo', {
                    headers: { Authorization: `Bearer ${token}` },
                });

                console.dir(resInfo);
                if (resInfo.status === 200) {
                    const userData = resInfo.data;
                    userStore.setUser(userData); // 사용자 정보 저장
                    router.push({name: 'home'});
                } else {
                    userStore.logout(); // 인증 실패 시 로그아웃 처리
                }
            } catch (error) {
                console.error(error);
                userStore.logout();
            }
        }

    }
    // console.log(localStorage.getItem('jwt'));
}
const push = () => {
    router.push({name: 'addContent'});
}
</script>

<style scoped></style>