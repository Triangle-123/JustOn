import { createApp } from 'vue'
import App from './App.vue'
import vClickOutside from 'click-outside-vue3'
import Toast, { POSITION } from "vue-toastification";
import "vue-toastification/dist/index.css";
const app = createApp(App);
app.use(vClickOutside);
app.use(Toast);

app.mount('#app');
