import {defineStore} from "pinia";
import {computed, ref} from "vue";
import {DroneRepo, getDroneRepos} from "@/js/api/dronetrics.api";

export const useDroneReposStore = defineStore("dronetrics/repos", () => {
    const droneRepos = ref<DroneRepo[]>([]);

    const sortedDroneRepos = computed<DroneRepo[]>(() => {
        return [...droneRepos.value]
            .filter(d => d.buildNumber > 1)
            .sort((a, b) => b.lastestBuild.lastUpdated - a.lastestBuild.lastUpdated);
    });

    async function fetchDroneRepos() {
        droneRepos.value = await getDroneRepos();
    }

    return {
        droneRepos,
        fetchDroneRepos,
        sortedDroneRepos,
    };
});
