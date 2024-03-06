package pl.zwierzchowski.RepoApp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.zwierzchowski.RepoApp.domain.Branch;
import pl.zwierzchowski.RepoApp.domain.Repository;
import pl.zwierzchowski.RepoApp.domain.dto.BranchDTO;
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
                .onErrorResume(Exception.class, e -> Flux.empty());


        return repositories;

    }

    private Mono<? extends Throwable> handleResponse(HttpStatusCode statusCode) {

        if (statusCode.is2xxSuccessful()) {
            return null;
        } else if (statusCode.is4xxClientError()) {
            // Handle client errors (e.g., 404 Not Found)
            return Mono.error(new RuntimeException("Employee not found"));
        } else if (statusCode.is5xxServerError()) {
            // Handle server errors (e.g., 500 Internal Server Error)
            return Mono.error(new RuntimeException("Server error"));
        } else {
            // Handle other status codes as needed
            return Mono.error(new RuntimeException("Unexpected error"));
        }
    }
}

