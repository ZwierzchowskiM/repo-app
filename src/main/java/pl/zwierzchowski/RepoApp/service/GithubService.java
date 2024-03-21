package pl.zwierzchowski.RepoApp.service;

import pl.zwierzchowski.RepoApp.domain.Branch;
import pl.zwierzchowski.RepoApp.domain.CommitResponse;
import pl.zwierzchowski.RepoApp.domain.Repository;
import pl.zwierzchowski.RepoApp.domain.dto.BranchDTO;
import pl.zwierzchowski.RepoApp.domain.dto.CommitDTO;
import pl.zwierzchowski.RepoApp.domain.dto.RepositoryDTO;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface GithubService {

    Set<RepositoryDTO> getRepositoriesDetails(String username);
    Flux<Repository> getAllRepositories(String username);
    Set<BranchDTO> getRepositoryBranchesDetails(String username, String repoName);
    Flux<Branch> getRepositoryBranches(String username, String repoName);
    Set<CommitDTO> getRepositoryCommitDetails(String username, String repoName);
    Flux<CommitResponse> getRepositoryCommits(String username, String repoName);


}
