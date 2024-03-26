package pl.zwierzchowski.RepoApp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitAuthor {

    private String name;
    private String email;
    private String date;
}
