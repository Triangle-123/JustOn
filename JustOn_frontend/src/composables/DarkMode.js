import { ref, onMounted } from "vue";

// 다크 모드 상태를 관리하는 ref
const isDarkMode = ref(false);

// 다크 모드를 토글하는 함수
const toggleDarkMode = () => {
  console.log("dark");
  isDarkMode.value = !isDarkMode.value;
  document.documentElement.classList.toggle("dark", isDarkMode.value);
};

// 초기 다크 모드 설정 로직
onMounted(() => {
  // 사용자의 시스템 다크 모드 설정을 가져옵니다.
  const prefersDark = window.matchMedia("(prefers-color-scheme: dark)").matches;
  isDarkMode.value = prefersDark;
  document.documentElement.classList.toggle("dark", isDarkMode.value);
});

export { isDarkMode, toggleDarkMode };
