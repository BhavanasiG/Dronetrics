import {createRouter, createWebHistory} from "vue-router";
import DronetricsPage from "./pages/DronetricsPage.vue";
import BuildPage from "@/pages/BuildPage.vue";

export const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: "/", name: "repos", component: DronetricsPage},
        {path: "/builds", name: "builds", component: BuildPage},
    ],
});
