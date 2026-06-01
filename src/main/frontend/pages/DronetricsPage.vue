<template>
  <div style="padding: 2rem;">
    <Sidebar :visible="true" header="Sidebar">
      <div v-html="logoSvg"></div>
    </Sidebar>

    <h1 style="margin-bottom: 2rem;">Dronetrics Dashboard</h1>
    <p>Welcome to the Dronetrics Dashboard! Here you can monitor the status of your drone repositories and their build statuses.
      Use the table below to quickly check the health of your projects and access relevant links.</p>

    <repos-table/>
  </div>
</template>

<script lang="ts">
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import {defineComponent, ref} from "vue";
import {getFavicon} from "@/branding/logo";
import ReposTable from "@/components/ReposTable.vue";

export default defineComponent({
  methods: {getFavicon},
  components: {
    ReposTable,
    DataTable,
    Column,
  },

  data() {
    const visible = ref(false);
    return {
      logoSvg: getFavicon(),
      visible,
    };
  },
});
</script>
