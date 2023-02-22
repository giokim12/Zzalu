import { createApp } from 'vue';
import App from './App.vue';
import './css/common.css';
import router from './router.js';
import store from '@/store';
import Vue3TouchEvents from "vue3-touch-events";
// import Vue from 'vue'
import VueApexCharts from 'vue3-apexcharts';
import VueSweetAlert2 from "vue-sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";

import { library } from '@fortawesome/fontawesome-svg-core';

/* import font awesome icon component */
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

/* import specific icons */
// import { faHouse } from '@fortawesome/free-solid-svg-icons';

import { fas } from '@fortawesome/free-solid-svg-icons';
import { fab } from '@fortawesome/free-brands-svg-icons';
/* add icons to the library */
// library.add(faHouse);


library.add(fas);
library.add(fab);
import { far } from '@fortawesome/free-regular-svg-icons';
library.add(far);

const app = createApp(App);
app.use(router);
app.use(store);
app.component('font-awesome-icon', FontAwesomeIcon);
app.mount('#app');
app.use(Vue3TouchEvents)
app.use(VueApexCharts)
app.use(VueSweetAlert2);