package pl.zwierzchowski.RepoApp.domain.dto;

import java.util.Date;

public record CommitDTO(String sha, String message, String authorLogin, String date) {
}
