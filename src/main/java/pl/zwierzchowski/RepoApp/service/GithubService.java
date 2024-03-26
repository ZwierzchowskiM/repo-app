package pl.zwierzchowski.RepoApp.service;

import pl.zwierzchowski.RepoApp.domain.dto.BranchDTO;
import pl.zwierzchowski.RepoApp.domain.dto.CommitDTO;
import pl.zwierzchowski.RepoApp.domain.dto.RepositoryDTO;
import reactor.core.publisher.Flux;
import java.net.URI;
import java.util.Set;

public interface GithubService {

    Set<RepositoryDTO> getRepositoriesDetails(String username);

    Set<BranchDTO> getRepositoryBranchesDetails(String username, String repoName);

    Set<CommitDTO> getRepositoryCommitDetails(String username, String repoName);

    <T> Flux<T> getResponse(String username, Class<T> responseType, String url);

    <T> Flux<T> fetchResponse(URI uri, Class<T> responseType);

}
