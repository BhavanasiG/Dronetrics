import {defineStore} from "pinia";
import {computed, ref} from "vue";
import {DroneRepoType} from "./dronetrics-repos.store";
import {DroneBuild, getSpecificDroneBuild} from "../js/api/dronetrics.api";

export const useDroneBuildsStore = defineStore("dronetrics/builds", () => {
    const droneBuilds = ref<DroneBuild[]>([]);

    const sortedDroneBuilds = computed<DroneBuild[]>(() => {
        droneBuilds.value.forEach((droneBuild) => {
            droneBuild.lastUpdated = droneBuild.lastUpdated.replace("T", " ")
        });
        return [...droneBuilds.value]
            .sort((a, b) => b.buildNumber - a.buildNumber);
    });

    async function fetchDroneBuilds(fullRepoName: string, repoType: DroneRepoType) {
        droneBuilds.value = await getSpecificDroneBuild(fullRepoName, repoType);
    }

    return {
        droneBuilds,
        fetchDroneBuilds,
        sortedDroneBuilds,
    };
});
