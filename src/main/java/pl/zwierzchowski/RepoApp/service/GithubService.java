package pl.zwierzchowski.RepoApp.service;

import pl.zwierzchowski.RepoApp.domain.Branch;
import pl.zwierzchowski.RepoApp.domain.Repository;
import pl.zwierzchowski.RepoApp.domain.dto.RepositoryDTO;

import java.util.List;

public interface GithubService {

    Repository[] getRepositories(String username);

}
