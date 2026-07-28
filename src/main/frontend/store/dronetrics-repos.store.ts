import {defineStore} from "pinia";
import {computed, ref} from "vue";
import {DroneRepo, getDroneRepos} from "@/js/api/dronetrics.api";

export type DroneRepoType = 'ecase' | 'digital';

export const useDroneReposStore = defineStore("dronetrics/repos", () => {
    const droneRepos = ref<DroneRepo[]>([]);

    const sortedDroneRepos = computed<DroneRepo[]>(() => {
        droneRepos.value.forEach((droneRepo) => {
            droneRepo.build.lastUpdated = droneRepo.build.lastUpdated.replace("T", " ")
        });
        return [...droneRepos.value]
            .filter(d => d.build != null)
            .sort((a, b) => new Date(b.build.lastUpdated).getTime() - new Date(a.build.lastUpdated).getTime());
    });

    async function fetchDroneRepos(repoType: DroneRepoType) {
        droneRepos.value = await getDroneRepos(repoType);
    }

    return {
        droneRepos,
        fetchDroneRepos,
        sortedDroneRepos,
    };
});
