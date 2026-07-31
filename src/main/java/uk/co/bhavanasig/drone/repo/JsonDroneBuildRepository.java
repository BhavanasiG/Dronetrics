package uk.co.bhavanasig.drone.repo;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import uk.co.bhavanasig.drone.model.JsonDroneBuild;
import uk.co.bhavanasig.drone.model.JsonDroneBuildId;

public interface JsonDroneBuildRepository extends ListCrudRepository<JsonDroneBuild, JsonDroneBuildId> {
  List<JsonDroneBuild> findByRepoNameOrderByBuildNumberDesc(String repoName);

  Page<JsonDroneBuild> findByRepoName(String repoName, Pageable pageable);

  List<JsonDroneBuild> findTop50ByRepoNameOrderByBuildNumberDesc(String repoName);
}
