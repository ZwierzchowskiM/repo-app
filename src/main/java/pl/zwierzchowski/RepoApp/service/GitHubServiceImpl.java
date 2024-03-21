package pl.zwierzchowski.RepoApp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.zwierzchowski.RepoApp.domain.Branch;
import pl.zwierzchowski.RepoApp.domain.CommitResponse;
import pl.zwierzchowski.RepoApp.domain.Repository;
import pl.zwierzchowski.RepoApp.domain.dto.BranchDTO;
import pl.zwierzchowski.RepoApp.domain.dto.CommitDTO;
import pl.zwierzchowski.RepoApp.domain.dto.RepositoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    String test = "https://api.github.com/repos/ZwierzchowskiM/repo-app/commits";

    WebClient webClient;

    public GitHubServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    public Set<RepositoryDTO> getRepositoriesDetails(String username) {

        Flux<Repository> repositories = getAllRepositories(username);

        Set<RepositoryDTO> repositoryDTOS =
                repositories
                        .map(repository -> new RepositoryDTO(repository.getName(), repository.getUrl()))
                        .collect(Collectors.toSet()).block();

        return repositoryDTOS;
    }

    public Flux<Repository> getAllRepositories(String username) {

        Flux<Repository> repositories = webClient.get()
                .uri(gitHubReposApiUrl,username)
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(),
                        clientResponse -> handleResponse(clientResponse.statusCode()))
                .bodyToFlux(Repository.class)
                .log()
                .onErrorResume(Exception.class, e -> Flux.empty());

        return repositories;
    }

    public Set<BranchDTO> getRepositoryBranchesDetails(String username,String repositoryName) {

        Flux<Branch> branches = getRepositoryBranches(username, repositoryName);

        Set<BranchDTO> branchDTOS =
                branches
                        .map(branch -> new BranchDTO(branch.getName()))
                        .collect(Collectors.toSet()).block();

        return branchDTOS;
    }


    public Flux<Branch> getRepositoryBranches(String username,String repositoryName) {

        Flux<Branch> branches = webClient.get()
                .uri(gitHubBranchesApiUrl,username,repositoryName)
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(),
                        clientResponse -> handleResponse(clientResponse.statusCode()))
                .bodyToFlux(Branch.class)
                .onErrorResume(Exception.class, e -> Flux.empty());

        return branches;
    }

    public Set<CommitDTO> getRepositoryCommitDetails(String username, String repositoryName) {

        Flux<CommitResponse> commits = getRepositoryCommits(username, repositoryName);

        Set<CommitDTO> commitDTOS =
                commits
                        .map(commitResponse -> new CommitDTO(
                                commitResponse.getSha(), commitResponse.getCommit().getMessage(),
                                commitResponse.getCommit().getAuthor().getName(),
                                commitResponse.getCommit().getAuthor().getDate()))
                        .collect(Collectors.toSet()).block();

        return commitDTOS;
    }
    public Flux<CommitResponse> getRepositoryCommits(String username, String repositoryName) {

        Flux<CommitResponse> commits = webClient.get()
                .uri(gitHubCommitsApiUrl,username,repositoryName)
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(),
                        clientResponse -> handleResponse(clientResponse.statusCode()))
                .bodyToFlux(CommitResponse.class)
                .log()
                .onErrorResume(Exception.class, e -> Flux.empty());

        return commits;
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

