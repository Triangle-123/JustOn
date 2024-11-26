import "./assets/main.css";

import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from './router'
import vClickOutside from "click-outside-vue3";
import Toast, { POSITION } from "vue-toastification";
import "vue-toastification/dist/index.css";
import VueSweetalert2 from "vue-sweetalert2";
import 'sweetalert2/dist/sweetalert2.min.css';
const app = createApp(App);
const pinia = createPinia();
app.use(pinia);
app.use(vClickOutside);
app.use(Toast);
app.use(router);
app.use(VueSweetalert2);
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
