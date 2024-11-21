import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import AddContentView from "@/views/AddContentView.vue";
import DiaryListView from "@/views/DiaryListView.vue";
import LoginView from "@/views/LoginView.vue";
import { useUserStore } from "@/stores/user";
import SignUpView from "@/views/SignUpView.vue";
import MyPageView from "@/views/MyPageView.vue";
import LoggedinHomeView from "@/views/LoggedinHomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/addcontent",
      name: "addContent",
      component: AddContentView,
    },
    {
      path: "/diaryList",
      name: "diaryList",
      component: DiaryListView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/signup",
      name: "signup",
      component: SignUpView,
    },
    {
      path: "/loggedinHome",
      name: "loggedinHome",
      component: LoggedinHomeView,
    },
    {
      path: "/mypage",
      name: "mypage",
      component: MyPageView,
    },
  ],
});

router.beforeEach(async (to, from) => {
  const allowedRoutes = ["login", "home", "signup"]; // 허용할 라우트

  const userStore = useUserStore();
  await userStore.getUser();
  // console.log(userStore.user);
  if (!userStore.user) {
    if (allowedRoutes.includes(to.name)) {
      return true;
    }
    return { name: "login" };
  }
  return true;
});

export default router;
