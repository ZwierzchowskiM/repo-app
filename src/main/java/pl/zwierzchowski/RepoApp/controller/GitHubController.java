package pl.zwierzchowski.RepoApp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.zwierzchowski.RepoApp.domain.dto.BranchDTO;
import pl.zwierzchowski.RepoApp.domain.dto.CommitDTO;
import pl.zwierzchowski.RepoApp.domain.dto.RepositoryDTO;
import pl.zwierzchowski.RepoApp.service.GitHubServiceImpl;
import pl.zwierzchowski.RepoApp.service.GithubService;
import java.util.Set;

@RestController
@RequestMapping("${api.path}/github")
public class GitHubController {

    private static final Logger logger = LoggerFactory.getLogger(GitHubServiceImpl.class);

    private GithubService gitHubService;

    public GitHubController(GithubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping(value = "/repositories", headers = "Accept=application/json")
    public ResponseEntity<Set<RepositoryDTO>> getUserRepositories(@RequestParam String username) {

        logger.info("Fetching repositories for user: {}", username);
        Set<RepositoryDTO> allRepositories = gitHubService.getRepositoriesDetails(username);

        return ResponseEntity.ok(allRepositories);
    }

    @GetMapping(value = "/branches", headers = "Accept=application/json")
    public ResponseEntity<Set<BranchDTO>> getRepositoryBranches(@RequestParam String username, @RequestParam String repositoryName) {

        logger.info("Fetching branches for user: {} and repository: {}", username, repositoryName);
        Set<BranchDTO> repositoryBranches = gitHubService.getRepositoryBranchesDetails(username, repositoryName);

        return ResponseEntity.ok(repositoryBranches);
    }

    @GetMapping(value = "/commits", headers = "Accept=application/json")
    public ResponseEntity<Set<CommitDTO>> getRepositoryCommits(@RequestParam String username, @RequestParam String repositoryName) {

        logger.info("Fetching commits for user: {} and repository: {}", username, repositoryName);
        Set<CommitDTO> repositoryCommits = gitHubService.getRepositoryCommitDetails(username, repositoryName);

        return ResponseEntity.ok(repositoryCommits);
    }
}