import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080/', // API 서버 주소
    headers: {
        'Content-Type': 'application/json',
    },
})

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
  
export default apiClient;