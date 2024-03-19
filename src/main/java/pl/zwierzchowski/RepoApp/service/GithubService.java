package pl.zwierzchowski.RepoApp.service;

import pl.zwierzchowski.RepoApp.domain.Branch;
import pl.zwierzchowski.RepoApp.domain.Repository;
import pl.zwierzchowski.RepoApp.domain.dto.BranchDTO;
import pl.zwierzchowski.RepoApp.domain.dto.RepositoryDTO;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface GithubService {

    Set<RepositoryDTO> getRepositories(String username);
    Flux<Repository> getAllRepositories(String username);
    Set<BranchDTO> getRepositoryBranchesDetails(String username, String repoName);
    Flux<Branch> getRepositoryBranches(String username, String repoName);

}
