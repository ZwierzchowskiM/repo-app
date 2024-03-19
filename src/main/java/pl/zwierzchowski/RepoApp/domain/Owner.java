package pl.zwierzchowski.RepoApp.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    private String login;
    private String email;
    private String url;
    @JsonProperty("repos_url")
    private String reposUrl;
}
