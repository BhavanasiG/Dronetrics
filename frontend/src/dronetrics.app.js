import { createApp } from "vue";
import PrimeVue from "primevue/config";
import Aura from "@primevue/themes/aura";
import "primeicons/primeicons.css";
import App from "./App.vue";
import { router } from "./router.ts";
import { setDynamicFavicon } from "./branding/logo.js";
import { createPinia } from "pinia";

setDynamicFavicon();
const app = createApp(App);

app.use(PrimeVue, {
  theme: {
    preset: Aura,
    options: {
      darkModeSelector: ".p-dark",
    },
  },
  ripple: true,
});

const pinia = createPinia();
app.use(pinia);
app.use(router);
app.mount("#dronetrics-app");
