import "./assets/main.css";

import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
// import router from './router'
import vClickOutside from "click-outside-vue3";
import Toast, { POSITION } from "vue-toastification";
import "vue-toastification/dist/index.css";
const app = createApp(App);

app.use(createPinia());
app.use(vClickOutside);
app.use(Toast);
// app.use(router);

app.mount("#app");

// 스크롤바
import { OverlayScrollbars } from "overlayscrollbars";
import "overlayscrollbars/styles/overlayscrollbars.css";

const options = {
  className: "os-theme-dark",
  resize: "both",
  scrollbars: {
    visibility: "auto",
    autoHide: "move",
    autoHideDelay: 800,
  },
};

OverlayScrollbars(document.querySelector(".my-scrollbar"), options);
