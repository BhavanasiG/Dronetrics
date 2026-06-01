<template>
  <div style="padding: 2rem;">

    <DataTable v-model:filters="filters" :globalFilterFields="['repoName']" :loading="isLoading" :sortOrder="-1" :value="sortedDroneRepos" sortField="build.lastUpdated" stripedRows>
      <template #header>
        <div style="display: flex; justify-content: flex-end;">
          <IconField>
            <InputIcon class="pi pi-search" />
            <InputText v-model="filters['global'].value" placeholder="Search repositories" />
          </IconField>
        </div>
      </template>
      <template #empty> No repositories found.</template>
      <template #loading> Loading Drone data. Please wait...</template>

      <Column field="repoName" header="Repository Name" sortable>
        <template #body="{ data }">
          <router-link :to="{ name: 'builds', query: { repo: data.repoName } }">{{ data.repoName }}</router-link>
        </template>
      </Column>
      <Column field="build.lastUpdated" header="Last Updated" sortable></Column>
      <Column field="build.buildNumber" header="Build Number">
        <template #body="{ data }">
          <a :href="buildLink(data)" target="_blank" rel="noopener noreferrer">{{ data.build.buildNumber }}</a>
        </template>
      </Column>
      <Column field="build.status" header="Status"></Column>
      <Column field="build.author" header="Author" sortable>
        <template #body="{ data }">
          <div style="display: flex; align-items: center; gap: 0.5rem;">
            <Avatar :image="data.build.authorAvatar" shape="circle" />
            <span>{{ data.build.author }}</span>
          </div>
        </template>
      </Column>
    </DataTable>

    <DataTable :loading="isLoading" stripedRows>

    </DataTable>
  </div>
</template>

<script lang="ts">
import {FilterMatchMode} from "@primevue/core/api";
import Avatar from "primevue/avatar";
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import IconField from "primevue/iconfield";
import InputIcon from "primevue/inputicon";
import InputText from "primevue/inputtext";
import {defineComponent} from "vue";
import {getFavicon} from "@/branding/logo";
import {useDroneReposStore} from "@/store/dronetrics-repos.store";
import {DroneRepo} from "@/js/api/dronetrics.api";

export default defineComponent({
  methods: {
    getFavicon,
    buildLink(repo: DroneRepo): string {
      return `https://drone-github-ecase.fivium.co.uk/${repo.repoName}/${repo.build.buildNumber}`;
    },
  },
  components: {
    Avatar,
    DataTable,
    Column,
    IconField,
    InputIcon,
    InputText,
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
