package pl.zwierzchowski.RepoApp.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Owner {

    private String login;
    private String url;
    @JsonProperty("repos_url")
    private String reposUrl;
}
