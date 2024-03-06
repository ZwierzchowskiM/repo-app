package pl.zwierzchowski.RepoApp.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.zwierzchowski.RepoApp.domain.Repository;
import pl.zwierzchowski.RepoApp.domain.dto.RepositoryDTO;
import pl.zwierzchowski.RepoApp.service.GithubService;
import reactor.core.publisher.Flux;

import java.util.Set;


@RestController
@RequestMapping("/repositories")
public class RepositoryController {

    private GithubService gitHubService;

    public RepositoryController(GithubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping(value = "/", headers = "Accept=application/json")
    public ResponseEntity<Set<RepositoryDTO>> getUserRepositories(@RequestParam String username) {

        Set<RepositoryDTO> allRepositories = gitHubService.getRepositoriesDetails(username);

        return ResponseEntity.ok(allRepositories);
    }


}