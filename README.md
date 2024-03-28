# RepoApp


The GitHubServiceImpl class provides functionality to interact with the GitHub API to fetch repository details for a given user.

- Spring Boot: This service is built using the Spring Boot framework.
- WebClient: Used for making HTTP requests to the GitHub API.

## Configuration

Configuration
GitHub API URLs: The URLs for fetching repositories and branches are configurable through application.properties (github.repos.api.url and github.branches.api.url).

## Endpoints

### getRepositories:

Fetching Repository Information for a User
This endpoint allows fetching information about a user's repositories on GitHub. For each repository, information about the name and urle is provided.


#### Parameters:
- `username` (String, required) - The username on GitHub.

#### Example Usage:
GET http://localhost:8080/api/v1/repositories/?username=zwierzchowskim

#### Response:
```JSON
  [
  {
    "name": "task-manager-rest-api",
    "url": "https://api.github.com/repos/ZwierzchowskiM/task-manager-rest-api"
  },
  {
    "name": "repo-app",
    "url": "https://api.github.com/repos/ZwierzchowskiM/repo-app"
  },
  {
    "name": "photo-album-app",
    "url": "https://api.github.com/repos/ZwierzchowskiM/photo-album-app"
  }
]
```
### getRepositoryBranches:

Fetching Branches Information for a Repository
This endpoint allows fetching information about a repository branches on GitHub. For branch, information about the name is provided.


#### Parameters:
- `username` (String, required) - The username on GitHub.
- `repositoryName` (String, required) - The repository name.

#### Example Usage:
GET http://localhost:8080/github/branches?username=ZwierzchowskiM&repositoryName=repo-app

#### Response:
```JSON
[
  {
    "name": "feature_add_GitHubAPI_connection"
  },
  {
    "name": "main"
  }
]
```
### getRepositoryCommits:

Fetching Branches Information Commits for a Repository
This endpoint allows fetching information about a repository commits on GitHub. For commit, information about sha, message, author and date is provided.

```
GET /github/repositories?username={username}
```

#### Parameters:
- `username` (String, required) - The username on GitHub.
- `repositoryName` (String, required) - The repository name.

#### Example Usage:
GET http://localhost:8080/github/commits?username=ZwierzchowskiM&repositoryName=repo-app

#### Response:
```JSON
[
  {
    "sha": "8f51520776a91783ab4c7e06f00cd9acc5a13546",
    "message": "Merge pull request #1 from ZwierzchowskiM/feature_add_GitHubAPI_connectionFeature add git hub api connection",
    "authorLogin": "ZwierzchowskiM",
    "date": "2024-03-21T09:59:45Z"
  },
  {
    "sha": "b01b4f4de1133ffe072b197af07bca9516004c69",
    "message": "edit README.md",
    "authorLogin": "ZwierzchowskiM",
    "date": "2024-03-21T10:02:56Z"
  }
]
```

## Swagger

Swagger UI is available at `http://localhost:8080/swagger-ui/index.html#/`.


## How to run

To get this project up and running, navigate to root directory of an application and execute following commands:

* Create a jar file.
```
$ mvn package
```

* Then build docker image using already built jar file.

```
$ docker build -t repoapp .
```

* Run whole setup

```
$ docker run -p 8080:8080 repoapp
```
