<template>
  <div style="padding: 2rem;">
    <router-link :to="{ name: 'repos' }">&larr; Back to repositories</router-link>

    <h1 style="margin: 1rem 0 2rem;">{{ repoName }}</h1>
    <p>All builds for this repository.</p>

    <DataTable v-model:filters="filters" :loading="isLoading" :sortOrder="-1" :value="builds" sortField="buildNumber" stripedRows>
      <template #header>
        <div style="display: flex; align-items: center; gap: 0.5rem; flex-wrap: wrap; justify-content: flex-end;">
          <div style="display: flex; align-items: center; gap: 0.5rem;">
            <Checkbox v-model="showAll" binary inputId="showAll"/>
            <label for="showAll">Show all</label>
          </div>
          <MultiSelect
              v-model="filters['event'].value" :maxSelectedLabels="2" :options="eventOptions" display="chip"
              placeholder="Filter events" showClear style="min-width: 14rem;"
          />
          <MultiSelect
              v-model="filters['author'].value" :maxSelectedLabels="2" :options="authorOptions" display="chip" filter
              filterPlaceholder="Search usernames" placeholder="Filter usernames" showClear style="min-width: 14rem;"
          />
        </div>
      </template>
      <template #empty> No builds found.</template>
      <template #loading> Loading build data. Please wait...</template>

      <Column field="buildNumber" header="Build Number" sortable>
        <template #body="{ data }">
          <a :href="buildLink(data)" rel="noopener noreferrer" target="_blank">{{ data.buildNumber }}</a>
        </template>
      </Column>
      <Column field="status" header="Status" sortable></Column>
      <Column field="event" header="Event" sortable></Column>
      <Column field="title" header="Title">
        <template #body="{ data }">
          <a :href="data.link" rel="noopener noreferrer" target="_blank">{{ data.title }}</a>
        </template>
      </Column>
      <Column field="sourceBranch" header="Branch">
        <template #body="{ data }">
          <a :href="branchLink(data)" rel="noopener noreferrer" target="_blank">{{ data.sourceBranch }}</a>
        </template>
      </Column>
      <Column field="lastUpdated" header="Last Updated" sortable></Column>
      <Column field="author" header="Author">
        <template #body="{ data }">
          <div style="display: flex; align-items: center; gap: 0.5rem;">
            <Avatar :image="data.authorAvatar" shape="circle"/>
            <span>{{ data.author }}</span>
          </div>
        </template>
      </Column>
    </DataTable>
  </div>
</template>

<script lang="ts">
import {FilterMatchMode} from "@primevue/core/api";
import Avatar from "primevue/avatar";
import Checkbox from "primevue/checkbox";
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import MultiSelect from "primevue/multiselect";
import {defineComponent} from "vue";
import {getFavicon} from "@/branding/logo";
import {useDroneBuildsStore} from "@/store/dronetrics-builds.store";
import {BuildEvent, DroneBuild} from "@/js/api/dronetrics.api";
import {DroneRepoType} from "@/store/dronetrics-repos.store";

const EVENT_OPTIONS: BuildEvent[] = [
  "PUSH",
  "PULL_REQUEST",
  "TAG",
  "PROMOTE",
  "ROLLBACK",
  "CRON",
  "CUSTOM",
  "UNKNOWN",
];

export default defineComponent({
  components: {
    Avatar,
    Checkbox,
    DataTable,
    Column,
    MultiSelect,
  },

  data() {
    return {
      isLoading: true,
      showAll: false,
      logoSvg: getFavicon(),
      eventOptions: EVENT_OPTIONS,
      filters: {
        author: {value: null as string[] | null, matchMode: FilterMatchMode.IN},
        event: {value: null as BuildEvent[] | null, matchMode: FilterMatchMode.IN},
      },
    };
  },

  setup() {
    const droneBuildsStore = useDroneBuildsStore();
    return {
      droneBuildsStore,
    };
  },

  computed: {
    repoName(): string {
      const repo = this.$route.query.repo;
      return Array.isArray(repo) ? (repo[0] ?? "") : (repo ?? "");
    },
    repoType(): DroneRepoType {
      const repoType = this.$route.query.repoType;
      return Array.isArray(repoType) ? (repoType[0] ?? 'ecase') : (repoType ?? 'ecase');
    },
    builds(): DroneBuild[] {
      if (this.showAll) {
        return this.droneBuildsStore.sortedDroneBuilds;
      }

      const byBranch = new Map<string, DroneBuild>();
      const prTitleByBranch = new Map<string, string>();
      const prTitleToLink = new Map<string, string>();

      for (const build of this.droneBuildsStore.sortedDroneBuilds) {
        const existing = byBranch.get(build.sourceBranch);

        if (!existing || this.isPreferredBuild(build, existing)) {
          byBranch.set(build.sourceBranch, build);
        }

        if (build.event === "PULL_REQUEST" && (!prTitleByBranch.has(build.sourceBranch) || !prTitleToLink.has(build.sourceBranch))) {
          prTitleByBranch.set(build.sourceBranch, build.title);
          prTitleToLink.set(build.sourceBranch, build.link);
        }
      }

      const withPrTitle = [...byBranch.values()].map(build => {
        const prTitle = prTitleByBranch.get(build.sourceBranch);
        return prTitle ? {...build, title: prTitle} : build;
      });

      return [...withPrTitle].map(build => {
        const prLink = prTitleToLink.get(build.sourceBranch);
        return prLink ? {...build, link: prLink} : build;
      });
    },
    authorOptions(): string[] {
      return [...new Set(this.builds.map(b => b.author).filter(Boolean))].sort();
    },
  },

  methods: {
    buildLink(build: DroneBuild): string {
      if (this.repoType === "ecase") {
        return `${import.meta.env.VITE_DRONE_ECASE_API_URL}/${this.repoName}/${build.buildNumber}`;
      } else {
        return `${import.meta.env.VITE_DIGITAL_ECASE_API_URL}/${this.repoName}/${build.buildNumber}`;
      }
    },
    branchLink(build: DroneBuild): string {
      return `https://github.com/${this.repoName}/tree/${build.sourceBranch}`;
    },
    // Prefer the PUSH build for a branch over a PULL_REQUEST build; otherwise take the latest.
    isPreferredBuild(candidate: DroneBuild, current: DroneBuild): boolean {
      const candidateIsPush = candidate.event === "PUSH";
      const currentIsPush = current.event === "PUSH";
      if (candidateIsPush !== currentIsPush) {
        return candidateIsPush;
      }
      return candidate.buildNumber > current.buildNumber;
    },
  },

  async created() {
    try {
      await this.droneBuildsStore.fetchDroneBuilds(this.repoName, this.repoType);
    } catch (error: unknown) {
      console.error(error);
    } finally {
      this.isLoading = false;
    }
  },
});
</script>
