package pl.zwierzchowski.RepoApp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commit {

        private String url;
        private CommitAuthor author;
        private String message;
}
