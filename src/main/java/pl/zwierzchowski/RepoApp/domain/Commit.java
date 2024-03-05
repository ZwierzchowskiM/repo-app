package pl.zwierzchowski.RepoApp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Commit {

    private String sha;
    private String url;
}
