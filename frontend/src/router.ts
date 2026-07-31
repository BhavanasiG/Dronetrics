import { createRouter, createWebHistory } from "vue-router";
import BuildPage from "@/pages/BuildPage.vue";
import DronetricsPage from "./pages/DronetricsPage.vue";

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", name: "repos", component: DronetricsPage },
    { path: "/builds", name: "builds", component: BuildPage },
  ],
});
