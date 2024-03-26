package pl.zwierzchowski.RepoApp.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repository {

    private String name;
    private Owner owner;
    private Object description;
    private boolean fork;
    private String url;
    @JsonProperty("branches_url")
    private String branchesUrl;
    @JsonProperty("commits_url")
    private String commitsUrl;
    @JsonProperty("comments_url")
    private String commentsUrl;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("pushed_at")
    private Date pushedAt;
    @JsonProperty("git_url")
    private String gitUrl;
    @JsonProperty("clone_url")
    private String cloneUrl;
}