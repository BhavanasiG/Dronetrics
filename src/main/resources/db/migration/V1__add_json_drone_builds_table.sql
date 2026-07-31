DROP TABLE IF EXISTS dronetrics.json_drone_builds;

CREATE TABLE IF NOT EXISTS dronetrics.json_drone_builds (
    repo_name TEXT NOT NULL,
    build_number BIGINT NOT NULL,
    status TEXT,
    link TEXT,
    event TEXT,
    title TEXT,
    source_repo TEXT,
    source_branch TEXT,
    target_branch TEXT,
    author_avatar TEXT,
    author TEXT,
    last_updated TIMESTAMPTZ,
    PRIMARY KEY (repo_name, build_number)
);

CREATE INDEX IF NOT EXISTS idx_drone_build_repo_updated
    ON dronetrics.json_drone_builds(repo_name, last_updated DESC);
