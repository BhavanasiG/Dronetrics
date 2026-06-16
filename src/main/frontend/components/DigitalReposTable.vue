<template>
  <repos-table :isLoading="isLoading" :repo-type="'digital'" :sorted-drone-repos="sortedDroneRepos"/>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {useDroneReposStore} from "@/store/dronetrics-repos.store";
import {DroneRepo} from "@/js/api/dronetrics.api";
import ReposTable from "@/components/ReposTable.vue";

export default defineComponent({
  components: {
    ReposTable,
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
      return this.droneRepoStore.sortedDroneRepos;
    },
  },

  async created() {
    try {
      await this.droneRepoStore.fetchDroneRepos("digital");
    } catch (error: unknown) {
      console.error(error);
    } finally {
      this.isLoading = false;
    }
  },
});
</script>
