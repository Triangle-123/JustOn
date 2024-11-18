import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AddContentView from '@/views/AddContentView.vue'
import DiaryListView from '@/views/DiaryListView.vue'

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
    },
    {
      path: '/diaryList',
      name: 'diaryList',
      component: DiaryListView,
    },
  ],
})

export default router
