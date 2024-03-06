package pl.zwierzchowski.RepoApp.service;

import pl.zwierzchowski.RepoApp.domain.Branch;
import pl.zwierzchowski.RepoApp.domain.Repository;
import pl.zwierzchowski.RepoApp.domain.dto.RepositoryDTO;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;

public interface GithubService {

    Set<RepositoryDTO> getRepositoriesDetails(String username);
    Flux<Repository> getAllRepositories(String username);

}
