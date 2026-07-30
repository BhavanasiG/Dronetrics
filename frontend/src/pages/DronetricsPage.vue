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
import SelectButton from "primevue/selectbutton";
import {defineComponent} from "vue";
import DigitalReposTable from "../components/DigitalReposTable.vue";
import EcaseReposTable from "../components/EcaseReposTable.vue";

export default defineComponent({
  components: {
    DigitalReposTable,
    EcaseReposTable,
    SelectButton,
  },

  data() {
    const options = ["eCase", "Digital"];
    return {
      options,
      value: "eCase",
    };
  },
});
</script>
