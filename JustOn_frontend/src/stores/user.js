import {ref, computed} from 'vue';
import { defineStore } from 'pinia';
import axios from "@/axios/index";

export const useUserStore = defineStore("user", () => {
    const user = ref(null);

    const setUser = (userInfo) => {
        user.value = userInfo;
    }

    const logout = () => {
        setUser(null);
        localStorage.removeItem('jwt');
    }

    const isLoggedin = computed(() => {
        return user.value ? true : false;
    })

    const getUser = async () => {
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
              setUser(userData); // 사용자 정보 저장
            } else {
              logout(); // 인증 실패 시 로그아웃 처리
            }
          } catch (error) {
            console.error(error);
            logout();
          }
        }
      }
    return {user, setUser, logout, isLoggedin, getUser};
})