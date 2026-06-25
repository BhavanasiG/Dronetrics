<template>
  <div style="padding: 2rem;">

    <p><i style="display: block; text-align: start;">Found {{ sortedDroneRepos.length }} repositories</i></p>

    <DataTable
        v-model:filters="filters"
        :globalFilterFields="['repoName']"
        :loading="isLoading"
        :rows="10"
        :rowsPerPageOptions="[5].concat((Math.floor(sortedDroneRepos.length/10)) > 10 ? [10, (Math.floor(sortedDroneRepos.length/10))]
            : [(Math.floor(sortedDroneRepos.length/10))])"
        :sortOrder="-1"
        :value="sortedDroneRepos"
        paginator
        sortField="build.lastUpdated"
        stripedRows
    >
      <template #header>
        <div style="display: flex; justify-content: flex-end; gap: 1rem;">
          <MultiSelect
              v-model="filters['build.status'].value"
              :maxSelectedLabels="1"
              :options="statusOptions"
              placeholder="Filter by Status"
              style="min-width: 14rem"
          >
            <template #option="slotProps">
              <div style="display: flex; align-items: center; gap: 0.5rem;">
                <span>{{ slotProps.option }}</span>
              </div>
            </template>
          </MultiSelect>

          <IconField>
            <InputIcon class="pi pi-search"/>
            <InputText v-model="filters['global'].value" placeholder="Search repositories"/>
          </IconField>
        </div>
      </template>
      <template #empty> No repositories found.</template>
      <template #loading> Loading Drone data. Please wait...</template>

      <Column field="repoName" header="Repository Name" sortable>
        <template #body="{ data }">
          <router-link :to="{ name: 'builds', query: { repo: data.repoName, repoType: repoType } }">{{
              data.repoName
            }}
          </router-link>
        </template>
      </Column>
      <Column field="build.lastUpdated" header="Last Updated" sortable></Column>
      <Column field="build.buildNumber" header="Build Number">
        <template #body="{ data }">
          <a :href="buildLink(data)" rel="noopener noreferrer" target="_blank">{{ data.build.buildNumber }}</a>
        </template>
      </Column>
      <Column field="build.status" header="Status"></Column>
      <Column field="build.author" header="Author" sortable>
        <template #body="{ data }">
          <div style="display: flex; align-items: center; gap: 0.5rem;">
            <Avatar :image="data.build.authorAvatar" shape="circle"/>
            <span>{{ data.build.author }}</span>
          </div>
        </template>
      </Column>
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
import {defineComponent, PropType} from "vue";
import {getFavicon} from "@/branding/logo";
import {DroneRepo} from "@/js/api/dronetrics.api";
import {DroneRepoType} from "@/store/dronetrics-repos.store";
import {MultiSelect} from "primevue";

export default defineComponent({
  methods: {
    getFavicon,
    buildLink(repo: DroneRepo): string {
      if (this.repoType === "ecase") {
        return `${import.meta.env.VITE_DRONE_ECASE_API_URL}/${repo.repoName}/${repo.build.buildNumber}`;
      } else {
        return `${import.meta.env.VITE_DRONE_DIGITAL_API_URL}/${repo.repoName}/${repo.build.buildNumber}`;
      }
    },
  },
  components: {
    Avatar,
    DataTable,
    Column,
    IconField,
    InputIcon,
    InputText,
    MultiSelect,
  },
  props: {
    sortedDroneRepos: {
      type: Array as PropType<DroneRepo[]>,
      required: true,
    },
    isLoading: {
      type: Boolean,
      required: true,
    },
    repoType: {
      type: String as PropType<DroneRepoType>,
      required: true,
    }
  },
  data() {
    const statusOptions: string[] = [
      'PENDING', 'RUNNING', 'SUCCESS', 'FAILURE',
      'KILLED', 'ERROR', 'SKIPPED', 'BLOCKED',
      'DECLINED', 'WAITING_ON_DEPENDENCIES', 'UNKNOWN'
    ];
    return {
      statusOptions,
      filters: {
        global: {value: null as string | null, matchMode: FilterMatchMode.CONTAINS},
        'build.status': {value: null as string[] | null, matchMode: FilterMatchMode.IN}
      },
    };
  },
});
</script>
