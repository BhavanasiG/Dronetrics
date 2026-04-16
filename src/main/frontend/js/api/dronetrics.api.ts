const DRONETRICS_ENDPOINT = "/rest/api/drone";

export type JsonDroneRepo = {
    id: number,
    uid: string,
    user_id: number,
    namespace: string,
    name: string,
    slug: string,
    scm: string,
    git_http_url: string,
    git_ssh_url: string,
    link: string,
    default_branch: string,
    private: boolean,
    visibility: string,
    active: true,
    config_path: string,
    trusted: boolean,
    protected: boolean,
    ignore_forks: boolean,
    ignore_pull_requests: boolean,
    timeout: number,
    counter: number,
    synced: number,
    created: number,
    updated: number,
    version: number
}

/* TODO: This needs to be called in a store */
export async function getDroneRepositories(): Promise<JsonDroneRepo[]> {
    const response = await fetch(DRONETRICS_ENDPOINT);

    if (!response.ok) {
        throw new Error(`Failed to fetch drone data: ${response.status}`);
    }

    return await response.json();
}
