import {DroneRepoType} from "@/store/dronetrics-repos.store";

const DRONETRICS_ENDPOINT = "/rest/api/drone";

export type DroneRepo = {
    repoName: string,
    // link: string,
    build: DroneBuild,
}

export interface DroneBuild {
    buildNumber: number;
    status: BuildStatus;
    link: string;
    event: BuildEvent;
    title: string;
    sourceRepo: string;
    sourceBranch: string;
    targetBranch: string;
    authorAvatar: string;
    author: string;
    lastUpdated: string;
}

export type BuildStatus =
    | 'PENDING'
    | 'RUNNING'
    | 'SUCCESS'
    | 'FAILURE'
    | 'KILLED'
    | 'ERROR'
    | 'SKIPPED'
    | 'BLOCKED'
    | 'DECLINED'
    | 'WAITING_ON_DEPENDENCIES'
    | 'UNKNOWN';

export type BuildEvent =
    | 'PUSH'
    | 'PULL_REQUEST'
    | 'TAG'
    | 'PROMOTE'
    | 'ROLLBACK'
    | 'CRON'
    | 'CUSTOM'
    | 'UNKNOWN';

export async function getDroneRepos(repoType: DroneRepoType): Promise<DroneRepo[]> {
    const response = await fetch(DRONETRICS_ENDPOINT + `/${repoType}/repos`);

    if (!response.ok) {
        throw new Error(`Failed to fetch drone data: ${response.status}`);
    }

    return await response.json();
}

export async function getSpecificDroneBuild(fullRepoName: string, repoType: DroneRepoType): Promise<DroneBuild[]> {
    const response = await fetch(DRONETRICS_ENDPOINT + `/${repoType}/builds?repoName=${fullRepoName}`);

    if (!response.ok) {
        throw new Error(`Failed to fetch drone data: ${response.status}`);
    }

    return await response.json();
}
