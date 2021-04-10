# Guest Book Service

<p align="center">
  <a href="#">
    <img src="./img/img.png" alt="playlist logo" width="73" height="73">
  </a>
</p>

<h3 align="center">Playlist Service (Back-End Spring Boot REST API)</h3>

<p align="center">
  Playlist is a service where the user can go and create a playlist and add/remove songs to playlist.
  <br>

  <br>
</p>

## Table of contents

- [Technology Used](#technology-used)
- [Installation Instruction](#installation-instruction)
- [Auto-Deploy Instructions](#auto-deploy-instructions)
- [API](#api)
- [What's Included](#whats-included)
- [Creators](#developers)

## Technology Used

- Gradle as Build Tool
- Spring boot is used for the backend Services.
- H2 database for Tests
- Postgres database to store the data.
- Junit and Mockito for Tests.
- Deployed on Heroku

## Installation Instruction

Software Requirement:

1. Gradle ([install](https://gradle.org/install/))

2. Java (minimum java 8) ([install](https://www.oracle.com/java/technologies/javase-downloads.html))

3. Docker ([install](https://docs.docker.com/get-docker/))

4. Heroku CLI - Optional for Deployment. ([install](https://devcenter.heroku.com/articles/heroku-cli))
   Follow these easy step:


1. Clone the repository:
     ```
     https://github.com/Christopher-Diehl-cognizant-com/PlaylistService
     ```
2. Execute this on command line:

     ```shell
     $ cd PlaylistService
     $ docker build -t <your email>/playlistService .
     $ docker network create --driver bridge playlistnetwork
     $ docker run -d -p 5432:5432 -e POSTGRES_USER=cognizant -e POSTGRES_PASSWORD=galvanize -e POSTGRES_DB=playlistdb --network playlistnetwork --name playlistpostgres postgres
     $ docker run -d -p 8081:8080 -e SPRING_PROFILES_ACTIVE=dockerlocal --network playlistnetwork --name playlistservice <your email>/playlistService
     ```
3. Heroku Deployment (Heroku CLI Required.)

   ```shell
   $ heroku login
   $ heroku create
   $ heroku git:remote -a
   $ heroku container:login
   $ heroku container:push web
   $ heroku container:release web
   ```


4. Enjoy

## Auto-Deploy Instructions

Follow these to deploy the container automatically. Files Required:

- Dockerfile
- heroku.yml Steps:

1. Login into heroku.

```shell
heroku login
```

2. Add Heroku Git Remote

```shell
# playlist-service is my app name. Please use yours 
heroku git:remote -a playlist-service 
```

3. Use Heroku Stack to set heroku.yml

```shell
heroku stack:set container
```

4. Commit and Push into heroku remote.

```shell
git add --all 
git commit -m "heroku commit"
git push heroku master 
```

5. Open App

```shell
heroku open
```

Hint: For auto deploy please follow step 4.

## Sample Server

Feel free to play with our sample server.

- ([Server# 1](https://chrisdiehlcognizantplaylist.herokuapp.com))

## API

```text
Base URL: /, Version: 1.1, local port: 8081

Default request content-types: application/json

Default response content-types: application/json

Schemes: http 
```

## API Reference

- [Server Docs](https://chrisdiehlcognizantplaylist.herokuapp.com/docs/index.html)

<table style="
    width: 100%;
    max-width: 100%;
    margin-bottom: 20px;
    border: 1px solid #ddd;
    border-collapse: collapse;
    border-spacing: 0;
    background-color: transparent;
    display: table;
">
  <thead>
  <tr>
    <th>Path</th>
    <th>Operation</th>
    <th>Description</th>
    <th>Controller -> Method</th>
  </tr>
  </thead>
  <tbody>
  <tr>
    <td style="border: 1px solid #ddd;padding: 5px;" rowspan="1">
      <a href="#summary">/playlist</a>
    </td>
    <td style="border: 1px solid #ddd;padding: 5px;">
      <a href="#post-playlist">POST</a>
    </td>
    <td style="border: 1px solid #ddd;padding: 5px;">
      <p>Create Playlist. </p>
    </td>
    <td style="border: 1px solid #ddd;padding: 5px;">
      <p>PlaylistController -> newPlaylist()</p>
    </td>
  </tr>
  <tr>
    <td style="border: 1px solid #ddd;padding: 5px;" rowspan="3">
      <a href="#summary">/playlist/song</a>
    </td>
    <td style="border: 1px solid #ddd;padding: 5px;">
      <a href="#put-playlistsong">PUT</a>
    </td>
    <td style="border: 1px solid #ddd;padding: 5px;">
      <p>Add song into Playlist.  </p>
    </td>
    <td style="border: 1px solid #ddd;padding: 5px;">
      <p>PlaylistController -> addSong2PlayList()</p>
    </td>
  </tr>
  <tr>
    <td style="border: 1px solid #ddd;padding: 5px;">
      <a href="#delete-playlistsong">DELETE</a>
    </td>
    <td style="border: 1px solid #ddd;padding: 5px;">
      <p>Delete Song from Playlist</p>
    </td>
    <td style="border: 1px solid #ddd;padding: 5px;">
      <p>PlaylistController -> removeSong2PlayList()</p>
    </td>
  </tr>
  <tr>
    <td style="border: 1px solid #ddd;padding: 5px;">
      <a href="#get-playlistsong">GET</a>
    </td>
    <td style="border: 1px solid #ddd;padding: 5px;">
      <p>Get All the songs of a Playlist</p>
    </td>
    <td style="border: 1px solid #ddd;padding: 5px;">
      <p>PlaylistController -> playListSongs()</p>
    </td>
  </tr>
  </tbody>
</table>

#### API Details

#### POST /playlist

Response Header

```text
    Status: 201 CREATED
```

Request Paramter

```text
{
name=[test_list]
}
```

Response Header

```text
[Content-Type:"application/json"]
```

Response Body

```json5
{
  "message": "Successfully Created."
}
```

#### PUT /playlist/song

Response Header

```text
    Status: 201 CREATED
```

Request Parameter

```text
{
  name=[test_list], song_name=[My song]
}
```

Response Parameter

```json5
{
  "message": "Successfully: Added song to test_list playlist."
}
```

#### DELETE /playlist/song

Response Header

```text
    Status: 204 NO CONTENT
```

Request Parameter

```text
{
   name=[test_list], song_name=[remove_song]
}
```

Response Parameter

```json5
{
  "message": "Successfully: Removed song from test_list playlist."
}
```

#### GET /playlist/song

Response Header

```text
    Status: 200 OK
```

Request Parameter

```text
{
   name=[test_list]
}
```

Response Parameter

```json5
{
  "message": "Playlist test_list songs.",
  "songs": [
    "song 1",
    "song 2"
  ]
}
```

## What's included

Within the download you'll find the following directories and files, logically grouping common assets and providing both
compiled and minified variations. You'll see something like this:

<details>
<summary>
File Tree.
</summary>

```text
│   .gitignore
│   build.gradle
│   Command.sh
│   Dockerfile
│   gradlew
│   gradlew.bat
│   heroku.yml
│   Readme.md
│   settings.gradle
└── src
    ├── docs
    │    └── asciidoc
    │           └── index.adoc
    ├── main
    │    ├── generated
    │    ├── java
    │    │ └── com
    │    │     └── galvanize
    │    │         └── playlist
    │    │             ├── PlaylistApplication.java
    │    │             ├── controller
    │    │             │   └── PlaylistController.java
    │    │             ├── entity
    │    │             │   └── PlayListEntity.java
    │    │             ├── repository
    │    │             │   └── PlayListRepo.java
    │    │             ├── response
    │    │             │   ├── CustomResponse.java
    │    │             │   └── PlayListSongsResponse.java
    │    │             └── service
    │    │                 └── PlayListService.java
    │    └── resources
    │        ├── application-dockerlocal.properties
    │        ├── application-qa.properties
    │        ├── application-production.properties
    │        └── application.properties
    └── test
        └── java
            └── com
                └── galvanize
                    └── playlist
                        ├── PlaylistApplicationTests.java
                        ├── integration
                        │       └── PlaylistServiceIT.java
                        └── unit
                            ├── controller
                            │   └── PlayListControllerUnitTest.java
                            └── service
                                └── PlayListServiceUnitTest.java
```

</details>

## Developers

**Christopher Diehl**

- <https://github.com/Christopher-Diehl-cognizant-com>

**Mohammad Javed**

- <https://github.com/gajjuCoderBoi>