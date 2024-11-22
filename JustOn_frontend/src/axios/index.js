import axios from 'axios';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const apiClient = axios.create({
    baseURL: 'http://192.168.210.75:8080/', // API 서버 주소
    headers: {
        'Content-Type': 'application/json',
    },
})

const router = useRouter();

apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('jwt'); // JWT 토큰을 localStorage에서 가져옴
  if (token) {
      // console.log(token);
      config.headers.Authorization = `Bearer ${token}`; // Authorization 헤더에 토큰 추가
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

apiClient.interceptors.response.use(
  response => response,
  error => {
    if(error.response.status === 401 || error.response.status === 403) {
      console.log("token Expired");
      console.log(error);
      const userStore = useUserStore();
      userStore.logout();
      // router.push({name:'login'});
    }
  }
);

export default apiClient;