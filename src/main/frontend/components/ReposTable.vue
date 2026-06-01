<template>
  <div style="padding: 2rem;">

    <DataTable :loading="isLoading" :sortOrder="-1" :value="sortedDroneRepos" sortField="lastUpdated" stripedRows>
      <template #empty> No repositories found.</template>
      <template #loading> Loading Drone data. Please wait...</template>

      <Column field="name" header="Repository Name" sortable></Column>
      <Column field="buildNumber" header="Build Number"></Column>
      <Column field="lastestBuild.lastUpdated" header="Last Updated"></Column>
    </DataTable>

    <DataTable :loading="isLoading" stripedRows>

    </DataTable>
  </div>
</template>

<script lang="ts">
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import {defineComponent} from "vue";
import {getFavicon} from "@/branding/logo";
import {useDroneReposStore} from "@/store/dronetrics-repos.store";
import {DroneRepo} from "@/js/api/dronetrics.api";

export default defineComponent({
  methods: {getFavicon},
  components: {
    DataTable,
    Column,
  },

  data() {
    return {
      isLoading: true,
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
      console.log(this.droneRepoStore.sortedDroneRepos)
      return this.droneRepoStore.sortedDroneRepos;
    },
  },

  async created() {
    try {
      await this.droneRepoStore.fetchDroneRepos();
    } catch (error: unknown) {
      console.error(error);
    } finally {
      this.isLoading = false;
    }
  },
});
</script>
