import {defineStore} from "pinia";
import {computed, ref} from "vue";
import {DroneBuild, getSpecificDroneBuild} from "@/js/api/dronetrics.api";

export const useDroneReposStore = defineStore("dronetrics/builds", () => {
    const droneBuilds = ref<DroneBuild[]>([]);

    const sortedDroneBuilds = computed<DroneBuild[]>(() => {
        return [...droneBuilds.value]
            .filter(d => d.buildNumber > 1)
            .sort((a, b) => b.buildNumber - a.buildNumber);
    });

    async function fetchDroneBuilds(fullRepoName: string) {
        droneBuilds.value = await getSpecificDroneBuild(fullRepoName);
    }

    return {
        droneBuilds,
        fetchDroneBuilds,
        sortedDroneBuilds,
    };
});
