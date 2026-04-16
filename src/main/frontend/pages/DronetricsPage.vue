<template>
  <div style="padding: 2rem;">
    <Sidebar :visible="true" header="Sidebar">
      <div v-html="logoSvg"></div>
    </Sidebar>

    <h1 style="margin-bottom: 2rem;">Dronetrics Dashboard</h1>
    <p>Welcome to the Dronetrics Dashboard! Here you can monitor the status of your drone repositories and their build statuses.
      Use the table below to quickly check the health of your projects and access relevant links.</p>

    <DataTable :loading="isLoading" :value="droneData" stripedRows>
      <template #empty> No repositories found.</template>
      <template #loading> Loading Drone data. Please wait...</template>

      <Column field="name" header="Repository Name"></Column>
      <Column field="status" header="Build Status"></Column>
      <Column field="link" header="Link"></Column>
    </DataTable>
  </div>
</template>

<script lang="ts">
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import {defineComponent, ref} from "vue";
import {getFavicon} from "@/branding/logo";
import {getDroneRepositories} from "@/js/api/dronetrics.api";

export default defineComponent({
  methods: {getFavicon},
  components: {
    DataTable,
    Column,
  },

  data() {
    const visible = ref(false);
    return {
      droneData: [] as any[],
      isLoading: true,
      logoSvg: getFavicon(),
      visible,
    };
  },

  async mounted() {
    try {
      this.droneData = await getDroneRepositories();
    } catch (error) {
      console.error(error);
    } finally {
      this.isLoading = false;
    }
  },
});
</script>
