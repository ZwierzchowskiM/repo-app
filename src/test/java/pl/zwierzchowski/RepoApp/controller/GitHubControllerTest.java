package pl.zwierzchowski.RepoApp.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.zwierzchowski.RepoApp.domain.dto.RepositoryDTO;
import pl.zwierzchowski.RepoApp.service.GitHubServiceImpl;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class GitHubControllerTest {

    @Value("${api.path}")
    private String apiPath;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GitHubServiceImpl gitHubService;

    @Test
    void givenValidUsername_whenGetUserRepositories_thenReturnsRepositoriesDetails() throws Exception {
        //given
        RepositoryDTO repositoryDTO1 = new RepositoryDTO("test1","url1");
        RepositoryDTO repositoryDTO2 = new RepositoryDTO("test2","url2");
        Set<RepositoryDTO> repositoryDTOs = Set.of(repositoryDTO1, repositoryDTO2);
        String username = "testUsername";

        //when
        when(gitHubService.getRepositoriesDetails(username)).thenReturn(repositoryDTOs);

        //then
        mockMvc.perform(get(apiPath + "/github/repositories")
                        .param("username", username))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void givenUserIdWithNoRepositories_whenListUserRepositories_thenReturnEmptySet() throws Exception {

        //given
        String username = "testUsername";

        //when
        when(gitHubService.getRepositoriesDetails(username)).thenReturn(Collections.emptySet());

        //then
        mockMvc.perform(get(apiPath + "/github/repositories")
                        .param("username", username))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    void givenEmptyUsername_whenListUserRepositories_thenReturnsNotFound() throws Exception {

        //given
        String emptyUsername = "";

        //then
        mockMvc.perform(get(apiPath + "/repositories/")
                        .param("username", emptyUsername))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getRepositoryBranches() {
    }

    @Test
    void getRepositoryCommits() {
    }
}