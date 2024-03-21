# RepoApp


The GitHubServiceImpl class provides functionality to interact with the GitHub API to fetch repository details for a given user.

- Spring Boot: This service is built using the Spring Boot framework.
- WebClient: Used for making HTTP requests to the GitHub API.

## Configuration

Configuration
GitHub API URLs: The URLs for fetching repositories and branches are configurable through application.properties (github.repos.api.url and github.branches.api.url).

## Endpoints
```
GET /github/repositories?username={username}
```
Fetching Detailed Repository Information for a User
This endpoint allows fetching detailed information about a user's repositories on GitHub. For each repository, information about the name, owner is provided.

### Parameters:
- `username` (String, required) - The username on GitHub.

### Example Usage:
GET http://localhost:8080/api/v1/repositories/?username=zwierzchowskim

### Response:
```JSON
  {
  "name": "photo-album-app",
  "owner": "ZwierzchowskiM"
  }
```


## Swagger

Swagger UI is available at `http://localhost:8080/swagger-ui/index.html#/`.



