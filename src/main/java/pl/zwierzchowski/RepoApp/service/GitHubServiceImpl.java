package pl.zwierzchowski.RepoApp.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.zwierzchowski.RepoApp.domain.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GitHubServiceImpl implements GithubService {

    private final String gitHubReposApiUrl = "https://api.github.com/users/ZwierzchowskiM/repos";

    WebClient webClient;

    public GitHubServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }


    public Flux<Repository> getAllRepositories(String username) {

        return webClient.get()
                .uri(gitHubReposApiUrl)
                .retrieve()
                .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(),
                        clientResponse -> handleResponse(clientResponse.statusCode()))
                .bodyToFlux(Repository.class)
                .onErrorResume(Exception.class, e -> Flux.empty()); // Return an empty collection on error
    }

//    private Mono<? extends Throwable> handleErrorResponse(HttpStatusCode statusCode) {
//
//        // Handle non-success status codes here (e.g., logging or custom error handling)
//        return Mono.error(new RuntimeException("Failed to fetch employee. Status code: " + statusCode));
//    }

    private Mono<? extends Throwable> handleResponse(HttpStatusCode statusCode) {

        if (statusCode.is2xxSuccessful()) {
            return null;
        }
        else if (statusCode.is4xxClientError()) {
            // Handle client errors (e.g., 404 Not Found)
            return Mono.error(new RuntimeException("Employee not found"));
        }
        else if (statusCode.is5xxServerError()) {
            // Handle server errors (e.g., 500 Internal Server Error)
            return Mono.error(new RuntimeException("Server error"));
        }
        else {
            // Handle other status codes as needed
            return Mono.error(new RuntimeException("Unexpected error"));
        }
    }
    }

