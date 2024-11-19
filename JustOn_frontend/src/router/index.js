import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AddContentView from '@/views/AddContentView.vue'
import DiaryListView from '@/views/DiaryListView.vue'
import LoginView from '@/views/LoginView.vue'
import { useUserStore } from '@/stores/user'
import SignUpView from '@/views/SignUpView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/addcontent',
      name: 'addContent',
      component: AddContentView,
      beforeEnter: async (to, from) => {
        const userStore = useUserStore();
        await userStore.getUser();
        console.log(userStore.user);
        if(!userStore.user) {
          return {name: 'login'};
        }
        return true;
      }
    },
    {
      path: '/diaryList',
      name: 'diaryList',
      component: DiaryListView,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignUpView,
    }
  ],
})

export default router
