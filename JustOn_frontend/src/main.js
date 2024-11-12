import "./assets/main.css";

import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
// import router from './router'

const app = createApp(App);

app.use(createPinia());
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
