<template>
    <div>
        <form @submit.prevent="login">
            <label>아이디</label>
            <input type="text" v-model="userName"/>
            <label>비밀번호</label>
            <input type="password" v-model="password"/>
            <button>로그인</button>
        </form>        
    </div>
</template>
    
<script setup>
import {ref} from 'vue';
import axios from '@/axios/index.js';

const userName = ref("");
const password = ref("");

const login = async () => {
    const response = await axios.post("api-user/login", {
        userName : userName.value,
        password : password.value,
    })

    console.dir(response);
    localStorage.setItem('jwt', response.headers.authorization.split(' ')[1]);
    console.log(localStorage.getItem('jwt'));
}

</script>
    
<style scoped>
    
</style>