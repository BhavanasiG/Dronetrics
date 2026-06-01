import {createApp} from "vue";
import PrimeVue from "primevue/config";
import Aura from "@primevue/themes/aura";
import "primeicons/primeicons.css";
import DronetricsPage from "@/pages/DronetricsPage.vue";
import {setDynamicFavicon} from "@/branding/logo.js";
import {createPinia} from "pinia";


setDynamicFavicon();
const app = createApp(DronetricsPage);

app.use(PrimeVue, {
  theme: {
    preset: Aura,
    options: {
      darkModeSelector: '.p-dark',
    }
  },
  ripple: true,
});

// TODO: Implement Pinia and Router
const pinia = createPinia();
app.use(pinia);
// app.use(router);
app.mount("#dronetrics-app");


