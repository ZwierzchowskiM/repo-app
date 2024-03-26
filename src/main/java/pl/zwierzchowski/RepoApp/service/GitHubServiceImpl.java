package pl.zwierzchowski.RepoApp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import pl.zwierzchowski.RepoApp.domain.Branch;
import pl.zwierzchowski.RepoApp.domain.CommitResponse;
import pl.zwierzchowski.RepoApp.domain.Repository;
import pl.zwierzchowski.RepoApp.domain.dto.BranchDTO;
import pl.zwierzchowski.RepoApp.domain.dto.CommitDTO;
import pl.zwierzchowski.RepoApp.domain.dto.RepositoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GitHubServiceImpl implements GithubService {

    @Value("${github.repos.api.url:https://api.github.com/users/{username}/repos}")
    private String gitHubReposApiUrl;
    @Value("${github.branches.api.url:https://api.github.com/repos/{username}/{reponame}/branches}")
    private String gitHubBranchesApiUrl;
    @Value("${github.commits.api.url:https://api.github.com/repos/{username}/{reponame}/commits}")
    private String gitHubCommitsApiUrl;

    WebClient webClient;

    public GitHubServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    public Set<RepositoryDTO> getRepositoriesDetails(String username) {

        Flux<Repository> repositories = getResponse(username, Repository.class, gitHubReposApiUrl);

        Set<RepositoryDTO> repositoryDTOS =
                repositories
                        .map(repository -> new RepositoryDTO(repository.getName(), repository.getUrl()))
                        .collect(Collectors.toSet()).block();

        return repositoryDTOS;
    }

    public Set<BranchDTO> getRepositoryBranchesDetails(String username, String repositoryName) {

        Flux<Branch> branches = getResponse(username, repositoryName, Branch.class, gitHubBranchesApiUrl);

        Set<BranchDTO> branchDTOS =
                branches
                        .map(branch -> new BranchDTO(branch.getName()))
                        .collect(Collectors.toSet()).block();

        return branchDTOS;
    }

    public Set<CommitDTO> getRepositoryCommitDetails(String username, String repositoryName) {

        Flux<CommitResponse> commits = getResponse(username, repositoryName, CommitResponse.class, gitHubCommitsApiUrl);

        Set<CommitDTO> commitDTOS =
                commits
                        .map(commitResponse -> new CommitDTO(
                                commitResponse.getSha(), commitResponse.getCommit().getMessage(),
                                commitResponse.getCommit().getAuthor().getName(),
                                commitResponse.getCommit().getAuthor().getDate()))
                        .collect(Collectors.toSet()).block();

        return commitDTOS;
    }

    public <T> Flux<T> getResponse(String username, Class<T> responseType, String url) {
        URI uri = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand(username)
                .toUri();

        return fetchResponse(uri, responseType);
    }

    public <T> Flux<T> getResponse(String username, String repositoryName, Class<T> responseType, String url) {
        URI uri = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand(username, repositoryName)
                .toUri();

        return fetchResponse(uri, responseType);
    }

    public <T> Flux<T> fetchResponse(URI uri, Class<T> responseType) {

        Flux<T> response = webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(),
                        clientResponse -> handleResponse(clientResponse.statusCode()))
                .bodyToFlux(responseType)
                .log()
                .onErrorResume(Exception.class, e -> Flux.empty());

        return response;
    }

    private Mono<? extends Throwable> handleResponse(HttpStatusCode statusCode) {

        if (statusCode.is2xxSuccessful()) {
            return null;
        } else if (statusCode.is4xxClientError()) {
            return Mono.error(new RuntimeException("Client Error"));
        } else if (statusCode.is5xxServerError()) {
            return Mono.error(new RuntimeException("Server error"));
        } else {
            return Mono.error(new RuntimeException("Unexpected error"));
        }
    }
}

