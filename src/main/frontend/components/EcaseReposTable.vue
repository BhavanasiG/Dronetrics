<template>
  <repos-table :isLoading="isLoading" :repo-type="'ecase'" :sorted-drone-repos="sortedDroneRepos"/>
</template>

<script lang="ts">
import {FilterMatchMode} from "@primevue/core/api";
import {defineComponent} from "vue";
import {getFavicon} from "@/branding/logo";
import {useDroneReposStore} from "@/store/dronetrics-repos.store";
import {DroneRepo} from "@/js/api/dronetrics.api";
import ReposTable from "@/components/ReposTable.vue";

export default defineComponent({
  methods: {
    getFavicon,
    buildLink(repo: DroneRepo): string {
      return `https://drone-github-ecase.fivium.co.uk/${repo.repoName}/${repo.build.buildNumber}`;
    },
  },
  components: {
    ReposTable,
  },
  data() {
    return {
      isLoading: true,
      filters: {
        global: {value: null as string | null, matchMode: FilterMatchMode.CONTAINS},
      },
    };
  },
  setup() {
    const droneRepoStore = useDroneReposStore();
    return {
      droneRepoStore,
    };
  },
  computed: {
    sortedDroneRepos(): DroneRepo[] {
      return this.droneRepoStore.sortedDroneRepos;
    },
  },

  async created() {
    try {
      await this.droneRepoStore.fetchDroneRepos("ecase");
    } catch (error: unknown) {
      console.error(error);
    } finally {
      this.isLoading = false;
    }
  },
});
</script>
