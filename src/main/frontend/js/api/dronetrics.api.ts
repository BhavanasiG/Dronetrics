const DRONETRICS_ENDPOINT = "/rest/api/drone";

export type DroneRepo = {
    name: string,
    fullName: string,
    link: string,
    buildNumber: number,
    lastestBuild: DroneBuild,
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
    lastUpdated: Date;
}

export type BuildStatus = 'RUNNING' | 'SUCCESS' | 'FAILURE' | 'KILLED';

export type BuildEvent = 'PUSH' | 'PULL_REQUEST';

/* TODO: This needs to be called in a store */
export async function getDroneRepos(): Promise<DroneRepo[]> {
    const response = await fetch(DRONETRICS_ENDPOINT + "/repos");

    if (!response.ok) {
        throw new Error(`Failed to fetch drone data: ${response.status}`);
    }

    return await response.json();
}

/* TODO: This needs to be called in a store */
export async function getSpecificDroneBuild(fullRepoName: string): Promise<DroneBuild[]> {
    const response = await fetch(DRONETRICS_ENDPOINT + `/builds?fullRepoName=${fullRepoName}`);

    if (!response.ok) {
        throw new Error(`Failed to fetch drone data: ${response.status}`);
    }

    return await response.json();
}
