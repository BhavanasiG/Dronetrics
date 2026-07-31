import type { DroneRepoType } from "./dronetrics-repos.store";
import type { DroneBuild } from "@/js/api/dronetrics.api";
import { defineStore } from "pinia";
import { computed, ref } from "vue";
import { getSpecificDroneBuild } from "@/js/api/dronetrics.api";

export const useDroneBuildsStore = defineStore("dronetrics/builds", () => {
  const droneBuilds = ref<DroneBuild[]>([]);

  const sortedDroneBuilds = computed<DroneBuild[]>(() => {
    droneBuilds.value.forEach((droneBuild) => {
      droneBuild.lastUpdated = droneBuild.lastUpdated.replace("T", " ");
    });
    return [...droneBuilds.value]
      .sort((a, b) => b.buildNumber - a.buildNumber);
  });

  async function fetchDroneBuilds(fullRepoName: string, repoType: DroneRepoType) {
    droneBuilds.value = await getSpecificDroneBuild(fullRepoName, repoType);
  }

  async function loadOlderBuilds(repoName: string, repoType: DroneRepoType, page: number) {
    const newBuilds = await getSpecificDroneBuild(repoName, repoType, page);

    newBuilds.forEach((droneBuild) => {
      droneBuild.lastUpdated = droneBuild.lastUpdated.replace("T", " ");
    });

    droneBuilds.value = [...droneBuilds.value, ...newBuilds];
    return newBuilds.length;
  }

  return {
    droneBuilds,
    fetchDroneBuilds,
    sortedDroneBuilds,
    loadOlderBuilds,
  };
});
