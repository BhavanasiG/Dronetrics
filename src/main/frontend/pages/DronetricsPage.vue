<template>
  <div style="padding: 2rem;">
    <h1 style="margin-bottom: 2rem;">Dronetrics Dashboard</h1>
    <p>Welcome to the Dronetrics Dashboard! Here you can monitor the status of your drone repositories and their build status.
      Use the table below to quickly check the health of your projects and access relevant links.</p>

    <SelectButton v-model="value" :allowEmpty="false" :options="options"/>
    <ecase-repos-table v-if="value === 'eCase'"/>
    <digital-repos-table v-if="value === 'Digital'"/>
  </div>
</template>

<script lang="ts">
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import {defineComponent, ref} from "vue";
import {getFavicon} from "@/branding/logo";
import ReposTable from "@/components/ReposTable.vue";
import EcaseReposTable from "@/components/EcaseReposTable.vue";
import DigitalReposTable from "@/components/DigitalReposTable.vue";
import {SelectButton} from "primevue";

export default defineComponent({
  methods: {getFavicon},
  components: {
    DigitalReposTable,
    EcaseReposTable,
    ReposTable,
    DataTable,
    Column,
    SelectButton,
  },

  data() {
    const visible = ref(false);
    const options = ["eCase", "Digital"]
    return {
      logoSvg: getFavicon(),
      visible,
      options,
      value: "eCase",
    };
  },
});
</script>
