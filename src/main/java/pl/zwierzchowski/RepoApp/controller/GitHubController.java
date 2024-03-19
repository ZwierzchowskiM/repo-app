package pl.zwierzchowski.RepoApp.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.zwierzchowski.RepoApp.domain.dto.BranchDTO;
import pl.zwierzchowski.RepoApp.domain.dto.CommitDTO;
import pl.zwierzchowski.RepoApp.domain.dto.RepositoryDTO;
import pl.zwierzchowski.RepoApp.service.GithubService;

import java.util.Set;


@RestController
@RequestMapping("/github")
public class GitHubController {

    private GithubService gitHubService;

    public GitHubController(GithubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping(value = "/repositories", headers = "Accept=application/json")
    public ResponseEntity<Set<RepositoryDTO>> getUserRepositories(@RequestParam String username) {

        Set<RepositoryDTO> allRepositories = gitHubService.getRepositories(username);

        return ResponseEntity.ok(allRepositories);
    }

    @GetMapping(value = "/branches", headers = "Accept=application/json")
    public ResponseEntity<Set<BranchDTO>> getRepositoryBranches(@RequestParam String username, @RequestParam String repositoryName) {

        Set<BranchDTO> repositoryBranches = gitHubService.getRepositoryBranchesDetails(username, repositoryName);

        return ResponseEntity.ok(repositoryBranches);
    }

    @GetMapping(value = "/commits", headers = "Accept=application/json")
    public ResponseEntity<Set<CommitDTO>> getRepositoryCommits(@RequestParam String username, @RequestParam String repositoryName) {

        Set<CommitDTO> repositoryCommits = gitHubService.getRepositoryCommitDetails(username, repositoryName);

        return ResponseEntity.ok(repositoryCommits);
    }



}